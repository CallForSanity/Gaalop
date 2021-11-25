package de.gaalop.visualizer;

/**
 * Creates a differentiater
 * @author Christian
 */
public class DifferentiaterCreator {
    
    private boolean optMaxima;
    private String maximaCommand;

    public DifferentiaterCreator(boolean optMaxima, String maximaCommand) {
        this.optMaxima = optMaxima;
        this.maximaCommand = maximaCommand;
    }

    /**
     * Creates a differentiater
     * @return The differentiater
     */
    public Differentiater createDifferentiater() {
        return (optMaxima)
                ? new MaximaDifferentiater(maximaCommand)
                : new CFGDifferentiater();
    }
    
}
