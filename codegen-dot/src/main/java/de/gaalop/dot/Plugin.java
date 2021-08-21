package de.gaalop.dot;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class implements the code generator plugin interface and expoeses the DOT code generator.
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
        return new DotCodeGenerator();
    }

    @Override
    public String getName() {
        return "Graphviz DOT";
    }

    @Override
    public String getDescription() {
        return "This plugin converts the algorithm to an optimized control dataflow grpah and exports it " +
                "in the Graphviz DOT file format.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
