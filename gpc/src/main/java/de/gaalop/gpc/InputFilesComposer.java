/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick Charrier
 */
public class InputFilesComposer {

    public static List<String> readInputFile() throws IOException {
        // process input file
        // split into gaalop input files and save in memory
        List<String> gaalopInFileVector = new ArrayList<String>();
        String line;
        {
            final BufferedReader inputFile = Main.createFileInputStringStream(Main.inputFilePath);
            while ((line = inputFile.readLine()) != null) {
                // found gaalop line
                if (line.contains(Main.gpcBegin)) {
                    StringBuffer gaalopInFileStream = new StringBuffer();

                    // read until end of optimized file
                    while ((line = inputFile.readLine()) != null) {
                        if (line.contains(Main.gpcEnd)) {
                            break;
                        } else {
                            gaalopInFileStream.append(line);
                            gaalopInFileStream.append(Main.LINE_END);
                        }
                    }
                    
                    // warning, if we reached end of file before gpc end
                    if(line == null)
                        System.err.println("Warning: Reached end of file before +pragma gpc end");

                    // add to vector
                    gaalopInFileVector.add(gaalopInFileStream.toString());
                }
            }
        }
        return gaalopInFileVector;
    }

}
