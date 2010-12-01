package de.gaalop.maple;

import com.sun.jna.Platform;
import de.gaalop.ConfigurationProperty;
import de.gaalop.Notifications;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import de.gaalop.ConfigurationProperty.Type;
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

	private int progress, max;

	/** This is a configuration property and should not be modified. */
	@ConfigurationProperty(type = Type.TEXT)
	public String mapleBinaryPath;

	/** This is a configuration property and should not be modified. */
	@ConfigurationProperty(type = Type.TEXT)
	public String mapleJavaPath;

	@ConfigurationProperty(type = Type.BOOLEAN)
	public boolean constantFolding = false;

	/**
	 * Try guessing a default value for the maple Path on Windows
	 */
	public Plugin() {
		if (Platform.isWindows()) {
			try {
				Win32MapleFinder finder = new Win32MapleFinder();
				String maplePath = finder.getMaplePathFromRegistry();
				mapleBinaryPath = maplePath + "\\bin.win\\";
				mapleJavaPath = maplePath + "\\java\\";
				log.info("Maple Path from Windows Registry: " + "binary=\"" + mapleBinaryPath + "\", java=\""
						+ mapleJavaPath + "\"");
			} catch (FileNotFoundException e) {
				log.info("Unable to find Maple in the Windows registry.");
			}
		} else if (Platform.isMac()) {
			String maplePath = "/Library/Frameworks/Maple.framework/Versions/13";
			mapleBinaryPath = maplePath + "/bin.APPLE_UNIVERSAL_OSX/";
			mapleJavaPath = maplePath + "/java/";
		} else if (Platform.isLinux()) {
			String maplePath = "/net/bifur/gaalop/maple";
			mapleJavaPath = maplePath + "/java/";
			mapleBinaryPath = maplePath + "/bin.IBM_INTEL_LINUX/";

		}
	}

	public static void main(String[] args) {
		new Plugin().createOptimizationStrategy();
	}

	private synchronized MapleSimplifier getSimplifier() {
		if (!Maple.isInitialized()) {
			try {
				Maple.initialize(new File(mapleJavaPath), new File(mapleBinaryPath));
			} catch (NullPointerException e) {
				setChanged();
				notifyObservers(new Notifications.Error(new IllegalArgumentException(
						"Unable to find Maple installation directory.")));
			}
		}

		if (simplifier == null) {
			simplifier = new MapleSimplifier(this);
		}

		return simplifier;
	}

	/**
	 * Needed for BeanUtils to write configuration file.
	 * 
	 * @return the mapleBinaryPath
	 */
	public String getMapleBinaryPath() {
		return mapleBinaryPath;
	}

	/**
	 * Needed for BeanUtils to read configuration file.
	 * 
	 * @param mapleBinaryPath the mapleBinaryPath to set
	 */
	public void setMapleBinaryPath(String mapleBinaryPath) {
		this.mapleBinaryPath = mapleBinaryPath;
	}
	
	/**
	 * Needed for BeanUtils to read configuration file.
	 * 
	 * @param mapleBinaryPath_ the mapleBinaryPath to set
	 */
	public void setMaplePathsByMapleBinaryPath(String mapleBinaryPath_)
	{
        mapleBinaryPath = mapleBinaryPath_;
        mapleJavaPath = mapleBinaryPath_ + "/../java";
	}

	/**
	 * Needed for BeanUtils to write configuration file.
	 * 
	 * @return the mapleJavaPath
	 */
	public String getMapleJavaPath() {
		return mapleJavaPath;
	}

	/**
	 * Needed for BeanUtils to read configuration file.
	 * 
	 * @param mapleJavaPath the mapleJavaPath to set
	 */
	public void setMapleJavaPath(String mapleJavaPath) {
		this.mapleJavaPath = mapleJavaPath;
	}

	/**
	 * Needed for BeanUtils to write configuration file.
	 * 
	 */
	public boolean getConstantFolding() {
		return constantFolding;
	}

	/**
	 * Needed for BeanUtils to read configuration file.
	 * 
	 */
	public void setConstantFolding(boolean constantFolding) {
		this.constantFolding = constantFolding;
	}

	@Override
	public OptimizationStrategy createOptimizationStrategy() {
		return new MapleStrategy(getSimplifier(), constantFolding);
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

	void notifyMaximum(int max) {
		this.max = max;
		setChanged();
		notifyObservers(new Notifications.Number(max));
	}

	void notifyProgress() {
		progress++;
		int percent = progress * 100 / max;
		System.out.println("Optimizing... " + percent + "%");
		setChanged();
		notifyObservers(new Notifications.Progress());
	}

	void notifyStart() {
		progress = 0;
		setChanged();
		notifyObservers(new Notifications.Start());
	}

}
