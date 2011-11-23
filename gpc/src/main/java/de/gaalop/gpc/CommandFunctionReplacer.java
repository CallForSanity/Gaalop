/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Replaces functions in commands.
 * @author pc
 */
public class CommandFunctionReplacer {
    private String[] commandParams;
    private String cleanedLineStart;
    private String cleanedLineEnd;
    private boolean found = false;
    
    public CommandFunctionReplacer(final String command,
                           final String commandName) throws IOException {
        
        // search command
        int pos = command.indexOf(commandName);
        if(!(found = (pos >= 0))) {
            cleanedLineStart = "";
            cleanedLineEnd = command;
            return;
        }

        // start cleaned 
        StringBuffer cleanedLineBuffer = new StringBuffer();
        cleanedLineStart = command.substring(0, pos);
        
        // find params start
        pos += commandName.length();
        pos = command.indexOf('(', pos) + 1;
        
        /*
         * Find params.
         * Since we do not know the end of the command beforehand,
         * we cannot parse the command directly.
         * Find end of function first.
         */
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
    
    public boolean isFound() {
        return found;
    }
    
    public String[] getCommandParams() {
        return commandParams;
    }
    
    public String getCleanedLineStart() {
        return cleanedLineStart;
    }
    
    public String getCleanedLineEnd() {
        return cleanedLineEnd;
    }
}
