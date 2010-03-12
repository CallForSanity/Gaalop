package de.gaalop.maple.engine;

import java.io.InputStream;

/**
 * This interface defines what a Maple engine has to provide
 * to the Maple plugin.
 *
 * @author Sebastian
 */
public interface MapleEngine {
    /**
     * Evaluates a maple command and returns the resulting output.
     *
     * @param command The maple command that should be evaluated.
     * @return A string containing the output maple produced after the command was evaluated.
     */
    String evaluate(String command) throws MapleEngineException;

    /**
     * Resets this Maple engine for beginning a new calculation.
     */
    void reset() throws MapleEngineException;

    /**
     * Loads a module into this maple engine.
     *
     * @param stream An input stream that wraps the content of the module to be loaded. It is the
     *               responsibility of the caller to close this stream.
     */
    void loadModule(InputStream stream) throws MapleEngineException;
}
