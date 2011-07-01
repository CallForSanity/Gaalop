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
import java.util.Set;

/**
 *
 * @author christian
 */
public class CFGImporter extends EmptyCFGVisitor {

    private DFGImporter dfgImporter = new DFGImporter();

    @Override
    public void visit(AssignmentNode node) {

        //TODO chs GAPP CFG Importing.
    }

    @Override
    public void visit(StoreResultNode node) {

    }

}
