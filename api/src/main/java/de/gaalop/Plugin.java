package de.gaalop;

import java.awt.*;

/**
 * This interface must be implemented by all plugins that wish to interact with the Gaalop infrastructure.
 *
 * Classes implementing this interface <em>MUST</em> be thread-safe.
 */
public interface Plugin {
    /**
     * Gets a human-readable name for this plugin. It is presented to users when they need
     * to select a code parser plugin.
     * @return A human readable string. Must not be null.
     */
    String getName();

    /**
     * Gets a human-readable description of this plugins purpose and scope. This is presented
     * as a longer description of a plugin to the user.
     * @return A human readable string that may contain newline characters. May be null.
     */
    String getDescription();

    /**
     * Gets an icon that can be used to represent this plugin. 16x16 Pixel icons should be used.
     * @return An Icon to represent this plugin or null if the plugin has no icon.
     */
    Image getIcon();
}
