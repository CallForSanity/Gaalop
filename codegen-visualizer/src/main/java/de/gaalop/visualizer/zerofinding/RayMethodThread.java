/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.ia_math.RealInterval;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class RayMethodThread extends Thread {
    
    private float fromOY_Incl;
    private float toOY_Excl;
    
    private int a ;
    private float dist;
    private ControlFlowGraph graph;
    
    private HashMap<MultivectorComponent, Double> globalValues;
    
    private LinkedList<AssignmentNode> nodes;
            
    
    public HashMap<String, LinkedList<Point3d>> points = new HashMap<String, LinkedList<Point3d>>();

    public RayMethodThread(float fromOY_Incl, float toOY_Excl, int a, float dist, ControlFlowGraph graph, HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> nodes) {
        this.fromOY_Incl = fromOY_Incl;
        this.toOY_Excl = toOY_Excl;
        this.a = a;
        this.dist = dist;
        this.graph = graph;
        this.globalValues = globalValues;
        this.nodes = nodes;
    }



    @Override
    public void run() {
        HashMap<MultivectorComponent, RealInterval> globalValuesI = new HashMap<MultivectorComponent, RealInterval>();
                for (MultivectorComponent mvC: globalValues.keySet())
                    globalValuesI.put(mvC, new RealInterval(globalValues.get(mvC)));        
                
        float ox = -a;
        globalValuesI.put(new MultivectorComponent("_V_ox", 0), new RealInterval(ox));
        
        for (float oy = fromOY_Incl; oy <= toOY_Excl; oy += dist) {
            globalValuesI.put(new MultivectorComponent("_V_oy", 0), new RealInterval(oy));
            for (float oz = -a; oz <= a; oz += dist) {
                globalValuesI.put(new MultivectorComponent("_V_oz", 0), new RealInterval(oz));
                for (AssignmentNode node: nodes) 
                    isolation(new RealInterval(0, 2*a),globalValuesI, graph, node.getVariable().getName());
            }
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
    
    
    
}
