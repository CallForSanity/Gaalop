package de.gaalop;

/**
 * For every CodeParser a plugin provides, it must provide a class implementing this interface
 * that can create CodeParsers and provides information to the user about itself.
 *
 * Classes implementing this interface <em>MUST</em> be thread-safe.
 */
public interface CodeParserPlugin extends Plugin {

    /**
     * Creates a new CodeParser instance.
     *
     * @return A CodeParser instance. This may be a shared instance, if the CodeParser does not
     * hold any state.
     */
    CodeParser createCodeParser();
}
