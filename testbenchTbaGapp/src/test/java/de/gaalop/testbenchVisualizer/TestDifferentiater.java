package de.gaalop.testbenchVisualizer;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.api.cfg.AssignmentNodeCollector;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.clucalc.input.CluCalcCodeParser;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.CFGReplaceVisitor;
import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.visualizer.CFGDifferentiater;
import de.gaalop.visualizer.DFGDifferentiater;
import de.gaalop.visualizer.zerofinding.Evaluater;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian
 */
public class TestDifferentiater {
    
    public static ControlFlowGraph parseGraph(String cluscript) throws CodeParserException {
        CodeParser cp = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        ControlFlowGraph graph = cp.parseFile(new InputFile("test", cluscript));
        CFGReplaceVisitor rep = new CFGReplaceVisitor(new ReplaceVisitor() {

            @Override
            public void visit(Variable node) {
                if (node.getName().contains("_")) {
                    String[] parts = node.getName().split("_");
                    result = new MultivectorComponent(parts[0], Integer.parseInt(parts[1]));
                }
                super.visit(node);
            }
            
        });
        graph.accept(rep);
        return graph;
    }
    
    private LinkedList<AssignmentNode> diffCluScript(String cluscript, String mv) throws CodeParserException {
        String[] parts = mv.split("_");
        AssignmentNodeCollector col = new AssignmentNodeCollector();
        parseGraph(cluscript).accept(col);
        return new CFGDifferentiater().differentiate(col.getAssignmentNodes(), new MultivectorComponent(parts[0], Integer.parseInt(parts[1])));
    }
    
    @Test
    public void additionRConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = x_0*2+y_0;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(2, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void additionLConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0+x_0*2;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(2, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void additionBConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0+z_0*2;", "x_0");
        values.put(new MultivectorComponent("z", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void additionNoConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = x_0+x_0*2;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(3, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void subtractionRConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = x_0*2-y_0;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(2, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void subtractionLConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0-x_0*2;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(-2, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void subtractionBConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0-z_0*2;", "x_0");
        values.put(new MultivectorComponent("z", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void subtractionNoConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = x_0-x_0*2;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(-1, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void multiplicationRConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = (2*x_0+1)*y_0;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(6, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void multiplicationLConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0*(2*x_0+1);", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(6, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void multiplicationBConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0*z_0;", "x_0");
        values.put(new MultivectorComponent("z", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void multiplicationNoConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = (2*x_0+1)*(2*x_0+1);", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(20, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void divisionRConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = (2*x_0+1)/y_0;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(2.0/3, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void divisionLConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0/(2*x_0+1);", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(-6.0/25, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void divisionBConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = y_0/z_0;", "x_0");
        values.put(new MultivectorComponent("z", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void divisionNoConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = (2*x_0+1)/(4*x_0+1);", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(-2.0/81, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void exponentiationRConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        AssignmentNodeCollector col = new AssignmentNodeCollector();
        Expression v = new Exponentiation(
                new Addition(new Multiplication(new FloatConstant(2), new MultivectorComponent("x", 0)), new FloatConstant(1)), 
                new FloatConstant(8)
                );
        col.getAssignmentNodes().add(new AssignmentNode(null, new MultivectorComponent("out", 0), v));
        LinkedList<AssignmentNode> e = new CFGDifferentiater().differentiate(col.getAssignmentNodes(), new MultivectorComponent("x",0));
        
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(1250000, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void exponentiationLConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        AssignmentNodeCollector col = new AssignmentNodeCollector();
        Expression v = new Exponentiation(
                new FloatConstant(2),
                new Addition(new Multiplication(new FloatConstant(2), new MultivectorComponent("x", 0)), new FloatConstant(1))
                );
        col.getAssignmentNodes().add(new AssignmentNode(null, new MultivectorComponent("out", 0), v));
        LinkedList<AssignmentNode> e = new CFGDifferentiater().differentiate(col.getAssignmentNodes(), new MultivectorComponent("x",0));
        
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(64*Math.log(2), values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void exponentiationBConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        AssignmentNodeCollector col = new AssignmentNodeCollector();
        Expression v = new Exponentiation(new FloatConstant(6),new FloatConstant(7));
        col.getAssignmentNodes().add(new AssignmentNode(null, new MultivectorComponent("out", 0), v));
        LinkedList<AssignmentNode> e = new CFGDifferentiater().differentiate(col.getAssignmentNodes(), new MultivectorComponent("x",0));
        
        values.put(new MultivectorComponent("z", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void exponentiationNoConst() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        AssignmentNodeCollector col = new AssignmentNodeCollector();
        Expression v = new Exponentiation(
                new Addition(new Multiplication(new FloatConstant(2), new MultivectorComponent("x", 0)), new FloatConstant(1)), 
                new Addition(new Multiplication(new FloatConstant(2), new MultivectorComponent("x", 0)), new FloatConstant(1))
                );
        col.getAssignmentNodes().add(new AssignmentNode(null, new MultivectorComponent("out", 0), v));
        LinkedList<AssignmentNode> e = new CFGDifferentiater().differentiate(col.getAssignmentNodes(), new MultivectorComponent("x",0));
        
        values.put(new MultivectorComponent("x", 0), 2d);
        values.put(new MultivectorComponent("y", 0), 3d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(3125*(2*Math.log(5)+2), values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    //TODO chs Test MathFunctionCalls
    
    @Test
    public void variable() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = a;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void floatConstant() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = 5;", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void negationConstant() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = -(5+7);", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(0, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    @Test
    public void negationVariable() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = -(5*x_0);", "x_0");
        values.put(new MultivectorComponent("x", 0), 2d);
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(-5, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    
    //TODO chs Test Negative tests

    /* //Template
    @Test
    public void simple() throws CodeParserException {
        HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>();
        // Begin Inputs
        LinkedList<AssignmentNode> e = diffCluScript("?out_0 = ;", "x_0");
        values.put(new MultivectorComponent("x", 0), );
        // End Inputs
        Evaluater evaluater = new Evaluater(values);
        evaluater.evaluate(e);
        
        // Begin Tests
        assertEquals(, values.get(new MultivectorComponent("out", 0)),10E-3);  
        // End Tests
    }
    */
    
    
}
