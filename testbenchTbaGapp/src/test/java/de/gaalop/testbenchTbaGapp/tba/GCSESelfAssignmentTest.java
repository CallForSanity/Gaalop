package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Implements a test for GCSE
 * @author CSteinmetz15
 */
public class GCSESelfAssignmentTest implements TBATestCase, GraphTestable {

    public GCSESelfAssignmentTest() {
    }

    @Override
    public String getCLUScript() {
        return "?a = c+4;"
             + "?b = c+4;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("b", 0)));
        assertEquals(17,outputs.get(new MultivectorComponent("a", 0)),0.01);
        assertEquals(17,outputs.get(new MultivectorComponent("b", 0)),0.01);
        assertEquals(2, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("c"), (double) 13);
        return result;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }

    @Override
    public void testGraph(ControlFlowGraph graph) {
        ControlFlowVisitor visitor = new EmptyControlFlowVisitor() {
            @Override
            public void visit(AssignmentNode node) {
                if (node.getVariable().equals(node.getValue())) {
                    Assert.fail("Self assignment of "+ node.getValue().toString()+" is not desired");
                }
                super.visit(node);
            }
            
        };
        graph.accept(visitor);
    }
    
}
