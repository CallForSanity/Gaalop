package de.gaalop.maple;

import com.sun.jna.Platform;
import de.gaalop.ConfigurationProperty;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import de.gaalop.maple.engine.Maple;
import de.gaalop.maple.engine.win32.Win32MapleFinder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class is the Plugin class for the maple plugin.
 */
public class Plugin implements OptimizationStrategyPlugin {

    private static Log log = LogFactory.getLog(Plugin.class);

    private static MapleSimplifier simplifier = null;

    @ConfigurationProperty()
    private String mapleBinaryPath;

    @ConfigurationProperty()
    private String mapleJavaPath;
    
    /**
     * Try guessing a default value for the maple Path on Windows
     */
    public Plugin() {
        if (Platform.isWindows()) {
            try {
                Win32MapleFinder finder = new Win32MapleFinder();
                String maplePath = finder.getMaplePathFromRegistry();
                mapleBinaryPath = maplePath + "/bin.win/";
                mapleJavaPath = maplePath + "/java/";
                log.info("Maple Path from Windows Registry: " +
                        "binary=\"" + mapleBinaryPath + "\", java=\"" + mapleJavaPath + "\"");
            } catch (FileNotFoundException e) {
                log.info("Unable to find Maple in the Windows registry.");
            }
        } else if (Platform.isMac()) {
          String maplePath = "/Library/Frameworks/Maple.framework/Versions/13";
          mapleBinaryPath = maplePath + "/bin.APPLE_UNIVERSAL_OSX/";
          mapleJavaPath = maplePath + "/java/";
        }
    }
    
    public static void main(String[] args) {
      new Plugin().createOptimizationStrategy();
    }

    private synchronized MapleSimplifier getSimplifier() {
        if (!Maple.isInitialized()) {
            Maple.initialize(new File(mapleJavaPath), new File(mapleBinaryPath));
        }

        if (simplifier == null) {
            simplifier = new MapleSimplifier();
        }

        return simplifier;
    }

    @Override
    public OptimizationStrategy createOptimizationStrategy() {
        return new MapleStrategy(getSimplifier());
    }

    @Override
    public String getName() {
        return "Maple";
    }

    @Override
    public String getDescription() {
        return "Optimizes the algorithm using the Maple computer algebra system.";
    }

    @Override
    public Image getIcon() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getMapleBinaryPath() {
        return mapleBinaryPath;
    }

    public void setMapleBinaryPath(String mapleBinaryPath) {
        this.mapleBinaryPath = mapleBinaryPath;
    }

    public String getMapleJavaPath() {
        return mapleJavaPath;
    }

    public void setMapleJavaPath(String mapleJavaPath) {
        this.mapleJavaPath = mapleJavaPath;
    }
}
