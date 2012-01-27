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
    
    public static Integer numBlocks = -1;
    public static final String inputsVector = "inputsVector";
    public static final String tempMv = "tempmv";
    public static final String dot = "dot";
    private final Plugin plugin;
    
    GAPPOpenCLCodeGenerator(Plugin plugin) {
    	this.plugin = plugin;
    }
    
    public static String getVarName(final String mvName) {
        if(mvName.startsWith(tempMv) || mvName.startsWith(dot) || mvName.startsWith(inputsVector))          
            return mvName + "_" + numBlocks;
        else
            return mvName;
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
        // new block
        ++numBlocks;
        
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
