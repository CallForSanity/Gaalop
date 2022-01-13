package de.gaalop.gapp;

import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.Notifications;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OptimizationStrategyPlugin;
import java.awt.Image;
import java.util.Observable;

/**
 * Implements a plugin for decorating the Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class Plugin extends Observable implements OptimizationStrategyPlugin {
    
    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean useScalarFunctions = true;
    
    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean maximaExpand = false;

    public boolean isMaximaExpand() {
        return maximaExpand;
    }

    public void setMaximaExpand(boolean maximaExpand) {
        this.maximaExpand = maximaExpand;
    }

    public boolean isUseScalarFunctions() {
        return useScalarFunctions;
    }


    public void setUseScalarFunctions(boolean useScalarFunctions) {
        this.useScalarFunctions = useScalarFunctions;
    }

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

}
