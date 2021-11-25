package de.gaalop.gpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Christian Steinmetz
 */
public class FileMethods {
    
    public static final BufferedReader createFileInputStringStream(String fileName) {
        try {
            final FileInputStream fstream = new FileInputStream(fileName);
            final DataInputStream in = new DataInputStream(fstream);
            return new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            return null;
        }
    }

    public static BufferedWriter createFileOutputStringStream(String fileName) {
        try {
            final FileWriter fstream = new FileWriter(fileName);
            return new BufferedWriter(fstream);
        } catch (Exception e) {
            return null;
        }
    }
    
}
