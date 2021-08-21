package de.gaalop.latex;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class represents the LaTeX code generation plugin for Gaalop.
 */
public class Plugin implements CodeGeneratorPlugin {

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
        return new LatexCodeGenerator();
    }

    @Override
    public String getName() {
        return "LaTeX";
    }

    @Override
    public String getDescription() {
        return "This plugin generates LaTeX code.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
