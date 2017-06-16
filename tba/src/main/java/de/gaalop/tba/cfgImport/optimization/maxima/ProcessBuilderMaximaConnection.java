package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the Maxima connection using the ProcessBuilder
 * @author Christian Steinmetz
 */
public class ProcessBuilderMaximaConnection implements MaximaConnection {

    public String commandMaxima;
    public static final String CMD_MAXIMA_WINDOWS = "C:\\Program Files (x86)\\Maxima-5.24.0\\bin\\maxima.bat";
    public static final String CMD_MAXIMA_LINUX = "/usr/bin/maxima";

    public ProcessBuilderMaximaConnection(String commandMaxima) {
        this.commandMaxima = commandMaxima;
    }

    @Override
    public MaximaOutput optimizeWithMaxima(MaximaInput input) throws OptimizationException {
        try {
            File tmpFile = File.createTempFile("tbaMaxima", ".txt");

            PrintWriter out = new PrintWriter(tmpFile);
            for (String line : input) {
                out.println(line);
            }
            out.close();

            MaximaOutput output = new MaximaOutput();

            String path = tmpFile.getCanonicalPath();
            if (File.separatorChar == '\\') {
                path = path.replaceAll("\\\\", "\\\\\\\\");
            }

            Process p;
            try {
                ProcessBuilder builder = new ProcessBuilder(commandMaxima);
                p = builder.start();
                PrintWriter writer = new PrintWriter(p.getOutputStream());
                writer.println("batch(\""+path+"\");");
                writer.flush();
            } catch (Exception e) {
                tmpFile.delete();
                throw new OptimizationException("Maxima is not accessible. Please check the Maxima command in the Configurations panel or disable the usage of Maxima.", null);
            }

            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = b.readLine()) != null) {
                output.add(line);
            }

            b.close();

            tmpFile.delete();


            return output;
        } catch (IOException ex) {
            Logger.getLogger(ProcessBuilderMaximaConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }
}
