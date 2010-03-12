package de.gaalop.clucalc.input;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Plugin class for the CluCalc code parser.
 */
public class Plugin implements CodeParserPlugin {

    private Log log = LogFactory.getLog(Plugin.class);

    private BufferedImage icon;

    public Plugin() {
        URL url = getClass().getResource("/de/gaalop/clucalc/icon.png");
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
        return "CluCalc";
    }

    @Override
    public String getDescription() {
        return "This plugin can parse files that contain a subset of CluScript V4.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }

    @Override
    public CodeParser createCodeParser() {
        return CluCalcCodeParser.INSTANCE;
    }
}
