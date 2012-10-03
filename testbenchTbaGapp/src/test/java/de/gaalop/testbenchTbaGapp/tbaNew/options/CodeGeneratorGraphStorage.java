package de.gaalop.testbenchTbaGapp.tbaNew.options;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Christian Steinmetz
 */
public class CodeGeneratorGraphStorage implements CodeGenerator {
    
    public ControlFlowGraph graph;

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        graph = in;
        return new HashSet<OutputFile>();
    }

}
