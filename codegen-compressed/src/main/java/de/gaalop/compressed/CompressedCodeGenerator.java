package de.gaalop.compressed;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class facilitates compressed C/C++ code generation.
 */
public class CompressedCodeGenerator extends DefaultCodeGenerator {
    
    private final Plugin plugin;
    
    public CompressedCodeGenerator(Plugin plugin) {
        super("cpp.g");
    	this.plugin = plugin;
    }

    @Override
    protected String generateCode(ControlFlowGraph graph) {
        MvSizeVisitor mvSizeVisitor = new MvSizeVisitor();
        try {
        	graph.accept(mvSizeVisitor);
        } catch (Throwable error) {
        	plugin.notifyError(error);
        }
        CompressedVisitor visitor = new CompressedVisitor(mvSizeVisitor.getMvSizes(), plugin.getStandalone(), plugin.getUseDouble());
        try {
        	graph.accept(visitor);
        } catch (Throwable error) {
        	plugin.notifyError(error);
        }
        return visitor.getCode();
    }

}
