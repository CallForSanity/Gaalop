/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author pc
 */
public class CommandReplacer {
    private int lineCount = 1;
    private String[] commandParams;
    private String cleanedLineStart;
    private String cleanedLineEnd;
    
    public CommandReplacer(String line,
                         final BufferedReader inputFile,
                         final String commandName) throws IOException {
        // start cleaned line
        int pos = line.indexOf(commandName);
        StringBuffer cleanedLineBuffer = new StringBuffer();
        cleanedLineStart = line.substring(0, pos);
        
        // find params start
        pos += commandName.length();
        pos = line.indexOf('(', pos) + 1;
        
        // find params
        StringBuffer argsBuffer = new StringBuffer();
        int level = 1;
        while(true) {
            if(line.charAt(pos) == '\n') {
                line = inputFile.readLine();
                ++lineCount;
                pos = 0;
            }
                        
            if(line.charAt(pos) == '(')
                ++level;
            else if(line.charAt(pos) == ')')
                --level;
            
            if(level == 0) {
                ++pos;
                break;
            }
            else
                argsBuffer.append(line.charAt(pos++));
        }
        
        // split into params
        commandParams = argsBuffer.toString().split(",");
        
        // trim params
        for(String param : commandParams)
            param.trim();
        
        // end cleanedLine
        cleanedLineEnd = line.substring(pos);
    }
    
    public int getLineCount() {
        return lineCount;
    }
    
    public String[] getCommandParams() {
        return commandParams;
    }
    
    public String replace(final String replacement) {
        return cleanedLineStart + replacement + cleanedLineEnd;
    }
}
