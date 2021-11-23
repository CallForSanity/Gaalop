package de.gaalop.codegenGapp;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.visitor.PrettyPrint;

/**
 * Implements a code generator for gapp members, 
 * which lists all GAPP members in a graph in a pretty-printed format
 * @author Christian Steinmetz
 */
public class GAPPCodeGenerator extends DefaultCodeGenerator {

    public GAPPCodeGenerator() {
        super("gapp");
    }
    
    @Override
    protected String generateCode(ControlFlowGraph in) {
        PrettyPrint printer = new PrettyPrint();
        in.accept(printer);
        return printer.getResultString();
    }
}
