package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.api.cfg.AssignmentNodeCollector;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.graphstorage.GraphStoragePlugin;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import de.gaalop.testbenchTbaGapp.tba.framework.TestDummy;
import de.gaalop.visualizer.zerofinding.RayMethod;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class TestAssignmentCopy {
    
    @Test
    public void testAssignmentCopy() {
        TBATestCase tC = new TBATestCase() {

            @Override
            public String getCLUScript() {
                return 
                        "G = createEllipsoid(2,3,3,2,2,4);\n"+
                        "H = createCylinder(2,3,0,2);\n"+
                        ":S = (G^H);\n";
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
                return "qga";
            }
        };
        
        GraphStoragePlugin graphStoragePlugin = new GraphStoragePlugin();
        
        assertTrue(TestDummy.compile(tC, graphStoragePlugin));
        
        AssignmentNodeCollector collector = new AssignmentNodeCollector();
        graphStoragePlugin.getGraph().accept(collector);
        LinkedList<AssignmentNode> graphAssignmentNodes = collector.getAssignmentNodes();
        LinkedList<String> graphAssignmentNodesStr = new LinkedList<String>();
        for (AssignmentNode n: graphAssignmentNodes)
            graphAssignmentNodesStr.add(n.toString());
        String[] arr1 = graphAssignmentNodesStr.toArray(new String[0]);
        
        //Copy List
        LinkedList<AssignmentNode> list = new LinkedList<AssignmentNode>();
        for (AssignmentNode node: graphAssignmentNodes) 
            list.add(node.copyElements());
        RayMethod.replace(list);
        
        LinkedList<String> graphAssignmentNodesStr2 = new LinkedList<String>();
        for (AssignmentNode n: graphAssignmentNodes)
            graphAssignmentNodesStr2.add(n.toString());
        String[] arr2 = graphAssignmentNodesStr2.toArray(new String[0]);
        
        assertArrayEquals(arr1, arr2);
        
    }

}
