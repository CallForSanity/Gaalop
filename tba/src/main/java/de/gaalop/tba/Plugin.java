package de.gaalop.tba;

import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import java.awt.Image;
import java.util.Observable;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;

/**
 * Implements the Table Based Approach as an OptimizationStrategyPlugin,
 * for using in Gaalop
 * 
 * @author christian
 */
public class Plugin extends Observable implements OptimizationStrategyPlugin {

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optMaxima = false;//true;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optOneExpressionRemoval = false;//true;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optConstantPropagation = true;//true;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optUnusedAssignments = true;//true;

        @ConfigurationProperty(type = Type.TEXT)
        public String maximaCommand = ProcessBuilderMaximaConnection.CMD_MAXIMA_LINUX;

        @ConfigurationProperty(type = Type.TEXT)
        public String algebra = "conf5d"; //make conformal 5d to the standard algebra

        public String getAlgebra() {
            return algebra;
        }

        public boolean isOptMaxima() {
            return optMaxima;
        }

        public boolean isOptConstantPropagation() {
            return optConstantPropagation;
        }

        public boolean isOptOneExpressionRemoval() {
            return optOneExpressionRemoval;
        }

        public boolean isOptUnusedAssignments() {
            return optUnusedAssignments;
        }

        public String getMaximaCommand() {
            return maximaCommand;
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
            return new TBAOptStrategy(this);
	}

}
