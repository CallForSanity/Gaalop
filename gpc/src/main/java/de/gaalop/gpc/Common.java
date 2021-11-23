package de.gaalop.gpc;

import de.gaalop.cfg.AssignmentNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;


/**
 *
 * @author Patrick Charrier
 */
public class Common {
        
    public static String formatBladeName(final String bladeName) {
        bladeName.replaceAll("1.0","1").replaceAll("1.0f","1");
        
        // remove whitespaces from blade
        StringTokenizer tokenizer = new StringTokenizer(bladeName," \t\n\r\f()");
        StringBuilder bladeBuffer = new StringBuilder();
        while(tokenizer.hasMoreTokens())
            bladeBuffer.append(tokenizer.nextToken());
        return bladeBuffer.toString();
    }
    
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
    
    public static AssignmentNode parseAssignment(final String line) throws IOException {

        ANTLRInputStream inputStream = new ANTLRInputStream(new StringReader(line));
        GPCLexer lexer = new GPCLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        GPCParser parser = new GPCParser(tokenStream);

        GPCMyVisitor visitor = new GPCMyVisitor();
        visitor.visit(parser.program());

        return visitor.assignment;
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
                final int statementPos = line.indexOf(GPCCommands.mvSearchString);
                
                if (statementPos >= 0) {
                    final String mvName = line.substring(statementPos
                            + GPCCommands.mvSearchString.length());
                    mvComponents.put(mvName, new HashMap<String,String>());
                }
            }

            // retrieve multivector component declarations
            {
                final int statementPos = line.indexOf(GPCCommands.mvCompSearchString);

                if (statementPos >= 0) {
                    final Scanner lineStream = new Scanner(line.substring(statementPos
                            + GPCCommands.mvCompSearchString.length()));

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
