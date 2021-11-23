package de.gaalop.gappopencl;

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

    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean standalone = true;

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

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }

    public boolean getStandalone() {
        return standalone;
    }

    @Override
    public CodeGenerator createCodeGenerator() {
        return new GAPPOpenCLCodeGenerator(this);
    }

    @Override
    public String getName() {
        return "GAPP OpenCL";
    }

    @Override
    public String getDescription() {
        return "This plugin generates C/C++ code.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }

    void notifyError(Throwable error) {
        setChanged();
        notifyObservers(new Notifications.Error(error));
    }
}
