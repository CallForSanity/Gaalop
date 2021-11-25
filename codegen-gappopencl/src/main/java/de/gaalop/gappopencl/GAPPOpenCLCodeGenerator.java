package de.gaalop.gappopencl;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class facilitates C/C++ code generation.
 */
public class GAPPOpenCLCodeGenerator extends DefaultCodeGenerator {
    
    public static Integer numBlocks = -1;
    public static final String inputsVector = "inputsVector";
    public static final String tempMv = "tempmv";
    public static final String dot = "dot";
    private final Plugin plugin;
    
    GAPPOpenCLCodeGenerator(Plugin plugin) {
        super("c");
    	this.plugin = plugin;
    }
    
    public static String getVarName(final String mvName) {
        if(mvName.startsWith(tempMv) || mvName.startsWith(dot) || mvName.startsWith(inputsVector))          
            return mvName + "_" + numBlocks;
        else
            return mvName;
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
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
