/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Patrick Charrier
 */
public class Common {
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

}
