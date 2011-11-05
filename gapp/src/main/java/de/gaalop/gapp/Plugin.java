package de.gaalop.gapp;

import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.Notifications;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;
import java.awt.Image;
import java.util.Observable;

/**
 * Implements a plugin for decorating the Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class Plugin extends Observable implements OptimizationStrategyPlugin {

    @ConfigurationProperty(type = Type.TEXT)
    public String algebra = "5d"; //make conformal 5d to the standard algebra
    @ConfigurationProperty(type = Type.TEXT)
    public String maximaCommand = ProcessBuilderMaximaConnection.CMD_MAXIMA_LINUX;
    
    /*
     * Please make sure this is disabled by default.
     * Some tests fail otherwise.
     */
    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean optMaxima = false;

    @Override
    public OptimizationStrategy createOptimizationStrategy() {
        return new GAPPOptStrategy(this);
    }

    @Override
    public String getName() {
        return "GAPP";
    }

    @Override
    public String getDescription() {
        return "This plugin decorates the ControlFlowGraph with GAPP instructions";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    void notifyError(Throwable error) {
        setChanged();
        notifyObservers(new Notifications.Error(error));
    }

    public String getAlgebra() {
        return algebra;
    }

    public void setAlgebra(String algebra) {
        this.algebra = algebra;
    }

    public boolean isOptMaxima() {
        return optMaxima;
    }

    public void setOptMaxima(boolean optMaxima) {
        this.optMaxima = optMaxima;
    }

    public String getMaximaCommand() {
        return maximaCommand;
    }

    public void setMaximaCommand(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }

}
