package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.StartNode;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.variables.GAPPValueHolder;
import java.util.HashSet;
import java.util.Set;

/**
 * Decorates a Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecorator extends EmptyControlFlowVisitor {

    private HashSet<String> createdGAPPVariables = new HashSet<String>();

    private GAPPBaseCreator gappCreator;

    private GAPP gappStart;

    public GAPPDecorator(int bladecount, GAPP gappStart) {
        gappCreator = new GAPPCreatorFull(null, bladecount);
        this.gappStart = gappStart;
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

        if (gappStart != null) {
            gapp.addGAPP(gappStart);
            gappStart = null;
        }

        // remember the name of the current destination multivector component
        MultivectorComponent mvC = (MultivectorComponent) node.getVariable();
        String name = mvC.getName();

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
        GAPPMultivectorComponent gMvC = new GAPPMultivectorComponent(mvC.getName(), mvC.getBladeIndex());
        parallelObject.accept(creator, gMvC);

        // Create gapp from creas
        gappCreator.createGAPPInstructionsFromAssignments(creator.getAssignments());

        // go on in graph
        super.visit(node);
    }

    

}
