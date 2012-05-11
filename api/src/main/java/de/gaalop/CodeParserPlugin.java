package de.gaalop;

import java.util.Observable;
import java.util.Observer;

/**
 * For every CodeParser a plugin provides, it must provide a class implementing this interface that can create CodeParsers and
 * provides information to the user about itself.
 * 
 * Classes implementing this interface <em>MUST</em> be thread-safe.
 */
public interface CodeParserPlugin extends Plugin {

	/**
	 * Creates a new CodeParser instance.
	 * 
	 * @return A CodeParser instance. This may be a shared instance, if the CodeParser does not hold any state.
	 */
	CodeParser createCodeParser();

	/**
	 * Wrapper method for {@link Observable#addObserver(Observer)}. Since interfaces cannot extend {@link Observer}, this method
	 * has to be implemented to call {@link Observable#addObserver(Observer)} or by handling an own list of observers.
	 * 
	 * @param observer observer to be registered for this plugin
	 */
	void addObserver(Observer observer);
}
