package de.gaalop;

/**
 * This interface must be implemented by all plugins that wish to provide optimization capabilities to
 * Gaalop.
 *
 * All implementing classes <em>MUST</em> be thread-safe.
 */
public interface OptimizationStrategyPlugin extends Plugin {

    /**
     * Creates a new instance of this plugins optimization strategy.
     * @return A new OptimizationStrategy instance. This instance can be shared only if it is thread-safe.
     */
    OptimizationStrategy createOptimizationStrategy();

}
