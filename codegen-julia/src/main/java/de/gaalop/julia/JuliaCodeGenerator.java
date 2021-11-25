package de.gaalop.julia;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

public class JuliaCodeGenerator extends DefaultCodeGenerator {

    private boolean forGrassmannJL;

    public JuliaCodeGenerator(boolean forGrassmannJL) {
        super("txt");
        this.forGrassmannJL = forGrassmannJL;
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
        JuliaVisitor visitor = new JuliaVisitor(forGrassmannJL);
        in.accept(visitor);
        return visitor.getCode();
    }
}
