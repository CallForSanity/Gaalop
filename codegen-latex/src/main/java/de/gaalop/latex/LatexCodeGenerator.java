package de.gaalop.latex;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class facilitates LaTeX code generation.
 */
public class LatexCodeGenerator extends DefaultCodeGenerator {

    public LatexCodeGenerator() {
        super("tex");
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
        LatexVisitor visitor = new LatexVisitor();
        in.accept(visitor);
        return visitor.getCode();
    }

}
