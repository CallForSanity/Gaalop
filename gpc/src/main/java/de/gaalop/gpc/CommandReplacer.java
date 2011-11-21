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
    
    public CommandReplacer(final String command,
                         final String commandName) throws IOException {
        // start cleaned command
        int pos = command.indexOf(commandName);
        StringBuffer cleanedLineBuffer = new StringBuffer();
        cleanedLineStart = command.substring(0, pos);
        
        // find params start
        pos += commandName.length();
        pos = command.indexOf('(', pos) + 1;
        
        // find params
        StringBuffer argsBuffer = new StringBuffer();
        int level = 1;
        while(true) {
            if(command.charAt(pos) == '(')
                ++level;
            else if(command.charAt(pos) == ')')
                --level;
            
            if(level == 0) {
                ++pos;
                break;
            }
            else
                argsBuffer.append(command.charAt(pos++));
        }
        
        // split into params
        commandParams = argsBuffer.toString().split(",");
        
        // trim params
        for(String param : commandParams)
            param.trim();
        
        // end cleanedLine
        cleanedLineEnd = command.substring(pos);
    }
    
    public String[] getCommandParams() {
        return commandParams;
    }
    
    public String replace(final String replacement) {
        return cleanedLineStart + replacement + cleanedLineEnd;
    }
}
