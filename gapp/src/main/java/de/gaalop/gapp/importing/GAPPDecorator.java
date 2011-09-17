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

        // remember the name of the current destination multivector component
        String name = ((MultivectorComponent) node.getVariable()).getName();

        if (!createdGAPPVariables.contains(name)) {
            // create a new GAPPMultivector for destination variable, if it does not exists
            GAPPMultivector mv = createNewMultivector(name);
            gapp.addInstruction(new GAPPResetMv(mv));
            createdGAPPVariables.add(name);
        }

        // process expression
        ExpressionCollector collector = new ExpressionCollector();
        node.getValue().accept(collector);
        ParallelObject parallelObject = collector.getResultValue();

        // Create creas
        AssignmentCreator creator = new AssignmentCreator();
        parallelObject.accept(creator, node.getVariable());

        // Create gapp from creas
        gappCreator.createGAPPInstructions(creator.getAssignments());

        // go on in graph
        super.visit(node);
    }

    

}
