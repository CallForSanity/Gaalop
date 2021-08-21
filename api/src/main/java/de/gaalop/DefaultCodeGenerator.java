package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

/**
 * Base class of a default code generator that can be used for easy creation of CodeGenerators.
 * All child classes have to call the constructor with the desired filename extension (e.g. c or java) and
 * implement the @generateCode() method.
 * 
 * @author Christian Steinmetz
 */
public abstract class DefaultCodeGenerator implements CodeGenerator {
    
    private final String filenameExtension;

    public DefaultCodeGenerator(String filenameExtension) {
        this.filenameExtension = filenameExtension;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph graph) throws CodeGeneratorException {
        String code = generateCode(graph);

        String filename = generateFilename(graph);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        return Collections.singleton(sourceFile);
    }
    
    protected String generateFilename(ControlFlowGraph graph) {
        String filename = "gaalop."+filenameExtension;
        if (graph.getSource() != null) {
            filename = graph.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += "."+filenameExtension;
        }
        return filename;
    }
    
    /**
     * Generates source code for a control flow graph
     * @param graph The control flow graph
     * @return The code
     */
    protected abstract String generateCode(ControlFlowGraph graph);
    
}
