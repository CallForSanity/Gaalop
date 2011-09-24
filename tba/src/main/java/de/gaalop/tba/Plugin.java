package de.gaalop.tba;

import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.Notifications;
import java.awt.Image;
import java.util.Observable;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;

/**
 * Implements the Table Based Approach as an OptimizationStrategyPlugin,
 * for using in Gaalop
 * 
 * @author Christian Steinmetz
 */
public class Plugin extends Observable implements OptimizationStrategyPlugin {

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optMaxima = true;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optOneExpressionRemoval = true;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optConstantPropagation = true;

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optUnusedAssignments = true;

        @ConfigurationProperty(type = Type.TEXT)
        public String maximaCommand = ProcessBuilderMaximaConnection.CMD_MAXIMA_LINUX;

        @ConfigurationProperty(type = Type.TEXT)
        public String algebra = "5d"; //make conformal 5d to the standard algebra

        @ConfigurationProperty(type = Type.BOOLEAN)
        public boolean optInserting = true;

       
        public boolean scalarFunctions = true;

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

        public boolean isOptInserting() {
            return optInserting;
        }

        public boolean isScalarFunctions() {
            return scalarFunctions;
        }

        public String getMaximaCommand() {
            return maximaCommand;
        }

        public void setAlgebra(String algebra) {
            this.algebra = algebra;
        }

        public void setMaximaCommand(String maximaCommand) {
            this.maximaCommand = maximaCommand;
        }

        public void setOptConstantPropagation(boolean optConstantPropagation) {
            this.optConstantPropagation = optConstantPropagation;
        }

        public void setOptMaxima(boolean optMaxima) {
            this.optMaxima = optMaxima;
        }

        public void setOptOneExpressionRemoval(boolean optOneExpressionRemoval) {
            this.optOneExpressionRemoval = optOneExpressionRemoval;
        }

        public void setOptUnusedAssignments(boolean optUnusedAssignments) {
            this.optUnusedAssignments = optUnusedAssignments;
        }

        public void setOptInserting(boolean optInserting) {
            this.optInserting = optInserting;
        }

        public void setScalarFunctions(boolean scalarFunctions) {
            this.scalarFunctions = scalarFunctions;
        }

	@Override
	public String getDescription() {
		return "This plugin uses a table based approach to optimize Geometric Algebra";
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

        void notifyError(Throwable error) {
            setChanged();
            notifyObservers(new Notifications.Error(error));
        }

}
