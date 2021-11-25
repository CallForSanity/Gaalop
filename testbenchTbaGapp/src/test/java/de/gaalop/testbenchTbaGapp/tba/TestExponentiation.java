package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.api.cfg.AssignmentNodeCollector;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.Plugin;
import de.gaalop.testbenchTbaGapp.graphstorage.GraphStoragePlugin;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import de.gaalop.testbenchTbaGapp.tba.framework.TestDummy;
import de.gaalop.visitors.DFGTraversalVisitor;
import de.gaalop.visualizer.zerofinding.RayMethod;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class TestExponentiation {
    
    @Ignore //TODO chs change to @Ignore because of using maxima at this test
    public void testAssignmentCopy() {
        TBATestCase tC = new TBATestCase() {

            @Override
            public String getCLUScript() {
                return 
                    "?b2 = a*a;\n"+
                    "?b3 = a*a*a;\n"+
                    "?b4 = a*a*a*a;\n"+
                    "?b5 = a*a*a*a*a;\n";
            }

            @Override
            public void testOutputs(HashMap<Variable, Double> outputs) {
                
            }

            @Override
            public HashMap<Variable, Double> getInputValues() {
                return new HashMap<Variable, Double>();
            }

            @Override
            public String getAlgebraName() {
                return "cga";
            }
        };
        
        GraphStoragePlugin graphStoragePlugin = new GraphStoragePlugin();
        
        de.gaalop.globalSettings.Plugin globalPlugin = new de.gaalop.globalSettings.Plugin();
        globalPlugin.optMaxima = true;
        globalPlugin.maximaCommand = "C:\\Program Files (x86)\\Maxima\\bin\\maxima.bat";
        
        TestDummy.compileWithOptions(tC, globalPlugin, graphStoragePlugin);
        
        AssignmentNodeMapCollector collectorMap = new AssignmentNodeMapCollector();
        graphStoragePlugin.getGraph().accept(collectorMap);
        HashMap<Variable, Expression> map = collectorMap.getAssignmentNodes();
        
        
        assertFalse(hasExponations(map.get(new MultivectorComponent("b2", 0))));
        assertFalse(hasExponations(map.get(new MultivectorComponent("b3", 0))));
        assertTrue(hasExponations(map.get(new MultivectorComponent("b4", 0))));
        assertTrue(hasExponations(map.get(new MultivectorComponent("b5", 0))));
    }
    
    private boolean hasExponations(Expression e) {
        HasExponentiations h = new HasExponentiations();
        e.accept(h);
        return h.hasExpononation;
    }

    class HasExponentiations extends DFGTraversalVisitor {
        
        public boolean hasExpononation = false;

        @Override
        public void visit(Exponentiation node) {
            hasExpononation = true;
            super.visit(node);
        }
        
        
        
    }
    
    
}
