package de.gaalop.mathematica;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

public class MathematicaCodeGenerator extends DefaultCodeGenerator {

    public MathematicaCodeGenerator() {
        super("txt");
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
        MathematicaVisitor visitor = new MathematicaVisitor();
        in.accept(visitor);
        return visitor.getCode();
    }

}
