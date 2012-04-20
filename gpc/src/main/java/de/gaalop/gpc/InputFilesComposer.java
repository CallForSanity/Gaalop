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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Patrick Charrier
 */
public class InputFilesComposer {

    public static List<String> readInputFile() throws Exception {
        // process input file
        // split into gaalop input files and save in memory
        List<String> gaalopInFileVector = new ArrayList<String>();
        StringBuffer globalImportBuffer = new StringBuffer();
        String line;
        final BufferedReader inputFile = Main.createFileInputStringStream(Main.inputFilePath);
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.gpcBegin)) {
                readGPCBlock(inputFile, globalImportBuffer, gaalopInFileVector);
            }
            else if(line.contains(Main.clucalcBegin))
                globalImportBuffer = readClucalcBlock(globalImportBuffer,
                                                      new StringBuffer(),
                                                      inputFile,
                                                      gaalopInFileVector);
        }

        return gaalopInFileVector;
    }

    protected static void readGPCBlock(final BufferedReader inputFile,
                                        StringBuffer globalImportBuffer,
                                        List<String> gaalopInFileVector) throws IOException, RecognitionException {
        StringBuffer gpcBlockImportBuffer = new StringBuffer();
        Map<String,Set<String>> mvBladeOutput = new HashMap<String,Set<String>>();
        StringBuilder commandBuffer = new StringBuilder();
        String line;
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.clucalcBegin)) { // start clucalc block
                readClucalcBlock(globalImportBuffer,
                                 gpcBlockImportBuffer,
                                 inputFile,
                                 gaalopInFileVector);
                // this line should not be part of the command buffer
                continue;
            } else if(line.contains(Main.gpcEnd)) // end gpc block
                break;
            
            // add line to command buffer
            commandBuffer.append(line).append(Main.LINE_END);
            // process command buffer
            processCommandBuffer(commandBuffer,
                                 gpcBlockImportBuffer,
                                 mvBladeOutput);
        }
        
        // add pragma outputs to InFile String
        final int lastIndex = gaalopInFileVector.size()-1;
        if(lastIndex >= 0) {
            StringBuffer finalInFile = new StringBuffer();
            
            // append mv blade outputs
            for(Entry<String,Set<String>> entry : mvBladeOutput.entrySet()) {
                finalInFile.append("//#pragma output ").append(entry.getKey());
                for(String bladeName : entry.getValue())
                    finalInFile.append(" ").append(bladeName);
                finalInFile.append(Main.LINE_END);
            }
            
            // append original file
            finalInFile.append(gaalopInFileVector.get(lastIndex));
            
            gaalopInFileVector.set(lastIndex,finalInFile.toString());
        }
    }

    protected static void processCommandBuffer(StringBuilder commandBuffer,
                                               StringBuffer gpcBlockImportBuffer,
                                               Map<String,Set<String>> mvBladeOutput) throws RecognitionException, IOException {
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
            
            processMvGetBladeCoeff(command,mvBladeOutput);
            if (command.contains(Main.gpcMvFromArray)) {
                processMvFromArray(command, gpcBlockImportBuffer);
            } else if (command.contains(Main.gpcMvFromStridedArray)) {
                processMvFromStridedArray(command, gpcBlockImportBuffer);
            } else if (command.contains(Main.gpcMvFromVector)) {
                processMvFromVector(command, gpcBlockImportBuffer);
            } else if (command.contains(Main.gpcMvToArray)) {
                processMvToArray(command, mvBladeOutput);
            } else if (command.contains(Main.gpcMvToStridedArray)) {
                processMvToStridedArray(command, mvBladeOutput);
            } else if (command.contains(Main.gpcMvToVector)) {
                processMvToVector(command, mvBladeOutput);
            }

            // we found a command end, remove command from buffer
            commandBuffer.delete(0, commandEndPos);
        }
    }

    protected static StringBuffer readClucalcBlock(StringBuffer globalImportBuffer,
                                                    StringBuffer gpcBlockImportBuffer,
                                                    final BufferedReader inputFile,
                                                    List<String> gaalopInFileVector) throws IOException {
        // found gaalop line
        StringBuilder gaalopInFileStream = new StringBuilder();
        // start with import statements
        gaalopInFileStream.append(globalImportBuffer.toString());
        gaalopInFileStream.append(gpcBlockImportBuffer.toString());
        // read until end of optimized file
        String line;
        while ((line = inputFile.readLine()) != null) {
            if (line.contains(Main.clucalcEnd))
                break;
            
            gaalopInFileStream.append(line);
            gaalopInFileStream.append(Main.LINE_END);
        }
        
        // warning, if we reach something illegal before clucalc end
        if (line == null)
            System.err.println("Warning: Reached end of file before #pragma clucalc end");
        else if (line.contains(Main.gpcEnd))
            System.err.println("Warning: Reached #pragma gpc end before #pragma clucalc end");
        else if (line.contains(Main.gpcBegin))
            System.err.println("Warning: Reached #pragma gpc end before #pragma clucalc end");
        else if (line.contains(Main.clucalcBegin))
            System.err.println("Warning: Reached #pragma clucalc begin before #pragma clucalc end");

        // add to vector
        gaalopInFileVector.add(gaalopInFileStream.toString());
        //System.out.println(gaalopInFileStream.toString());
        
        return new StringBuffer(); //globalImportBuffer; TODO fixme
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
    
    public static Set<String> getMvBladeOutputSet(Map<String,Set<String>> mvBladeOutput,
                                        String mv) {
        Set<String> set = mvBladeOutput.get(mv);
        if(set == null)
            set = new HashSet<String>();
        
        return set;
    }
    
    /*
     * Search for keywords in command and parse the command.
     * Handle mv_get_bladecoeff as a specieal case
     * because it may be embedded anywhere.
     */
    public static void processMvGetBladeCoeff(String command,
            Map<String,Set<String>> mvBladeOutput) throws IOException {
        // replace step by step
        CommandFunctionReplacer cp = new CommandFunctionReplacer(
                command,
                Main.gpcMvGetBladeCoeff);
        while (cp.isFound()) {
            final String mv = cp.getCommandParams()[0];
            Set<String> set = getMvBladeOutputSet(mvBladeOutput,mv);
            set.add(cp.getCommandParams()[1]);
            mvBladeOutput.put(mv, set);
            
            // search for further occurences
            cp = new CommandFunctionReplacer(
                    cp.getCleanedLineEnd(),
                    Main.gpcMvGetBladeCoeff);
        }
    }
    
    public static void processMvToArray(final String command,
                                        Map<String,Set<String>> mvBladeOutput) throws RecognitionException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if (assignment == null)
            return;
        
        Iterator<Expression> it = ((MacroCall) assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        Set<String> set = getMvBladeOutputSet(mvBladeOutput,mv);
        
        Iterator<Expression> it_copy = it;
        if(((MacroCall) assignment.getValue()).getArguments().size() == 2
           && it_copy.next().equals("_")) {
            
            set.add("1");
            
            set.add("e1");
            set.add("e2");
            set.add("e3");
            set.add("einf");
            set.add("e0");

            set.add("e1^e2");
            set.add("e1^e3");
            set.add("e1^einf");
            set.add("e1^e0");
            
            set.add("e2^e3");
            set.add("e2^einf");
            set.add("e2^e0");
            
            set.add("e3^einf");
            set.add("e3^e0");

            set.add("einf^e0");

            set.add("e1^e2^e3");
            set.add("e1^e2^einf");
            set.add("e1^e2^e0");
            set.add("e1^e3^einf");
            set.add("e1^e3^e0");
            set.add("e1^einf^e0");

            set.add("e2^e3^einf");
            set.add("e2^e3^e0");
            set.add("e2^einf^e0");
            set.add("e3^einf^e0");

            set.add("e1^e2^e3^einf");
            set.add("e1^e2^e3^e0");
            set.add("e1^e2^einf^e0");
            set.add("e1^e3^einf^e0");
            set.add("e2^e3^einf^e0");

            set.add("e1^e2^e3^einf^e0");
            
            mvBladeOutput.put(mv, set);
            return;
        }
        
        // add blades
        while (it.hasNext()) {
            set.add(Common.formatBladeName(it.next().toString()));
        }
        
        mvBladeOutput.put(mv, set);
    }

    public static void processMvToStridedArray(final String command,
                                                Map<String,Set<String>> mvBladeOutput) throws RecognitionException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if (assignment == null) {
            return;
        }

        // print pragma output
        Iterator<Expression> it = ((MacroCall) assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        Set<String> set = getMvBladeOutputSet(mvBladeOutput,mv);
        it.next(); // index
        it.next(); // stride

        // add blades
        while (it.hasNext()) {
            set.add(Common.formatBladeName(it.next().toString()));
        }
        
        mvBladeOutput.put(mv, set);
    }

    public static void processMvToVector(final String command,
                                        Map<String,Set<String>> mvBladeOutput) throws RecognitionException {
        // parse assignment
        AssignmentNode assignment = Common.parseAssignment(command);
        if (assignment == null) {
            return;
        }

        Iterator<Expression> it = ((MacroCall) assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        Set<String> set = getMvBladeOutputSet(mvBladeOutput,mv);
        
        // add blades
        while (it.hasNext()) {
            set.add(Common.formatBladeName(it.next().toString()));
        }
        
        mvBladeOutput.put(mv, set);
    }
}
