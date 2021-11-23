package de.gaalop.clucalc.output;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;

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

    @ConfigurationProperty(type = Type.TEXT)
    public String suffix = "_opt";

    private Log log = LogFactory.getLog(Plugin.class);
    private Image icon;

    private CluCalcCodeGenerator codeGenerator;

    public Plugin() {
        URL url = getClass().getResource("/de/gaalop/clucalc/icon.png");
        if (url != null) {
            try {
                icon = ImageIO.read(url);
            } catch (IOException e) {
                log.error("Unable to read plugin icon " + url);
            }
        } else {
            log.warn("Unable to find GAALOPScript plugin icon!");
        }
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public CodeGenerator createCodeGenerator() {
        if (codeGenerator == null) {
            codeGenerator = new CluCalcCodeGenerator(suffix);
        }
        codeGenerator.setSuffix(suffix);
        return codeGenerator;
    }

    @Override
    public String getName() {
        return "GAALOPScript";
    }

    @Override
    public String getDescription() {
        return "This plugin generates GAALOPScripts.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
