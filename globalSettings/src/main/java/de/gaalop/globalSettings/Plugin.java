package de.gaalop.globalSettings;

import de.gaalop.*;
import de.gaalop.ConfigurationProperty.Type;
import java.awt.Image;
import java.util.Observable;

/**
 * Sets the algebra on the Control Flow Graph
 * @author Christian Steinmetz
 */
public class Plugin extends Observable implements GlobalSettingsStrategyPlugin {
    
    @ConfigurationProperty(type=Type.FILEPATH)
    public String maximaCommand = "C:\\Program Files (x86)\\Maxima\\bin\\maxima.bat";
    
    /*
     * Please make sure this is disabled by default.
     * Some tests fail otherwise.
     */
    @ConfigurationProperty(type=Type.BOOLEAN)
    public boolean optMaxima = false;
    
    @ConfigurationProperty(type=Type.NUMBER)
    public int editorFontSize = 12;
    
    @ConfigurationProperty(type=Type.NUMBER)
    public int guiFontSize = 12;
    
    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean outputToNormalBase = false;

    @Override
    public GlobalSettingsStrategy createGlobalSettingsStrategy() {
        return new MyGlobalSettingsStrategy(this);
    }

    @Override
    public String getName() {
        return "Global Settings Plugin";
    }

    @Override
    public String getDescription() {
        return "This plugin sets the global settings";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    void notifyError(Throwable error) {
        setChanged();
        notifyObservers(new Notifications.Error(error));
    }

    public String getMaximaCommand() {
        return maximaCommand;
    }

    public void setMaximaCommand(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }

    public boolean isOptMaxima() {
        return optMaxima;
    }

    public void setOptMaxima(boolean optMaxima) {
        this.optMaxima = optMaxima;
    }

    public int getEditorFontSize() {
        return editorFontSize;
    }

    public void setEditorFontSize(int editorFontSize) {
        this.editorFontSize = editorFontSize;
    }

    public int getGuiFontSize() {
        return guiFontSize;
    }

    public void setGuiFontSize(int guiFontSize) {
        this.guiFontSize = guiFontSize;
    }

    public boolean isOutputToNormalBase() {
        return outputToNormalBase;
    }

    public void setOutputToNormalBase(boolean outputToNormalBase) {
        this.outputToNormalBase = outputToNormalBase;
    }

}
