package de.gaalop.tba;

import java.awt.Image;
import java.util.Observable;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;

public class Plugin extends Observable implements OptimizationStrategyPlugin {

	@Override
	public String getDescription() {
		return "This plugin uses a table based approach to break up Geometric Algebra";
	}

	@Override
	public Image getIcon() {
            return null;
	}

	@Override
	public String getName() {
		return "TBA";
	}

	@Override
	public OptimizationStrategy createOptimizationStrategy() {
            return new TBAOptStat();
	}

}
