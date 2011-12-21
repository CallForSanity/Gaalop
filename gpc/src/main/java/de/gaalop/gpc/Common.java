/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import antlr.RecognitionException;
import de.gaalop.cfg.AssignmentNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 *
 * @author Patrick Charrier
 */
public class Common {
        
    public static int findCommandEndPos(String command,int startPos) {
        startPos = Math.max(0, startPos);
        
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

    /*
     * Scan a generated block for multivectors and multivector components.
     */
    public static void scanBlock(Vector<String> gaalopOutFileVector, int gaalopFileNumber, Map<String, Map<String,String>> mvComponents) throws IOException {
        String line;
        BufferedReader gaalopOutFile = new BufferedReader(new StringReader(gaalopOutFileVector.get(gaalopFileNumber)));
        while ((line = gaalopOutFile.readLine()) != null) {
            // retrieve multivector declarations
            {
                final int statementPos = line.indexOf(Main.mvSearchString);
                
                if (statementPos >= 0) {
                    final String mvName = line.substring(statementPos
                            + Main.mvSearchString.length());
                    mvComponents.put(mvName, new HashMap<String,String>());
                }
            }

            // retrieve multivector component declarations
            {
                final int statementPos = line.indexOf(Main.mvCompSearchString);

                if (statementPos >= 0) {
                    final Scanner lineStream = new Scanner(line.substring(statementPos
                            + Main.mvCompSearchString.length()));

                    // read and save mvName mvBladeCoeffName bladeName
                    mvComponents.get(lineStream.next()).put(lineStream.next(), lineStream.next());
                }
            }
        }
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

}
