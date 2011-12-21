/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import antlr.RecognitionException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MacroCall;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 *
 * @author Patrick Charrier
 */
public class OutputFileComposer {
    public static void composeOutputFile(Vector<String> gaalopOutFileVector) throws IOException, RecognitionException {
        String line;
        // log
        System.out.println("writing");
        // retrieve input file directory
        String inputFileDir;
        {
            int pos = Main.inputFilePath.lastIndexOf('/');
            if (pos <= 0) {
                pos = Main.inputFilePath.lastIndexOf('\\');
            }
            assert (pos > 0);
            inputFileDir = Main.inputFilePath.substring(0, pos + 1);
        }
        
        // process line by line
        Map<String, Map<String,String>> mvComponents = new HashMap<String, Map<String,String>>();
        BufferedWriter outputFile = Main.createFileOutputStringStream(Main.outputFilePath);
        final BufferedReader inputFile = Main.createFileInputStringStream(Main.inputFilePath);
        StringBuffer commandBuffer = new StringBuffer();
        String lineCommentAppend;
        Integer gaalopBlockCount = 0;
        Integer lineCount = 1;
        writeLinePragma(outputFile, lineCount++);
        while ((line = inputFile.readLine()) != null) {
            
            // parse line commands first
            if (line.contains("#include") && line.contains("\"")) { // TODO parse this with ANTLR
                // flush command buffer first
                outputFile.append(commandBuffer.toString());
                commandBuffer = new StringBuffer();
                
                final int pos = line.indexOf('\"') + 1;
                outputFile.write(line.substring(0, pos));
                outputFile.write(inputFileDir);
                outputFile.write(line.substring(pos));
                outputFile.write(Main.LINE_END);
            } // found gaalop line - insert intermediate gaalop file
            else if (line.contains(Main.gpcBegin)) {
                // flush command buffer first
                outputFile.append(commandBuffer.toString());
                commandBuffer = new StringBuffer();

                // line pragma for compile errors
                writeLinePragma(outputFile, lineCount++); // we skipped gpc begin line

                // merge optimized code
                outputFile.write(gaalopOutFileVector.get(gaalopBlockCount));

                // skip original code
                while ((line = inputFile.readLine()) != null) {
                    if (line.contains(Main.gpcEnd))
                        break;
                    ++lineCount;
                }

                // line pragma for compile errors
                writeLinePragma(outputFile, lineCount++); // we skipped gpc end line

                // scan block
                Common.scanBlock(gaalopOutFileVector, gaalopBlockCount, mvComponents);
                
                // increment block count
                ++gaalopBlockCount;
            } else { // non-line commands
                // add line to command buffer
                commandBuffer.append(line).append(Main.LINE_END);

                // process command buffer
                int commandEndPos = -1;
                while(true) {
                    // get buffered command
                    String command = commandBuffer.toString();                    
                    // find command end pos
                    commandEndPos = Common.findCommandEndPos(command,
                                                             commandEndPos);

                    // exit loop, if no command end found
                    if(commandEndPos < 0)
                        break;
                    // continue loop, if inside comment
                    final int commentStart = command.lastIndexOf("/*");
                    final int commentEnd = command.lastIndexOf("*/");
                    if((commentStart >= 0 && // we found comment start
                        commentStart < commandEndPos) && // comment start before command end
                       (commentEnd < 0 || // no comment end
                        commentEnd >= commandEndPos)) { // comment end after command end (>= is important)
                        ++commandEndPos; // increment, so we do not find it again
                        continue;
                    }
                    
                    // extract command
                    command = command.substring(0,commandEndPos);                    
                    // special case handling
                    command = processMvGetBladeCoeff(command,mvComponents);

                    // other commands are always exclusive and not embedded
                    if (command.contains(Main.gpcMvFromArray)) {
                    } else if (command.contains(Main.gpcMvFromStridedArray)) {
                    } else if (command.contains(Main.gpcMvFromVector)) {
                    } else if (command.contains(Main.gpcMvToArray)) {
                        processMvToArray(command, outputFile, mvComponents);
                    } else if (command.contains(Main.gpcMvToStridedArray)) {
                        processMvToStridedArray(command, outputFile, mvComponents);
                    } else if (command.contains(Main.gpcMvToVector)) {
                        processMvToVector(command, outputFile, mvComponents);
                    } else {
                        // we found a comand, but it is not one of ours
                        outputFile.write(command);
                    }

                    // we found a command end, remove command from buffer
                    commandBuffer.delete(0, commandEndPos);
                }
            }
            
            // we read one line
            ++lineCount;
        }
        
        // flush command buffer
        outputFile.write(processMvGetBladeCoeff(commandBuffer.toString(),mvComponents));
        // close output file
        outputFile.close();
    }
    
