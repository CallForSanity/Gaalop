package de.gaalop.tba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Provides IO methods for strings
 * @author christian
 */
public class FileMethods {

   /**
     * Reads a file in a string
     * @param file The file, which should be readed
     * @return The String, which contains the content of the given file
     * @throws IOException
     * @author Copied from de.gaalop.gui.OpenFileAction
     */
    public static String readFile(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
                result.append('\n');
            }
        } finally {
            in.close();
        }
        return result.toString();
    }

    /**
     * Writes a string in a file
     * @param file The file, which should be used for writing
     * @param content The content, which should have been written
     * @throws IOException
     */
    public static void writeFile(File file, String content) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.print(content);
        writer.close();
    }

}
