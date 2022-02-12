package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;
import de.gaalop.dfg.Expression;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides methods for interacting with maxima
 * @author CSteinmetz15
 */
public class MaximaInteractor {
    
    private final String commandMaxima;
    
    private Process process;
    private BufferedReader bufferedReader;
    private PrintWriter writer;

    public MaximaInteractor(String commandMaxima) {
        this.commandMaxima = commandMaxima;
    }

    /**
     * Opens the connection with maxima
     * @throws OptimizationException 
     */
    public void openConnection() throws OptimizationException {
        try {
            ProcessBuilder builder = new ProcessBuilder(commandMaxima);
            process = builder.start();
            writer = new PrintWriter(process.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            initMaxima();
        } catch (Exception e) {
            process = null;
            bufferedReader = null;
            writer = null;
            throw new OptimizationException("Maxima is not accessible. Please check the Maxima command in the Configurations panel or disable the usage of Maxima optimization.", null);
        }
    }
    
    /**
     * Inits the maxima session
     * @throws IOException 
     */
    private void initMaxima() throws IOException {
        writer.println("display2d:false;"); // very important!
        writer.println("ratprint:false;"); // very important!
        writer.println("keepfloat:true;");
        writer.println("5.653;"); // very important to jump to first line after credits text of maxima!
        writer.flush();
        while (true) {
            String line = bufferedReader.readLine();
            if (line.contains("5.653"))
                break;
        }
    }
    
    /**
     * Closes the connection with maxima.
     * Tries the gentle way for 10 seconds. After this, it kills the maxima process hard.
     */
    public void closeConnection() {
        writer.println("quit();"); // very important!
        writer.flush();
        
        try {
            process.waitFor(10, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MaximaInteractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (process.isAlive()) {
            process.destroy();
        }
        
        process = null;
        bufferedReader = null;
        writer = null;
    }
    
    /**
     * Asks maxima, if an expression has the value 0.
     * @param expr The expression
     * @return true, if the expression is arithmetically equal to zero, otherwise false
     * @throws IOException 
     */
    public boolean detectZeroExpression(Expression expr) throws IOException {
        if (process == null) 
            throw new IllegalStateException("Maxima connection is not open!");
        
        // Write to Maxima
        DFGToMaximaCode dfgToMaximaCode = new DFGToMaximaCode();
        expr.accept(dfgToMaximaCode);
        String maximaInput = dfgToMaximaCode.getResultString();
        writer.println("is(equal(0,"+maximaInput+"));");
        writer.flush();

        // Read from Maxima
        while (true) {
            String maximaOutput = bufferedReader.readLine();

            if (maximaOutput.startsWith("(%i")) {
                maximaOutput = bufferedReader.readLine();
            }

            if (maximaOutput.startsWith("(%o")) {
                return "true".equals(maximaOutput.substring(maximaOutput.indexOf(")") + 2).trim().toLowerCase());
            }
        }
    }

}
