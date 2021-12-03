package de.gaalop.globalSettings;

import de.gaalop.GlobalSettingsStrategy;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;

/**
 *
 * @author Christian Steinmetz
 */
public class MyGlobalSettingsStrategy implements GlobalSettingsStrategy {
    
    private Plugin plugin;

    public MyGlobalSettingsStrategy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        graph.globalSettings.maximaCommand = plugin.maximaCommand;
        graph.globalSettings.optMaxima = plugin.optMaxima;
        graph.globalSettings.outputToNormalBase = plugin.outputToNormalBase;
    }

}
