package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;
import de.gaalop.LoggingListenerGroup;

/**
 * Declares an interface for optimizing with maxima
 * @author Christian Steinmetz
 */
public interface MaximaConnection {

    /**
     * Sends the input to Maxima and returns the output of Maxima
     * @param input The input for Maxima
     * @return The output of Maxima
     * @throws de.gaalop.OptimizationException
     */
    public MaximaOutput optimizeWithMaxima(MaximaInput input) throws OptimizationException;

    public void setProgressListeners(LoggingListenerGroup progressLogger);
}
