package de.gaalop;

import java.util.Observable;
import java.util.Observer;

/**
 * This interface must be implemented by all plugins that wish to provide GlobalSettings capabilities to
 * Gaalop.
 *
 * All implementing classes <em>MUST</em> be thread-safe.
 */
public interface GlobalSettingsStrategyPlugin extends Plugin {

    /**
     * Creates a new instance of this plugins GlobalSettings strategy.
     * @return A new GlobalSettingsStrategy instance. This instance can be shared only if it is thread-safe.
     */
    GlobalSettingsStrategy createGlobalSettingsStrategy();

    /**
     * Wrapper method for {@link Observable#addObserver(Observer)}. Since interfaces cannot extend {@link Observer}, this method
     * has to be implemented to call {@link Observable#addObserver(Observer)} or by handling an own list of observers.
     *
     * @param observer observer to be registered for this plugin
     */
    void addObserver(Observer observer);
}
