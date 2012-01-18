package de.gaalop.gappopencl;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;

import java.util.Set;
import java.util.Collections;
import java.nio.charset.Charset;

/**
 * This class facilitates C/C++ code generation.
 */
public class GAPPOpenCLCodeGenerator implements CodeGenerator {
    
    private final Plugin plugin;
    
    GAPPOpenCLCodeGenerator(Plugin plugin) {
    	this.plugin = plugin;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
        String code = generateCode(in);

        String filename = generateFilename(in);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        return Collections.singleton(sourceFile);
    }

    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop.c";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".c";
        }
        return filename;
    }

    /**
     * Generates source code for a control dataflow graph.
     *
     * @param in
     * @return
     */
    private String generateCode(ControlFlowGraph in) {
        // determine sizes of multivectors
        GAPPMvSizeVisitor mvSizeVisitor = new GAPPMvSizeVisitor();
        try {
        	in.accept(mvSizeVisitor);
        } catch (Throwable error) {
        	plugin.notifyError(error);
        }        
        
        // generate code
        GAPPOpenCLVisitor visitor = new GAPPOpenCLVisitor(mvSizeVisitor.getMvSizes());
        try {
        	in.accept(visitor);
        } catch (Throwable error) {
        	plugin.notifyError(error);
        }
        return visitor.getCode();
    }

}
