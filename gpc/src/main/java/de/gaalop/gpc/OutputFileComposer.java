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
                    
                int commandEndPos = -1;
                while(true) {
                    // get buffered command
                    String command = commandBuffer.toString();
                    System.out.println(command);
                    
                    // find command end pos
                    commandEndPos = findCommandEndPos(command,
                                                      Math.max(0,commandEndPos));

                    // exit loop, if no command end found
                    if(commandEndPos < 0)
                        break;

                    // exit loop, if inside comment
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
                    if (command.indexOf(Main.gpcMvFromArray) >= 0) {
                        processMvFromArray(command, outputFile, mvComponents);
                    } else if (command.indexOf(Main.gpcMvToArray) >= 0) {
                        processMvToArray(command, outputFile, mvComponents);
                    } else if (command.indexOf(Main.gpcMvToStridedArray) >= 0) {
                        processMvToStridedArray(command, outputFile, mvComponents);
                    } else if (command.indexOf(Main.gpcMvToVector) >= 0) {
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
    
    public static int findCommandEndPos(String command,int startPos) {
        // search for command end
        final String[] commandEndTokens = {";","{","}"};
        int commandEndPos = -1;
        for (final String commandEndToken : commandEndTokens) {
            int foundPos = command.indexOf(commandEndToken,startPos);

            if (foundPos >= 0
                    && ((foundPos += commandEndToken.length()) < commandEndPos)
                    || commandEndPos < 0)
                commandEndPos = foundPos;
        }
        return commandEndPos;
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
    
    public static void processMvFromArray(final String command, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents) throws RecognitionException, IOException {        
        // parse assignment
        AssignmentNode assignment = parseAssignment(command);
        if(assignment == null)
            return;
        // get mv name
        final String mv = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String array = it.next().toString();
        Integer count = 0;
        while(it.hasNext()) {
            outputFile.write(mv);
            outputFile.write("_");
            outputFile.write(count.toString());
            outputFile.write(" = ");
            outputFile.write(array);
            outputFile.write('[');
            outputFile.write(count.toString());
            outputFile.write(']');
            outputFile.write(";\n");
            outputFile.write(mv);
            
            ++count;
        }
        
        // include in block
        outputFile.write(Main.LINE_END);
        outputFile.write(mv);
        outputFile.write(" = ");
        outputFile.write(Main.LINE_END);
    }

    public static void processMvToArray(final String command, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents) throws RecognitionException, IOException {        
        // parse assignment
        AssignmentNode assignment = parseAssignment(command);
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
        AssignmentNode assignment = parseAssignment(command);
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
    
    public static String getOpenCLIndex(Integer index) {
        if(index < 10)
            return index.toString();
        else switch(index) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
        }
        
        assert(false);
        return "fail";
    }

    public static void processMvToVector(final String command, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents) throws RecognitionException, IOException {
        // parse assignment
        AssignmentNode assignment = parseAssignment(command);
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
            outputFile.write(getOpenCLIndex(count++));
            outputFile.write(" = ");
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(Main.LINE_END);
    }

    public static AssignmentNode parseAssignment(final String line) throws RecognitionException {
        try {
            ANTLRStringStream inputStream = new ANTLRStringStream(line);
            GPCLexer lexer = new GPCLexer(inputStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            GPCParser parser = new GPCParser(tokenStream);
            GPCParser.program_return parserResult = parser.program();
            CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
            GPCTransformer transformer = new GPCTransformer(treeNodeStream);
            AssignmentNode assignment = transformer.assignment();
            
            return assignment;
        } catch(Exception e) {
            return null;
        }
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
