package de.gaalop.gapp.visitor;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;

import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.variables.GAPPVariableBase;
import java.util.HashMap;

/**
 * Implements a concrete vistor that exports a gapp programm in a given control-flow-graph
 * @author christian
 */
public class GraphExporter implements GAPPVisitor {

    private ControlFlowGraph graph;

    private HashMap<GAPPVariableBase,Variable> variables;

    public GraphExporter(ControlFlowGraph graph, HashMap<GAPPVariableBase,Variable> variables) {
        this.graph = graph;
        this.variables = variables;
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        for (int i = 0; i < gappAddMv.getSelectorsDest().size(); i++) {
            MultivectorComponent mvcDest = new MultivectorComponent(gappAddMv.getDestinationMv().getName(), gappAddMv.getSelectorsDest().get(i));
            graph.getEndNode().insertBefore(
                    new AssignmentNode(graph, mvcDest,
                    ExpressionFactory.sum(mvcDest, new MultivectorComponent(gappAddMv.getSourceMv().getName(), gappAddMv.getSelectorsSrc().get(i)))));
        }
        return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        for (int i = 0; i < gappAssignMv.getValues().size(); i++) {
            GAPPScalarVariable curVar = gappAssignMv.getValues().get(i);
            Expression value = (curVar.isConstant())
                    ? new FloatConstant(curVar.getValue())
                    : new Variable(curVar.getName());

            graph.getEndNode().insertBefore(
                    new AssignmentNode(graph,
                    new MultivectorComponent(gappAssignMv.getDestinationMv().getName(), gappAssignMv.getSelectors().get(i)),
                    value));
        }
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        MultivectorComponent mvComp = new MultivectorComponent(gappDotVectors.getDestination().getName(), gappDotVectors.getDestSelector());

        GAPPVector part1 = gappDotVectors.getPart1();
        GAPPVector part2 = gappDotVectors.getPart2();

        assert (part1.getSize() == part2.getSize());
        Expression sum;
        if (part1.getSize() > 0) {
            sum = ExpressionFactory.product(new MultivectorComponent(part1.getName(), 0), new MultivectorComponent(part2.getName(), 0));
            if ((part1.getComponent(0).getSign() == -1) ^ (part2.getComponent(0).getSign() == -1)) {
                sum = ExpressionFactory.subtract(new FloatConstant(0), sum);
            }

            for (int i = 1; i < part1.getSize(); i++) {
                if ((part1.getComponent(i).getSign() == -1) ^ (part2.getComponent(i).getSign() == -1)) {
                    sum = ExpressionFactory.subtract(sum, ExpressionFactory.product(new MultivectorComponent(part1.getName(), i), new MultivectorComponent(part2.getName(), i)));
                } else {
                    sum = ExpressionFactory.sum(sum, ExpressionFactory.product(new MultivectorComponent(part1.getName(), i), new MultivectorComponent(part2.getName(), i)));
                }
            }
        } else {
            sum = new FloatConstant(0);
        }

        graph.getEndNode().insertBefore(new AssignmentNode(graph, mvComp, sum));
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        graph.getEndNode().insertBefore(
                new AssignmentNode(graph,variables.get(gappResetMv.getDestinationMv()), new FloatConstant(0)));
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        for (int i = 0; i < gappSetMv.getSelectorsDest().size(); i++) {
            graph.getEndNode().insertBefore(
                    new AssignmentNode(graph,
                    new MultivectorComponent(gappSetMv.getDestinationMv().getName(), gappSetMv.getSelectorsDest().get(i)),
                    new MultivectorComponent(gappSetMv.getSourceMv().getName(), gappSetMv.getSelectorsSrc().get(i))));
        }
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        for (int i = 0; i < gappSetVector.getDestination().slots.size(); i++) {
            graph.getEndNode().insertBefore(
                    new AssignmentNode(
                        graph,
                        new MultivectorComponent(gappSetVector.getDestination().getName(),i),
                        variables.get(gappSetVector.getDestination().slots.get(i).getParent())
                    ));
        }
        return null;
    }

    public ControlFlowGraph getGraph() {
        return graph;
    }

    public void setGraph(ControlFlowGraph graph) {
        this.graph = graph;
    }
}
