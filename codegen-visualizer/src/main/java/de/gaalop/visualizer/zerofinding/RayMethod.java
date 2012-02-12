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

    public RayMethod(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(ControlFlowGraph in, HashMap<MultivectorComponent, Double> globalValues) {
        HashMap<MultivectorComponent, RealInterval> globalValuesI = new HashMap<MultivectorComponent, RealInterval>();
                for (MultivectorComponent mvC: globalValues.keySet())
                    globalValuesI.put(mvC, new RealInterval(globalValues.get(mvC)));        
        
        points = new HashMap<String, LinkedList<Point3d>>();
        
        //prepare graph (understand _V_X=ox+t)
        
        LinkedList<AssignmentNode> products = prepareGraph(in);

        int a = 5;
        float dist = 0.1f;
        
        RealInterval t = new RealInterval(0, 2*a);
        
        float ox = -a;
        
        for (float oy = -a; oy <= a; oy += dist) 
            for (float oz = -a; oz <= a; oz += dist) 
                for (AssignmentNode product: products) 
                    isolation(new RealInterval(ox), new RealInterval(oy), new RealInterval(oz), t, globalValuesI, in, product.getVariable().getName());
            
        return points;
    }
    
    
    //return PRODUCT_S Multivector names
    private LinkedList<AssignmentNode> prepareGraph(ControlFlowGraph graph) {
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
        
        LinkedList<AssignmentNode> toDerive = new LinkedList<AssignmentNode>();
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
                toDerive.add(newNode);
                collect.get(s).getFirst().insertBefore(newNode);

                for (AssignmentNode node: collect.get(s)) 
                    graph.removeNode(node);
            }
  
        //differentiate with respect to t with the help of maxima = _V_PRODUCT_SD
        MaximaDifferentiater differentiater = new MaximaDifferentiater();
        LinkedList<AssignmentNode> derived;
        try {
            derived = differentiater.differentiate(toDerive, maximaCommand);
            ListIterator<AssignmentNode> listIt = toDerive.listIterator();
            for (AssignmentNode d: derived) {
                d.setVariable(new MultivectorComponent(d.getVariable().getName()+"D", 0));
                listIt.next().insertAfter(d);
            }
        } catch (OptimizationException ex) {
            Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RecognitionException ex) {
            Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
        }


        return toDerive;
    }
    
    
    private void isolation(RealInterval ox, RealInterval oy, RealInterval oz, RealInterval t, HashMap<MultivectorComponent, RealInterval> globalValuesI, ControlFlowGraph graph, String product) {
        HashMap<MultivectorComponent, RealInterval> values = new HashMap<MultivectorComponent, RealInterval>(globalValuesI);

        values.put(new MultivectorComponent("_V_ox", 0), ox);
        values.put(new MultivectorComponent("_V_oy", 0), oy);
        values.put(new MultivectorComponent("_V_oz", 0), oz);
        values.put(new MultivectorComponent("_V_t", 0), t);
        
        IntervalEvaluater evaluater = new IntervalEvaluater(values);
        graph.accept(evaluater);
        
        RealInterval f = values.get(new MultivectorComponent(product,0));
        if (f.lo() <= 0 && 0 <= f.hi()) {
            RealInterval df = values.get(new MultivectorComponent(product+"D",0));
            if (df.lo() <= 0 && 0 <= df.hi()) {
                if (t.hi()-t.lo() > 0.05) {
                    double center = (t.lo()+t.hi())/2.0d;
                    isolation(ox, oy, oz, new RealInterval(t.lo(), center), globalValuesI, graph, product);
                    isolation(ox, oy, oz, new RealInterval(center, t.hi()), globalValuesI, graph, product);
                } else {
                    
                        if (!points.containsKey(product))
                            points.put(product, new LinkedList<Point3d>());

                        points.get(product).add(new Point3d(ox.lo()+(t.lo()+t.hi())/2.0d, oy.lo(), oz.lo()));
                    
                     }
            } else {
                refinement(ox,oy,oz,t, globalValuesI, graph, product);
            }
        }

    }

    private void refinement(RealInterval ox, RealInterval oy, RealInterval oz, RealInterval t, HashMap<MultivectorComponent, RealInterval> globalValuesI, ControlFlowGraph graph, String product) {
        MultivectorComponent pr = new MultivectorComponent(product, 0);
        boolean refine = true;
        while (refine) {
            
            double center = (t.lo()+t.hi())/2.0d;
            HashMap<MultivectorComponent, RealInterval> values = new HashMap<MultivectorComponent, RealInterval>(globalValuesI);

            values.put(new MultivectorComponent("_V_ox", 0), ox);
            values.put(new MultivectorComponent("_V_oy", 0), oy);
            values.put(new MultivectorComponent("_V_oz", 0), oz);
            values.put(new MultivectorComponent("_V_t", 0), new RealInterval(t.lo()));
            
            IntervalEvaluater evaluater = new IntervalEvaluater(values);
            graph.accept(evaluater);
            
            HashMap<MultivectorComponent, RealInterval> values2 = new HashMap<MultivectorComponent, RealInterval>(globalValuesI);

            values2.put(new MultivectorComponent("_V_ox", 0), ox);
            values2.put(new MultivectorComponent("_V_oy", 0), oy);
            values2.put(new MultivectorComponent("_V_oz", 0), oz);
            values2.put(new MultivectorComponent("_V_t", 0), new RealInterval(center));  
            
            evaluater = new IntervalEvaluater(values2);
            graph.accept(evaluater);
            
            if (Math.abs(values2.get(pr).lo()) <= 0.01) refine = false;
            if (t.hi()-t.lo() < 0.001) return;
        
            if (values.get(pr).lo()*values2.get(pr).lo() < 0) 
                t = new RealInterval(t.lo(),center);
            else
                t = new RealInterval(center,t.hi());

        }
            
        if (!points.containsKey(product))
            points.put(product, new LinkedList<Point3d>());

        points.get(product).add(new Point3d(ox.lo()+(t.lo()+t.hi())/2.0d, oy.lo(), oz.lo()));

    }
    
}
