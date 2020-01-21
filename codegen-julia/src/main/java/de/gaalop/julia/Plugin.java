package de.gaalop.julia;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.ConfigurationProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Plugin implements CodeGeneratorPlugin {

    private Log log = LogFactory.getLog(Plugin.class);

    private Image icon;

    @ConfigurationProperty(type = ConfigurationProperty.Type.BOOLEAN)
    public boolean forGrassmann;

    public void setForGrassmann(boolean forGrassmann) {this.forGrassmann = forGrassmann;}
    public boolean getForGrassmann() {return this.forGrassmann;}

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
        return new JuliaCodeGenerator(getForGrassmann());
    }

    @Override
    public String getName() {
        return "Julia";
    }

    @Override
    public String getDescription() {
        return "This plugin generates Julia code.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}