package de.gaalop.tablebased;

import com.sun.jna.Platform;
import de.gaalop.ConfigurationProperty;
import de.gaalop.Notifications;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import de.gaalop.ConfigurationProperty.Type;
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

	private static TableBasedSimplifier simplifier = null;

	private int progress, max;

	/** This is a configuration property and should not be modified. */
	@ConfigurationProperty(type = Type.TEXT)
	public String mapleJavaPath;

	@ConfigurationProperty(type = Type.BOOLEAN)
	public boolean constantFolding = false;

	/**
	 * Try guessing a default value for the maple Path on Windows
	 */
	public Plugin() {
	}

	public static void main(String[] args) {
		new Plugin().createOptimizationStrategy();
	}

	private synchronized TableBasedSimplifier getSimplifier() {
		if (simplifier == null) {
			simplifier = new TableBasedSimplifier(this);
		}

		return simplifier;
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
		return new TableBasedStrategy(getSimplifier(), constantFolding);
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
