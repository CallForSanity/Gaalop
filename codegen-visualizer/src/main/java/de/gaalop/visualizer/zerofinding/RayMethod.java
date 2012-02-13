/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer.zerofinding;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.*;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaDifferentiater;
import de.gaalop.visitors.CFGReplaceVisitor;
import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.ia_math.RealInterval;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author christian
 */
public class RayMethod implements ZeroFinder {
    
    private HashMap<String, LinkedList<Point3d>> points;
    
    private String maximaCommand;
    
    private LinkedList<AssignmentNode> nodes;

    public RayMethod(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(ControlFlowGraph in, HashMap<MultivectorComponent, Double> globalValues) {
        HashMap<MultivectorComponent, RealInterval> globalValuesI = new HashMap<MultivectorComponent, RealInterval>();
                for (MultivectorComponent mvC: globalValues.keySet())
                    globalValuesI.put(mvC, new RealInterval(globalValues.get(mvC)));        
        
        points = new HashMap<String, LinkedList<Point3d>>();
        
        int a = 5;
        float dist = 0.1f;

        float ox = -a;
        globalValuesI.put(new MultivectorComponent("_V_ox", 0), new RealInterval(ox));
        
        for (float oy = -a; oy <= a; oy += dist) {
            globalValuesI.put(new MultivectorComponent("_V_oy", 0), new RealInterval(oy));
            for (float oz = -a; oz <= a; oz += dist) {
                globalValuesI.put(new MultivectorComponent("_V_oz", 0), new RealInterval(oz));
                for (AssignmentNode node: nodes) 
                    isolation(new RealInterval(0, 2*a),globalValuesI, in, node.getVariable().getName());
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
    
    
    private void isolation(RealInterval t, HashMap<MultivectorComponent, RealInterval> values, ControlFlowGraph graph, String product) {
        values.put(new MultivectorComponent("_V_t", 0), t);
        IntervalEvaluater evaluater = new IntervalEvaluater(values);
        graph.accept(evaluater);
        
        RealInterval f = values.get(new MultivectorComponent(product,0));
        if (f.lo() <= 0 && 0 <= f.hi()) {
            RealInterval df = values.get(new MultivectorComponent(product+"D",0));
            if (df.lo() <= 0 && 0 <= df.hi()) {
                if (t.hi()-t.lo() > 0.05) {
                    double center = (t.lo()+t.hi())/2.0d;
                    isolation(new RealInterval(t.lo(), center), values, graph, product);
                    isolation(new RealInterval(center, t.hi()), values, graph, product);
                } else {
                    
                        if (!points.containsKey(product))
                            points.put(product, new LinkedList<Point3d>());
                        
                        points.get(product).add(new Point3d(
                                values.get(new MultivectorComponent("_V_ox", 0)).lo()+(t.lo()+t.hi())/2.0d, 
                                values.get(new MultivectorComponent("_V_oy", 0)).lo(), 
                                values.get(new MultivectorComponent("_V_oz", 0)).lo()
                                ));
                    
                     }
            } else {
                refinement(t, values, graph, product);
            }
        }

    }

    private void refinement(RealInterval t, HashMap<MultivectorComponent, RealInterval> values, ControlFlowGraph graph, String product) {
        MultivectorComponent pr = new MultivectorComponent(product, 0);
        boolean refine = true;
        while (refine) {
            
            double center = (t.lo()+t.hi())/2.0d;
            
            values.put(new MultivectorComponent("_V_t", 0), new RealInterval(t.lo()));
            
            IntervalEvaluater evaluater = new IntervalEvaluater(values);
            graph.accept(evaluater);
            double lo = values.get(pr).lo();
            
            
            values.put(new MultivectorComponent("_V_t", 0), new RealInterval(center));  
            
            graph.accept(evaluater);
            double ce = values.get(pr).lo();
            
            if (Math.abs(ce) <= 0.01) refine = false;
            if (t.hi()-t.lo() < 0.001) return;
        
            if (ce*lo < 0) 
                t = new RealInterval(t.lo(),center);
            else
                t = new RealInterval(center,t.hi());

        }
            
        if (!points.containsKey(product))
            points.put(product, new LinkedList<Point3d>());

        points.get(product).add(new Point3d(
                values.get(new MultivectorComponent("_V_ox", 0)).lo()+(t.lo()+t.hi())/2.0d, 
                values.get(new MultivectorComponent("_V_oy", 0)).lo(), 
                values.get(new MultivectorComponent("_V_oz", 0)).lo()
                ));

    }

    @Override
    public boolean isPositionVariable(String name) {
        if (name.equals("_V_ox")) return true;
        if (name.equals("_V_oy")) return true;
        if (name.equals("_V_oz")) return true;
        if (name.equals("_V_t")) return true;
        
        return false;
    }
    
}
