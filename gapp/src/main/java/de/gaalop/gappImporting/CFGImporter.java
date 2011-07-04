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
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author christian
 */
public class CFGImporter extends EmptyControlFlowVisitor {

    private DFGImporter dfgImporter = new DFGImporter();

    private HashMap<Variable,GAPPMultivector> mapVariableGAPPMv = new HashMap<Variable, GAPPMultivector>();

    private GAPPMultivector createGAPPMvFromVariable(Variable variable) {
        return new GAPPMultivector(variable.getName());
    }

    private boolean containsGA(Expression expression) {
        ContainGAChecker checker = new ContainGAChecker();
        expression.accept(checker);
        return checker.isContainGA();
    }

    @Override
    public void visit(AssignmentNode node) {

        //TODO chs GAPP CFG Importing.

        if (containsGA(node.getValue())) {
            
            GAPP gapp = new GAPP();
            node.setGAPP(gapp);

            GAPPMultivector gappMv = createGAPPMvFromVariable(node.getVariable());
            gapp.addInstruction(new GAPPResetMv(gappMv));





        } else {
            //TODO MvExpressions


        }


        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        super.visit(node);
    }

}
