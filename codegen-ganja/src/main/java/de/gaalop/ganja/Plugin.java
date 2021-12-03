package de.gaalop.ganja;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.Notifications;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;

/**
 * This class implements the Plugin interface for Gaalop.
 */
public class Plugin extends Observable implements CodeGeneratorPlugin {

    private Log log = LogFactory.getLog(Plugin.class);

    private Image icon;
    
    @ConfigurationProperty(type = Type.TEXT)
    public String outputMode = "standalone_page";

    public Plugin() {
        URL url = getClass().getResource("icon.png");
        if (url != null) {
            try {
                icon = ImageIO.read(url);
            } catch (IOException e) {
                log.error("Unable to read plugin icon " + url);
            }
        } else {
            log.warn("Unable to find plugin icon!");
        }
    }

    @Override
    public CodeGenerator createCodeGenerator() {
        return new GanjaCodeGenerator(this, OutputMode.fromString(outputMode));
    }

    @Override
    public String getName() {
        return "Ganja";
    }

    @Override
    public String getDescription() {
        return "This plugin generates Ganja code.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
    
    void notifyError(Throwable error) {
    	setChanged();
    	notifyObservers(new Notifications.Error(error));
    }

    public String getOutputMode() {
        return outputMode;
    }

    public void setOutputMode(String outputMode) {
        this.outputMode = outputMode;
    }
    
    
}
