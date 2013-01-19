package de.gaalop.visualizer.zerofinding;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.*;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaDifferentiater;
import de.gaalop.visitors.CFGReplaceVisitor;
import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;

/**
 * Implements a zero finder method, which uses rays
 * @author christian
 */
public class RayMethod extends ZeroFinder {

    private HashMap<String, LinkedList<Point3d>> points;
    
    private String maximaCommand;
    
    private LinkedList<AssignmentNode> nodes;

    public RayMethod(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, boolean findOnlyIn2d) {
        points = new HashMap<String, LinkedList<Point3d>>();
        
        float a = cubeEdgeLength;
        float dist = density;
        
        int processorCount = Runtime.getRuntime().availableProcessors();
        
        RayMethodThread[] threads = new RayMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            float from = (i*2*a)/((float) processorCount) - a;
            float to = ((i != processorCount-1) ? ((i+1)*2*a)/((float) processorCount) : 2*a) - a; 

            threads[i] = new RayMethodThread(from, to, a, dist, in, globalValues, nodes, findOnlyIn2d);
            threads[i].start();
        }

        
        for (int i=0;i<threads.length;i++) {
            try {
                threads[i].join();
                for (String point: threads[i].points.keySet()) {
                    if (!points.containsKey(point))
                        points.put(point, threads[i].points.get(point));
                    else 
                        points.get(point).addAll(threads[i].points.get(point));
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(DiscreteCubeMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return points;
    }
    
    
    //return PRODUCT_S Multivector names
    @Override
    public void prepareGraph(ControlFlowGraph graph) {
        //replace x=ox+t,y=oy,z=oz
        CFGReplaceVisitor replacer = new CFGReplaceVisitor(new ReplaceVisitor() {

            private void visitVar(Variable node) {
                if (node.getName().equals("_V_X"))
                    result = new Addition(new MultivectorComponent("_V_ox", 0), new MultivectorComponent("_V_t", 0));
                if (node.getName().equals("_V_Y"))
                    result = new MultivectorComponent("_V_oy", 0);
                if (node.getName().equals("_V_Z"))
                    result = new MultivectorComponent("_V_oz", 0);
            }
            
            @Override
            public void visit(MultivectorComponent node) {
                visitVar(node);
            }

            @Override
            public void visit(Variable node) {
                visitVar(node);
            }
            
        });
        graph.accept(replacer);

        //search _V_PRODUCT and apply the sum of the squares = _V_PRODUCT_S
        HashMap<String, LinkedList<AssignmentNode>> collect = MultivectorNodeCollector.collect(graph);
        
        nodes = new LinkedList<AssignmentNode>();
        for (String s: collect.keySet()) 
            if (s.startsWith("_V_PRODUCT")) {
                Expression sumOfSquares = null; 
                
                for (AssignmentNode node: collect.get(s)) {
                    Expression square = new Multiplication(node.getValue(), node.getValue().copy());
                    
                    if (sumOfSquares == null) 
                        sumOfSquares = square;
                    else 
                        sumOfSquares = new Addition(sumOfSquares, square);
                }
                
                AssignmentNode newNode = new AssignmentNode(graph, new MultivectorComponent(s+"_S", 0), sumOfSquares);
                nodes.add(newNode);
                collect.get(s).getFirst().insertBefore(newNode);

                for (AssignmentNode node: collect.get(s)) 
                    graph.removeNode(node);
            }
  
        //Insert expressions, like Maxima,  !!!
        InsertingExpression.insertExpressions(graph);
        
        //differentiate with respect to t with the help of maxima = _V_PRODUCT_SD
        MaximaDifferentiater differentiater = new MaximaDifferentiater();
        LinkedList<AssignmentNode> derived;
        try {
            derived = differentiater.differentiate(nodes, maximaCommand);
            ListIterator<AssignmentNode> listIt = nodes.listIterator();
            for (AssignmentNode d: derived) {
                d.setVariable(new MultivectorComponent(d.getVariable().getName()+"D", 0));
                listIt.next().insertAfter(d);
            }
        } catch (OptimizationException ex) {
            Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RecognitionException ex) {
            Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean isPositionVariable(String name) {
        if (name.equals("_V_X")) return true;
        if (name.equals("_V_Y")) return true;
        if (name.equals("_V_Z")) return true;
        
        return false;
    }
    
}
