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
import java.util.Map.Entry;
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
    private static Integer gaalopBlockCount = 0;
    private static Integer lineCount = 1;
    
    public static void composeOutputFile(Vector<String> gaalopOutFileVector) throws IOException, RecognitionException {
        // log
        System.out.println("writing");
        
        // get input file dir
        String inputFileDir = getInputFileDir();
        
        // open output file
        BufferedWriter outputFile = Main.createFileOutputStringStream(Main.outputFilePath);
        
        // Tell the compiler where the original source file is.
        // Useful when there is an syntax error at the beginning.
        // The IDE will redirect to the original source file.
        writeLinePragma(outputFile, lineCount);
        
        // process line by line
        Map<String, Map<String,String>> mvComponents = new HashMap<String, Map<String,String>>();
        final BufferedReader inputFile = Main.createFileInputStringStream(Main.inputFilePath);
        String line;
        while ((line = inputFile.readLine()) != null) {            
            if (line.contains("#include") && line.contains("\"")) {
                // we read one line
                ++lineCount;

                processInclude(outputFile, line, inputFileDir);
            }
            else if (line.contains(Main.clucalcBegin)) {
                // we read one line
                // but processClucalcBlock handles counting internally
            	
                processClucalcBlock(outputFile, gaalopOutFileVector, inputFile, mvComponents);
            }
            else if(line.contains(Main.gpcBegin)) {
                // we read one line
                ++lineCount;

                processGPCBlock(inputFile, mvComponents, outputFile, gaalopOutFileVector);
            }
            else {
                // we read one line
                ++lineCount;

                outputFile.write(line);
                outputFile.write(Main.LINE_END);
            }
        }
        
        // close output file
        outputFile.close();
    }

    protected static String getInputFileDir() {
        // retrieve input file directory
        String inputFileDir;
        {
            int pos = Main.inputFilePath.lastIndexOf('/');
            if (pos <= 0)
                pos = Main.inputFilePath.lastIndexOf('\\');
            assert (pos > 0);
            inputFileDir = Main.inputFilePath.substring(0, pos + 1);
        }
        return inputFileDir;
    }

    protected static void processClucalcBlock(BufferedWriter outputFile,
                                              Vector<String> gaalopOutFileVector,
                                              final BufferedReader inputFile,
                                              Map<String, Map<String, String>> mvComponents) throws IOException {
        // line pragma for compile errors
        writeLinePragma(outputFile, ++lineCount); // we skipped clucalc begin line, pre-increment because we actually want to point to the line after the #pragma

        // merge optimized code
        outputFile.write(gaalopOutFileVector.get(gaalopBlockCount));
        // skip original code
        String line;
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.clucalcEnd))
                break;
            ++lineCount;
        }
        // line pragma for compile errors
        writeLinePragma(outputFile, ++lineCount); // we skipped clucalc end line, pre-increment because we actually want to point to the line after the #pragma
        
        // scan block
        Common.scanBlock(gaalopOutFileVector, gaalopBlockCount, mvComponents);
        
        // increment block count
        ++gaalopBlockCount;
    }

    protected static void processGPCBlock(final BufferedReader inputFile,
                                          Map<String, Map<String, String>> mvComponents,
                                          BufferedWriter outputFile,
                                          Vector<String> gaalopOutFileVector) throws IOException, RecognitionException {
        StringBuffer commandBuffer = new StringBuffer();

        String line;
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.clucalcBegin)) { // start clucalc block
                // flush command buffer
                outputFile.write(commandBuffer.toString());
                
                // process clucalc block
                processClucalcBlock(outputFile, gaalopOutFileVector,
                                    inputFile, mvComponents);

                // continue without appending to command buffer
                continue;
            } else if(line.contains(Main.gpcEnd)) { // end gpc block
                // flush command buffer
                outputFile.write(commandBuffer.toString());
                
                // line pragma for compile errors
                writeLinePragma(outputFile, ++lineCount); // we skipped gpc end line, pre-increment because we actually want to point to the line after the #pragma

                // we reached the end of this block, so exit loop.
                break;
            }
            
            // we read one line
            ++lineCount;
            
            // append line to command buffer
            commandBuffer.append(line).append(Main.LINE_END);
            // process command buffer
            processCommandBuffer(commandBuffer, mvComponents, outputFile);
         }
    }

    protected static void processCommandBuffer(StringBuffer commandBuffer, Map<String, Map<String, String>> mvComponents, BufferedWriter outputFile) throws RecognitionException, IOException {
        int commandEndPos = -1;
        while(true) {
            // get buffered command
            String command = commandBuffer.toString();                    
            // find command end pos
            commandEndPos = Common.findCommandEndPos(command,
                                                     commandEndPos);

            // exit loop, if no command end found
            if(commandEndPos < 0)
                break; // request new line
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
            
            // special case handling for mv_getbladecoeff
            command = processMvGetBladeCoeff(command,mvComponents);
            // other commands are always exclusive and not embedded
            if (command.contains(Main.gpcMvFromArray) ||
                command.contains(Main.gpcMvFromStridedArray) ||
                command.contains(Main.gpcMvFromVector)) {
                // just ignore import commands
            } else if (command.contains(Main.gpcMvToArray))
                processMvToArray(command, outputFile, mvComponents);
            else if (command.contains(Main.gpcMvToStridedArray))
                processMvToStridedArray(command, outputFile, mvComponents);
            else if (command.contains(Main.gpcMvToVector))
                processMvToVector(command, outputFile, mvComponents);
            else // we found a comand, but it is not one of ours
                outputFile.write(command);

            // we found a command end, remove command from buffer
            commandBuffer.delete(0, commandEndPos);
        }
    }

    protected static void processInclude(BufferedWriter outputFile, String line, String inputFileDir) throws IOException {
        // TODO parse this with ANTLR
        
        final int pos = line.indexOf('\"') + 1;
        outputFile.write(line.substring(0, pos));
        outputFile.write(inputFileDir);
        outputFile.write(line.substring(pos));
        outputFile.write(Main.LINE_END);
    }
    
    /*
     * Search for keywords in command and parse the command.
     * Handle mv_get_bladecoeff as a specieal case
     * because it may be embedded anywhere.
     */
    public static String processMvGetBladeCoeff(String command,
            Map<String, Map<String,String>> mvComponents) throws IOException {
        StringBuilder out = new StringBuilder();
        
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
    
    public static String getMvBladeCoeffArrayEntry(Map<String, Map<String, String>> mvComponents,
                                                   final String mv,
                                                   String blade) {
        // format blade
        blade = Common.formatBladeName(blade);
        
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
        	// get blade
        	final String blade = it.next().toString();
        	
        	// skip blade if zero
        	if(blade.equals("0"))
        		continue;
        	
            outputFile.write(array);
            outputFile.write('[');
            outputFile.write((count++).toString());
            outputFile.write(']');
            outputFile.write(" = ");                    
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,blade));                                        
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
        	// get blade
        	final String blade = it.next().toString();
        	
        	// skip blade if zero
        	if(blade.equals("0"))
        		continue;
        	
            outputFile.write(array);
            outputFile.write("[(");
            outputFile.write(index);
            outputFile.write(") + ");
            outputFile.write((counter++).toString());
            outputFile.write(" * (");
            outputFile.write(stride);
            outputFile.write(")] = ");                    

            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,blade));                                        
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
        	// get blade
        	final String blade = it.next().toString();
        	
        	// skip blade if zero
        	if(blade.equals("0"))
        		continue;
        	
            outputFile.write(vec);
            outputFile.write(".s"); // TODO recognize vector size and assign with make_float
            outputFile.write(Common.getOpenCLIndex(count++));
            outputFile.write(" = ");
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,blade));                                        
            outputFile.write(";\n");
        }
        outputFile.write(Main.LINE_END);
    }
    
    public static void writeLinePragma(BufferedWriter outputFile, Integer lineCount) throws IOException {
        if(!Main.writeLinePragmas)
            return;
        
        // line pragma for compile errors
        outputFile.write("#line ");
        outputFile.write(lineCount.toString());
        outputFile.write(" \"");
        outputFile.write(Main.inputFilePath);
        outputFile.write("\"\n");
    }
}
