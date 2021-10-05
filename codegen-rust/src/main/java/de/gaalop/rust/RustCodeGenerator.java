package de.gaalop.rust;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

public class RustCodeGenerator extends DefaultCodeGenerator {

    public RustCodeGenerator() {
        super("rs");
    }
    
    @Override
    protected String generateCode(ControlFlowGraph in) {
        RustVisitor visitor = new RustVisitor();
        in.accept(visitor);
        return visitor.getCode();
    }

}
