/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.gapp.visitor.PrettyPrint;

/**
 *
 * @author christian
 */
public class GAPPVisitor extends EmptyControlFlowVisitor {

    protected StringBuilder code = new StringBuilder();

    private boolean genericVisit(AssignmentNode node) {
        if (node.getGAPP() == null) return false;

        PrettyPrint printer = new PrettyPrint();
        node.getGAPP().accept(printer, null);
        code.append(printer.getResultString());
        return true;
    }

    private void printExpression(Expression expression) {
        SpecialJavaVisitor v = new SpecialJavaVisitor();
        expression.accept(v);
        code.append(v.getCode());
    }

    @Override
    public void visit(StartNode node) {
        super.visit(node);
    }

    @Override
    public void visit(AssignmentNode node) {
        code.append("//");printExpression(node.getVariable());
        code.append(" = ");printExpression(node.getValue());code.append(";\n");
        if (!genericVisit(node)) {
            printExpression(node.getValue()); code.append(";\n");
        }

        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        SpecialJavaVisitor v = new SpecialJavaVisitor();
        node.accept(v);
        code.append(v.getCode());
        code.append(";\n");
        super.visit(node);
    }

    @Override
    public void visit(IfThenElseNode node) {
        SpecialJavaVisitor v = new SpecialJavaVisitor();
        node.accept(v);
        code.append(v.getCode());
        code.append(";\n");
        super.visit(node);
    }

    @Override
    public void visit(BlockEndNode node) {
        super.visit(node);
    }

    @Override
    public void visit(LoopNode node) {
        super.visit(node);
    }

    @Override
    public void visit(BreakNode node) {
        super.visit(node);
    }

    @Override
    public void visit(Macro node) {
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(EndNode node) {
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        SpecialJavaVisitor v = new SpecialJavaVisitor();
        node.accept(v);
        code.append(v.getCode());
        code.append(";\n");
        super.visit(node);
    }

    public String getCode() {
        return code.toString();
    }

    

}
