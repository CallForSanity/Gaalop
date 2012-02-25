package de.gaalop.visualizerC;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.Notifications;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;
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

    @ConfigurationProperty(type = Type.FILEPATH)
    public String maximaCommand = ProcessBuilderMaximaConnection.CMD_MAXIMA_LINUX;

    @ConfigurationProperty(type = Type.DIRPATH)
    public String lwJglNativePath = "/usr/lib/jni/";

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
        return new VisCCodeGenerator(maximaCommand);
    }

    @Override
    public String getName() {
        return "Visualizer";
    }

    @Override
    public String getDescription() {
        return "This plugin visualizes cluscript code.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
    
    void notifyError(Throwable error) {
    	setChanged();
    	notifyObservers(new Notifications.Error(error));
    }

    public String getMaximaCommand() {
        return maximaCommand;
    }

    public void setMaximaCommand(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }

    public String getLwJglNativePath() {
        return lwJglNativePath;
    }

    public void setLwJglNativePath(String lwJglNativePath) {
        this.lwJglNativePath = lwJglNativePath;
    }
}