    /*
     * Search for keywords in command and parse the command.
     * Handle mv_get_bladecoeff as a specieal case
     * because it may be embedded anywhere.
     */
    public static String processMvGetBladeCoeff(String command,
            Map<String, Map<String,String>> mvComponents) throws IOException {
        
        StringBuffer out = new StringBuffer();
        
        // replace step by step
        CommandFunctionReplacer cp = new CommandFunctionReplacer(
                command,
                Main.gpcMvGetBladeCoeff);
        while (cp.isFound()) {

            // get blade coeff array entry
            final String bladeCoeffArrayEntry = getMvBladeCoeffArrayEntry(
                    mvComponents,
                    cp.getCommandParams()[0],
                    cp.getCommandParams()[1]);

            // write to file
            out.append(cp.getCleanedLineStart());
            out.append(bladeCoeffArrayEntry);

            // search for further occurences
            cp = new CommandFunctionReplacer(
                    cp.getCleanedLineEnd(),
                    Main.gpcMvGetBladeCoeff);
        }

        // go on with remains of command
        out.append(cp.getCleanedLineEnd());

        return out.toString();
    }
    
    public static String getMvBladeCoeffArrayEntry(Map<String, Map<String, String>> mvComponents, final String mv, String blade) {
        // remove whitespaces from blade
        StringTokenizer tokenizer = new StringTokenizer(blade);
        StringBuffer bladeBuffer = new StringBuffer();
        while(tokenizer.hasMoreTokens())
            bladeBuffer.append(tokenizer.nextToken());
        blade = bladeBuffer.toString();
        
        // check for negation
        boolean negated;
        if(negated = (blade.charAt(0) == '-'))
            blade = blade.substring(1);
        
        // get blade coeff array entry
        String bladeCoeffArrayEntry = null;
        Map<String,String> bladeMap = mvComponents.get(mv);
        if(bladeMap != null)
            bladeCoeffArrayEntry = bladeMap.get(blade);
        // handle the case that this blade coeff is zero
        if(bladeCoeffArrayEntry == null)
            bladeCoeffArrayEntry = Main.gpcZero;
        
        return negated ? "-" + bladeCoeffArrayEntry : bladeCoeffArrayEntry;
    }

    public static void processMvToArray(final String command, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents) throws RecognitionException, IOException {        
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if(assignment == null)
            return;
        // get array name
        String array = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        Integer count = 0;
        outputFile.write(Main.LINE_END);
        outputFile.write(Main.LINE_END);
        while(it.hasNext()) {
            outputFile.write(array);
            outputFile.write('[');
            outputFile.write((count++).toString());
            outputFile.write(']');
            outputFile.write(" = ");                    
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(Main.LINE_END);
    }

    public static void processMvToStridedArray(final String command, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents) throws RecognitionException, IOException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if(assignment == null)
            return;
        // get array expression
        final String array = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        final String index = it.next().toString();
        final String stride = it.next().toString();
        outputFile.write(Main.LINE_END);
        outputFile.write(Main.LINE_END);
        Integer counter = 0;
        while(it.hasNext()) {
            outputFile.write(array);
            outputFile.write("[(");
            outputFile.write(index);
            outputFile.write(") + ");
            outputFile.write((counter++).toString());
            outputFile.write(" * (");
            outputFile.write(stride);
            outputFile.write(")] = ");                    

            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(Main.LINE_END);
    }

    public static void processMvToVector(final String command, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents) throws RecognitionException, IOException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if(assignment == null)
            return;
        // get array name
        String vec = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        int count = 0;
        outputFile.write(Main.LINE_END);
        while(it.hasNext()) {
            outputFile.write(vec);
            outputFile.write(".s"); // TODO recognize vector size and assign with make_float
            outputFile.write(Common.getOpenCLIndex(count++));
            outputFile.write(" = ");
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(Main.LINE_END);
    }
    
    public static void writeLinePragma(BufferedWriter outputFile, Integer lineCount) throws IOException {
        // line pragma for compile errors
        outputFile.write("#line ");
        outputFile.write(lineCount.toString());
        outputFile.write(" \"");
        outputFile.write(Main.inputFilePath);
        outputFile.write("\"\n");
    }
}
