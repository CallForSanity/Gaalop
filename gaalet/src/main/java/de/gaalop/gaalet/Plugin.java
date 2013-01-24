package de.gaalop.gaalet;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserPlugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.net.URL;
import java.util.Observable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Plugin class for the CluCalc code parser.
 */
public class Plugin extends Observable implements CodeParserPlugin
{

    private Log log = LogFactory.getLog(Plugin.class);

    private BufferedImage icon;
    
    public Plugin() {
        URL url = getClass().getResource("icon.png");
        if (url != null) {
            try {
                icon = ImageIO.read(url);
            } catch (IOException e) {
                log.error("Unable to read plugin icon " + url);
            }
        } else {
            log.warn("Unable to find CluCalc plugin icon!");
        }
    }
    
    @Override
    public String getName() {
        return "Gaalet";
    }

    @Override
    public String getDescription() {
        return "...";
    }

    @Override
    public Image getIcon() {
        return icon;
    }

    @Override
    public CodeParser createCodeParser() {
    	GaaletCodeParser.INSTANCE.setPluginReference(this);
        return GaaletCodeParser.INSTANCE;
    }
        
    /**
     * Notifies this class' observers about new maximum number of assignments.
     * 
     * @param n
     */
	void setNumberOfAssignments(int n) {
		setChanged();
		notifyObservers(Integer.valueOf(n));
	}
}
