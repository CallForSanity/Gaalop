package de.gaalop.tba;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import java.io.PrintWriter;

public class Plugin extends Observable implements OptimizationStrategyPlugin {

	@Override
	public String getDescription() {
		return "This plugin uses the table based approach to build a Geometric Algebra Parallelism Program (GAPP).";
	}

	@Override
	public Image getIcon() {
            return null;
		//ImageIcon icon = new ImageIcon("plugin_icon.png");
		//return icon.getImage();
	}

	@Override
	public String getName() {
		return "GAPP";
	}

	@Override
	public OptimizationStrategy createOptimizationStrategy() {
            return new GAPPOptStrat();
	}

}
