package de.gaalop.gaalet.output;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.gaalet.NameTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class implements the Plugin interface for Gaalop.
 */
public class Plugin implements CodeGeneratorPlugin {

    private Log log = LogFactory.getLog(Plugin.class);

    private Image icon;
    
    //@ConfigurationProperty(type = Type.TEXT)
    private
    String variableType = "double";  
   
	/** This is a configuration property and should not be modified. */
	@ConfigurationProperty(type = Type.BOOLEAN)
	public boolean useNameTable;	    
    
    public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}
	
	public boolean isUseNameTable() {
		useNameTable = NameTable.isInUse();
		return useNameTable;
	}

	public void setUseNameTable(boolean useNameTable) {
		NameTable.setUse(useNameTable);
		this.useNameTable = useNameTable;
	}
	

	public Plugin() {
        URL url = getClass().getResource("/de/gaalop/gaalet/icon.png");
        if (url != null) {
            try {
                icon = ImageIO.read(url);
            } catch (IOException e) {
                log.error("Unable to read plugin icon " + url);
            }
        } else {
            log.warn("Unable to find plugin icon!");
        }
        
        useNameTable = false;
    }

    @Override
    public CodeGenerator createCodeGenerator() {
        return new CppCodeGenerator(this);
    }

    @Override
    public String getName() {
        return "C/C++ (gaalet)";
    }

    @Override
    public String getDescription() {
        return "This plugin generates C/C++ code putting the output data in a gaalet mulivector.";
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
