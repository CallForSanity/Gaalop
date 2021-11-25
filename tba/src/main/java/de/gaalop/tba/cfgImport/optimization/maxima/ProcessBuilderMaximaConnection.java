package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;
import de.gaalop.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Implements the Maxima connection using the ProcessBuilder
 * @author Christian Steinmetz
 */
public class ProcessBuilderMaximaConnection implements MaximaConnection {

    public String commandMaxima;
    public static final String CMD_MAXIMA_WINDOWS = "C:\\Program Files (x86)\\Maxima-5.24.0\\bin\\maxima.bat";
    public static final String CMD_MAXIMA_LINUX = "/usr/bin/maxima";
    private LoggingListenerGroup listeners;

    public ProcessBuilderMaximaConnection(String commandMaxima) {
        this.commandMaxima = commandMaxima;
        //this.loggingListeners = new LoggingListenerGroup();
    }
    
    @Override
    public void setProgressListeners(LoggingListenerGroup progressListeners) {
    	listeners = progressListeners;
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
                throw new OptimizationException("Maxima is not accessible. Please check the Maxima command in the Configurations panel or disable the usage of Maxima optimization.", null);
            }

            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

            listeners.logNote("Compiling with Maxima, please wait...", 0.0);
            int lastPercent = 0;
            
            String line;
            int progress = 0;
            while ((line = b.readLine()) != null) {
            	// only increase progress when maxima returns an input line
            	String pattern = "[(][%]i\\d*[)].*";
            	if(line.matches(pattern)) {
            		progress++;
            	}
                if (line.contains("quotient") && line.contains("zero")) {
                    Logger.getLogger(ProcessBuilderMaximaConnection.class.getName()).log(Level.INFO, "Quotient is zero. Aborting.");
                    return null;
                }
            	// simulating long task
            	/*
            	try {
                	TimeUnit.SECONDS.sleep(1);
                } catch(Exception e) {
                	System.out.println("exception: "+e.getMessage());
                }
                */
            	// maxima responds with the line sent and the optimized line
            	
            	// so now we must tell the status bar our progress
            	int numSteps = input.size()+1; // the counting starts with 1
            	if(numSteps <= 0) {
            		numSteps = 1;
            	}
            	
            	/*
            	System.out.println("received: "+line);
            	System.out.println(""+progress);
            	System.out.println(""+numSteps);
            	System.out.println();
            	*/
            	
            	// because input and output are separate steps
            	// numSteps = numSteps*2;
                
                int curPercent = (progress * 100) / numSteps;
                if (curPercent >= lastPercent+5) {
                    lastPercent = curPercent;
                    listeners.logNote("Compiling with Maxima, please wait...", (double)(((double)progress)/numSteps));
                }

                output.add(line);
            }
            
            listeners.logNote("Done", 1.0);
        	

            b.close();

            tmpFile.delete();


            return output;
        } catch (IOException ex) {
            Logger.getLogger(ProcessBuilderMaximaConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }
}
