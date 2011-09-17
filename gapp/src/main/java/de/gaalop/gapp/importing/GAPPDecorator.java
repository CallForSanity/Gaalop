package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPValueHolder;
import java.util.HashSet;

/**
 * Decorates a Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecorator extends EmptyControlFlowVisitor {

    private HashSet<String> createdGAPPVariables = new HashSet<String>();

    private GAPPCreator gappCreator;

    public GAPPDecorator(int bladecount) {
        gappCreator = new GAPPCreator(bladecount);
    }

    private GAPPMultivector createNewMultivector(String name) {
        return new GAPPMultivector(name, new GAPPValueHolder[gappCreator.getBladeCount()]);
    }

    @Override
    public void visit(AssignmentNode node) {
        // create a new GAPP object and set it as member
        GAPP gapp = new GAPP();
        node.setGAPP(gapp);
        gappCreator.setGapp(gapp);

        // remember the current destination multivector component
        MultivectorComponent curDestMvComp = (MultivectorComponent) node.getVariable();

        if (!createdGAPPVariables.contains(curDestMvComp.getName())) {
            // create a new GAPPMultivector for destination variable
            GAPPMultivector mv = createNewMultivector(curDestMvComp.getName());
            gapp.addInstruction(new GAPPResetMv(mv));
            createdGAPPVariables.add(curDestMvComp.getName());
        }

        // process expression
        ExpressionCollector collector = new ExpressionCollector();
        node.getValue().accept(collector);
        
        ParallelObject resultValue = collector.getResultValue();
        //TODO do sth with returned ParallelObject resultValue
        
        resultValue.accept(gappCreator, null);

        // go on in graph
        super.visit(node);
    }

    

}
