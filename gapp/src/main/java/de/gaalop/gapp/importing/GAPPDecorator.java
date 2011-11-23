package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.ParVariable;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.tba.Algebra;
import java.util.HashSet;

/**
 * Decorates a Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecorator extends EmptyControlFlowVisitor {

    private HashSet<String> createdGAPPVariables = new HashSet<String>();
    private GAPPCreator gappCreator;
    private GAPP gappStart;
    private int bladeCount;

    public GAPPDecorator(GAPP gappStart, HashSet<String> variables, int bladeCount, boolean scalarFunctions, Algebra algebra) {
        this.gappStart = gappStart;
        this.bladeCount = bladeCount;
        gappCreator = new GAPPCreator(variables, bladeCount, algebra);
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

        Variable variable = node.getVariable();

        String name = variable.getName();

        if (!createdGAPPVariables.contains(name)) {
            // create a new GAPPMultivector for destination variable, if it does not exist
            GAPPMultivector mv = new GAPPMultivector(name);
            gapp.addInstruction(new GAPPResetMv(mv, bladeCount));
            createdGAPPVariables.add(name);
        }

        // process expression
        ExpressionCollector collector = new ExpressionCollector();
        node.getValue().accept(collector);
        ParallelObject parallelObject = collector.getResultValue();


        DotProductsFinder finder = new DotProductsFinder();
        DotProduct newDot = (DotProduct) parallelObject.accept(finder, null);
        if (newDot != null) {
            parallelObject = newDot;
        }


        if (variable instanceof MultivectorComponent) {

            MultivectorComponent mvC = (MultivectorComponent) variable;

            //process further: create GAPP from the ParallelObject data structure
            GAPPMultivectorComponent gMvC = new GAPPMultivectorComponent(mvC.getName(), mvC.getBladeIndex());
            parallelObject.accept(gappCreator, gMvC);

        } else {
            // only a MathFunctionCall is possible
            //create gapp here
            ExtCalculation extCalculation = (ExtCalculation) parallelObject;

            gapp.addInstruction(new GAPPCalculateMv(extCalculation.getType(),
                    new GAPPMultivector(variable.getName()),
                    new GAPPMultivector(((ParVariable) extCalculation.getOperand1()).getName()),
                    (extCalculation.getOperand2() == null)
                    ? null
                    : new GAPPMultivector(((ParVariable) extCalculation.getOperand2()).getName())));

        }

        // go further in graph
        super.visit(node);
    }
}
