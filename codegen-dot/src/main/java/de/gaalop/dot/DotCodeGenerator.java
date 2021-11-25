package de.gaalop.dot;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;


/**
 * This class implements the DOT code generator.
 */
public class DotCodeGenerator extends DefaultCodeGenerator {

    public DotCodeGenerator() {
        super("dot");
    }
    
    @Override
    protected String generateCode(ControlFlowGraph in) {
        CfgVisitor visitor = new CfgVisitor();
        in.accept(visitor);
        return visitor.getResult();
    }
}
