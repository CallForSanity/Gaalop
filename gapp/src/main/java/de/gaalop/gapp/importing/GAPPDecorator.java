package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import java.util.HashSet;

/**
 * Decorates a Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecorator extends EmptyControlFlowVisitor {

    private HashSet<String> createdGAPPVariables = new HashSet<String>();

    private GAPPCreator gappCreator = new GAPPCreator(null);

    private GAPPMultivector createNewMultivector(String name) {
        return new GAPPMultivector(name);
    }

    @Override
    public void visit(AssignmentNode node) {
        // create a new GAPP object and set it as member
        GAPP gapp = new GAPP();
        node.setGAPP(gapp);
        gappCreator.setGapp(gapp);

        // remember the name of the current destination multivector component
        MultivectorComponent mvC = (MultivectorComponent) node.getVariable();
        String name = mvC.getName();

        if (!createdGAPPVariables.contains(name)) {
            // create a new GAPPMultivector for destination variable, if it does not exist
            GAPPMultivector mv = createNewMultivector(name);
            gapp.addInstruction(new GAPPResetMv(mv));
            createdGAPPVariables.add(name);
        }

        System.out.println();
        System.out.println(node.getVariable().toString());
        System.out.println(node.getValue().toString());


        // process expression
        ExpressionCollector collector = new ExpressionCollector();
        node.getValue().accept(collector);
        ParallelObject parallelObject = collector.getResultValue();

        System.out.println(parallelObject.toString());

        DotProductsFinder finder = new DotProductsFinder();
        DotProduct newDot = (DotProduct) parallelObject.accept(finder, null);
        if (newDot != null)
            parallelObject = newDot;
        System.out.println(parallelObject.toString());

        //process further: create GAPP from the ParallelObject data structure
        GAPPMultivectorComponent gMvC = new GAPPMultivectorComponent(mvC.getName(), mvC.getBladeIndex());
        
        parallelObject.accept(gappCreator, gMvC);

        // go further in graph
        super.visit(node);
    }

}
