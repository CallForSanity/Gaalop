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
 * Implements an empty ControlFlowGraph visitor
 * @author christian
 */
public class EmptyCFGVisitor implements ControlFlowVisitor {

    @Override
    public void visit(StartNode node) {

    }

    @Override
    public void visit(AssignmentNode node) {

    }

    @Override
    public void visit(StoreResultNode node) {
       
    }

    @Override
    public void visit(IfThenElseNode node) {
 
    }

    @Override
    public void visit(BlockEndNode node) {
       
    }

    @Override
    public void visit(LoopNode node) {
   
    }

    @Override
    public void visit(BreakNode node) {
   
    }

    @Override
    public void visit(Macro node) {
       
    }

    @Override
    public void visit(ExpressionStatement node) {
   
    }

    @Override
    public void visit(EndNode node) {
        
    }

    @Override
    public void visit(ColorNode node) {
     
    }

}
