package de.gaalop.clucalc.output;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.awt.*;

/**
 * This class implements the CodeGenerator part of the CluCalc plugin.
 */
public class Plugin implements CodeGeneratorPlugin {

    private Log log = LogFactory.getLog(Plugin.class);

    private Image icon;

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
    public CodeGenerator createCodeGenerator() {
        return CluCalcCodeGenerator.INSTANCE;
    }

    @Override
    public String getName() {
        return "CluCalc";
    }

    @Override
    public String getDescription() {
        return "This plugin generates CluCalc scripts.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
