package de.gaalop.visitors;

import de.gaalop.cfg.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Prints an extended view of the Control Flow Graph.
 * 
 * Additional to the standard printout, the name of the node type is printed.
 * 
 * @author Christian Steinmetz
 */
public class CFGExtendedPrinter extends EmptyControlFlowVisitor {
    
    private PrintStream output;

    private CFGExtendedPrinter(OutputStream output) {
        this.output = new PrintStream(output);
    }
    
    public static void print(ControlFlowGraph graph, OutputStream output) {
        CFGExtendedPrinter printer = new CFGExtendedPrinter(output);
        graph.accept(printer);
        try {
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(CFGExtendedPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visit(StartNode node) {
        output.println("StartNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(AssignmentNode node) {
        output.println("AssignmentNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        output.println("StoreResultNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(IfThenElseNode node) {
        output.println("IfThenElseNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(BlockEndNode node) {
        output.println("BlockEndNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(LoopNode node) {
        output.println("LoopNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(BreakNode node) {
        output.println("BreakNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(Macro node) {
        output.println("MacroNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        output.println("ExpressionStatement: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(EndNode node) {
        output.println("EndNode: "+node.toString());
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        output.println("ColorNode: "+node.toString());
        super.visit(node);
    }
    
}
