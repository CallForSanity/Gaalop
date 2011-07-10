package de.gaalop.tba;

import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import java.awt.Image;
import java.util.Observable;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;

public class Plugin extends Observable implements OptimizationStrategyPlugin {

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean gcd = false;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean maxima = false;

        public boolean isGcd() {
            return gcd;
        }

        public boolean isMaxima() {
            return maxima;
        }

        

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
        return new TBAOptStat(this);
	}

}
