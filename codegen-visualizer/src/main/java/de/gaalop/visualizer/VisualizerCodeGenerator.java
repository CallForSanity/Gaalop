/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.visualizer;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.util.HashSet;
import java.util.Set;

/**
 * This class visualizes the graph
 * @author Christian Steinmetz
 */
public class VisualizerCodeGenerator implements CodeGenerator {

    private Plugin plugin;

    public VisualizerCodeGenerator(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        //TODO chs visualize
        return new HashSet<OutputFile>();
    }

}
