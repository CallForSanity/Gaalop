package de.gaalop.vis2d;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.Notifications;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class implements the Plugin interface for Gaalop.
 */
public class Plugin extends Observable implements CodeGeneratorPlugin {

    private Log log = LogFactory.getLog(Plugin.class);

    private Image icon;

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
        return new Vis2dCodeGen();
    }

    @Override
    public String getName() {
        return "Vis2d";
    }

    @Override
    public String getDescription() {
        return "This plugin visualizes cluscript code in 2d.";
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
