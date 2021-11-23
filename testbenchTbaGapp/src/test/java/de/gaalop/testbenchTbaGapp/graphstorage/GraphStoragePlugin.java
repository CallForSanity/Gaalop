package de.gaalop.testbenchTbaGapp.graphstorage;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Christian Steinmetz
 */
public class GraphStoragePlugin implements CodeGeneratorPlugin, CodeGenerator {
    
    private ControlFlowGraph graph;

    @Override
    public CodeGenerator createCodeGenerator() {
        return this;
    }

    @Override
    public String getName() {
        return "Graph storage";
    }

    @Override
    public String getDescription() {
        return "This plugin stores the graph.";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        graph = in;
        return new HashSet<OutputFile>();
    }
    
    public ControlFlowGraph getGraph() {
        return graph;
    }

}
