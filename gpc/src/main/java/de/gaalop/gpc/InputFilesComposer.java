/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import de.gaalop.NameTable;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MacroCall;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import antlr.RecognitionException;

/**
 *
 * @author Patrick Charrier
 */
public class InputFilesComposer {

    public static List<String> readInputFile() throws Exception {
        // process input file
        // split into gaalop input files and save in memory
        List<String> gaalopInFileVector = new ArrayList<String>();
        StringBuffer importBuffer = new StringBuffer();
        String line;
        final BufferedReader inputFile = Main.createFileInputStringStream(Main.inputFilePath);
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.gpcBegin))
                readGPCBlock(inputFile, importBuffer, gaalopInFileVector);
            else if(line.contains(Main.clucalcBegin))
                importBuffer = readClucalcBlock(importBuffer, inputFile, gaalopInFileVector);
        }

        return gaalopInFileVector;
    }

    protected static void readGPCBlock(final BufferedReader inputFile,
                                        StringBuffer importBuffer,
                                        List<String> gaalopInFileVector) throws IOException, RecognitionException {
        StringBuilder commandBuffer = new StringBuilder();
        String line;
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.clucalcBegin)) // start clucalc block
                readClucalcBlock(importBuffer, inputFile, gaalopInFileVector);
            else if(line.contains(Main.gpcEnd)) // end gpc block
                break;
            
            // add line to command buffer
            commandBuffer.append(line).append(Main.LINE_END);
            // process command buffer
            processCommandBuffer(commandBuffer, importBuffer);
        }
    }

    protected static void processCommandBuffer(StringBuilder commandBuffer, StringBuffer importBuffer) throws RecognitionException, IOException {
        int commandEndPos = -1;
        while (true) {
            // get buffered command
            String command = commandBuffer.toString();
            // find command end pos
            commandEndPos = Common.findCommandEndPos(command,
                    commandEndPos);

            // exit loop, if no command end found
            if (commandEndPos < 0)
                break;
            // continue loop, if inside comment
            final int commentStart = command.lastIndexOf("/*");
            final int commentEnd = command.lastIndexOf("*/");
            if ((commentStart >= 0 && // we found comment start
                    commentStart < commandEndPos) && // comment start before command end
                    (commentEnd < 0 || // no comment end
                    commentEnd >= commandEndPos)) { // comment end after command end (>= is important)
                ++commandEndPos; // increment, so we do not find it again
                continue;
            }

            // extract command
            command = command.substring(0, commandEndPos);
            if (command.contains(Main.gpcMvFromArray)) {
                processMvFromArray(command, importBuffer);
            } else if (command.contains(Main.gpcMvFromStridedArray)) {
                processMvFromStridedArray(command, importBuffer);
            } else if (command.contains(Main.gpcMvFromVector)) {
                processMvFromVector(command, importBuffer);
            }

            // we found a command end, remove command from buffer
            commandBuffer.delete(0, commandEndPos);
        }
    }

    protected static StringBuffer readClucalcBlock(StringBuffer importBuffer,
                                                    final BufferedReader inputFile,
                                                    List<String> gaalopInFileVector) throws IOException {
        // found gaalop line
        StringBuilder gaalopInFileStream = new StringBuilder();
        // start with import statements and clear them
        gaalopInFileStream.append(importBuffer.toString());
        importBuffer = new StringBuffer();
        // read until end of optimized file
        String line;
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.clucalcEnd))
                break;
            
            gaalopInFileStream.append(line);
            gaalopInFileStream.append(Main.LINE_END);
        }
        // warning, if we reached end of file before gpc end
        if (line == null)
            System.err.println("Warning: Reached end of file before +pragma clucalc end");

        // add to vector
        gaalopInFileVector.add(gaalopInFileStream.toString());
        //System.out.println(gaalopInFileStream.toString());
        
        return importBuffer;
    }

    public static void processMvFromArray(final String command, StringBuffer output) throws RecognitionException, IOException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if (assignment == null)
            return;

        // print mv assignment
        output.append(Main.LINE_END);
        output.append(assignment.getVariable().getName()); // mv name
        output.append(" = 0");

        Iterator<Expression> it = ((MacroCall) assignment.getValue()).getArguments().iterator();
        final String array = it.next().toString();
        int count = 0;
        while (it.hasNext()) {
            final String arrayEntry = array + "[" + (count++) + "]";
            output.append(" + ");
            output.append(NameTable.getInstance().add(arrayEntry));
            output.append("*");
            output.append(it.next().toString());
        }
        output.append(";\n");
    }
    
    public static void processMvFromStridedArray(final String command, StringBuffer output) throws RecognitionException, IOException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if (assignment == null)
            return;

        // print mv assignment
        output.append(Main.LINE_END);
        output.append(assignment.getVariable().getName()); // mv name
        output.append(" = 0");

        Iterator<Expression> it = ((MacroCall) assignment.getValue()).getArguments().iterator();
        final String array = it.next().toString();
        final String index = it.next().toString();
        final String stride = it.next().toString();
        int count = 0;
        while (it.hasNext()) {
            final String arrayEntry = array + "[(" + index + ") + "
                                    + (count++) + " * (" + stride + ")]";
            output.append(" + ");
            output.append(NameTable.getInstance().add(arrayEntry));
            output.append("*");
            output.append(it.next().toString());
        }
        output.append(";\n");
    }
    
    public static void processMvFromVector(final String command, StringBuffer output) throws RecognitionException, IOException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if (assignment == null)
            return;

        // print mv assignment
        output.append(Main.LINE_END);
        output.append(assignment.getVariable().getName()); // mv name
        output.append(" = 0");

        Iterator<Expression> it = ((MacroCall) assignment.getValue()).getArguments().iterator();
        final String vector = it.next().toString();
        int count = 0;
        while (it.hasNext()) {
            final String vectorEntry = vector + ".s" + Common.getOpenCLIndex(count++);
            output.append(" + ");
            output.append(NameTable.getInstance().add(vectorEntry));
            output.append("*");
            output.append(it.next().toString());
        }
        output.append(";\n");
    }
}
