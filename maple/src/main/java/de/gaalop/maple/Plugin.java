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
import java.util.Observable;

/**
 * This class is the Plugin class for the maple plugin.
 */
public class Plugin extends Observable implements OptimizationStrategyPlugin {

	private static Log log = LogFactory.getLog(Plugin.class);

	private static MapleSimplifier simplifier = null;

	/** This is a configuration property and should not be modified. */
	@ConfigurationProperty
	public String mapleBinaryPath;

	/** This is a configuration property and should not be modified. */
	@ConfigurationProperty
	public String mapleJavaPath;

	/**
	 * Try guessing a default value for the maple Path on Windows
	 */
	public Plugin() {
        /*String maplePath = System.getenv("MAPLE");
        if(maplePath.length() != 0)
        {
            mapleBinaryPath = maplePath + "/bin.win/";
            mapleJavaPath = maplePath + "/java/";
        }
        mapleBinaryPath = "C:/Programme/Maple 12/bin.win/";
        mapleJavaPath = "C:/Programme/Maple 12/java/";*/
	}

	public void setMaplePathsByMapleBinaryPath(String mapleBinaryPath_)
	{
        mapleBinaryPath = mapleBinaryPath_;
        mapleJavaPath = mapleBinaryPath_ + "/../java";
	}

	public static void main(String[] args) {
		new Plugin().createOptimizationStrategy();
	}

	private synchronized MapleSimplifier getSimplifier() {
		if (!Maple.isInitialized()) {
			Maple.initialize(new File(mapleJavaPath), new File(mapleBinaryPath));
		}

		if (simplifier == null) {
			simplifier = new MapleSimplifier(this);
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
		return null; // To change body of implemented methods use File | Settings | File Templates.
	}

	void notifyProgress() {
		setChanged();
		notifyObservers();
	}

	void notifyStart() {
		setChanged();
		notifyObservers(Integer.valueOf(0));
	}

}
