/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;

/**
 *
 * @author christian
 */
public class CFGImporter implements ControlFlowVisitor {

    @Override
    public void visit(StartNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(AssignmentNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(StoreResultNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IfThenElseNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BlockEndNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LoopNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BreakNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Macro node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ExpressionStatement node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(EndNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ColorNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
