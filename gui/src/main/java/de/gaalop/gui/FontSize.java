package de.gaalop.gui;

import de.gaalop.GlobalSettingsStrategyPlugin;
import de.gaalop.Plugins;

/**
 * Class for retrieving the global font size
 * @author Christian Steinmetz
 */
public class FontSize {
    
    /**
     * Returns the global font size
     * @return The font size
     */
    public static int getEditorFontSize() {
        de.gaalop.globalSettings.Plugin globalSettings = null;
        for (GlobalSettingsStrategyPlugin p: Plugins.getGlobalSettingsStrategyPlugins()) 
            if (p instanceof de.gaalop.globalSettings.Plugin)
                globalSettings = (de.gaalop.globalSettings.Plugin) p;
        return (globalSettings != null) ? globalSettings.getEditorFontSize() : 12;
    }
    
    /**
     * Returns the global font size
     * @return The font size
     */
    public static int getGuiFontSize() {
        de.gaalop.globalSettings.Plugin globalSettings = null;
        for (GlobalSettingsStrategyPlugin p: Plugins.getGlobalSettingsStrategyPlugins()) 
            if (p instanceof de.gaalop.globalSettings.Plugin)
                globalSettings = (de.gaalop.globalSettings.Plugin) p;
        return (globalSettings != null) ? globalSettings.getGuiFontSize() : 12;
    }
    
}
