package de.gaalop;

/**
 * This interface must be implemented by all plugins that wish to provide Code Generation capabilities to
 * Gaalop.
 *
 * All implementing classes <em>MUST</em> be thread-safe.
 */
public interface CodeGeneratorPlugin extends Plugin {

    /**
     * Creates a new code generator instance.
     * @return A code generator instance. The returned object does not need to be unique, if it can be used by multiple
     * threads concurrently.
     */
    CodeGenerator createCodeGenerator();

}
