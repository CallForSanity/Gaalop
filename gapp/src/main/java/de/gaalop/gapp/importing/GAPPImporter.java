package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.HashSet;

/**
 * The new GAPPImporter
 * @author Christian Steinmetz
 */
public class GAPPImporter extends EmptyControlFlowVisitor {

    private final int bladeCount;

    private GAPP curGAPP;
    private HashSet<String> createdGAPPVariables = new HashSet<String>();

    public GAPPImporter(int bladeCount) {
        this.bladeCount = bladeCount;
    }

    private GAPPMultivector createNewMultivector(String name) {
        return new GAPPMultivector(name, new GAPPValueHolder[bladeCount]);
    }

    @Override
    public void visit(AssignmentNode node) {
        // create a new GAPP object and set it as member
        curGAPP = new GAPP();
        node.setGAPP(curGAPP);

        // remember the current destination multivector component
        MultivectorComponent curDestMvComp = (MultivectorComponent) node.getVariable();

        if (!createdGAPPVariables.contains(curDestMvComp.getName())) {
            // create a new GAPPMultivector for destination variable
            GAPPMultivector mv = createNewMultivector(curDestMvComp.getName());
            curGAPP.addInstruction(new GAPPResetMv(mv));
            createdGAPPVariables.add(curDestMvComp.getName());
        }

        // process expression
        ExpressionCollector collector = new ExpressionCollector();
        node.getValue().accept(collector);

        //TODO do sth with returned ParallelObject resultValue

        // go on in graph
        super.visit(node);
    }

    private int curTmpIndex = 0;

    private GAPPVector createTMP() {
        curTmpIndex++;
        return new GAPPVector("sTmp"+curTmpIndex);
    }

}
