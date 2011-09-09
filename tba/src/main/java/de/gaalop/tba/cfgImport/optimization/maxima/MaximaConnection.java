package de.gaalop.tba.cfgImport.optimization.maxima;

/**
 * Declares an interface for optimizing with maxima
 * @author Christian Steinmetz
 */
public interface MaximaConnection {

    /**
     * Sends the input to Maxima and returns the output of Maxima
     * @param input The input for Maxima
     * @return The output of Maxima
     */
    public MaximaOutput optimizeWithMaxima(MaximaInput input);

}
