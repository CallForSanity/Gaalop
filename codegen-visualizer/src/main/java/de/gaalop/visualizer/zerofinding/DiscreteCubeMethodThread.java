/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class DiscreteCubeMethodThread extends Thread {
    
    private static final double EPSILON = 1E-1;
    
    private int fromX_Incl;
    private int toX_Excl;
    
    private int a ;
    private float dist;
    private ControlFlowGraph graph;
    
    private HashMap<MultivectorComponent, Double> globalValues;
    
    public HashMap<String, LinkedList<Point3d>> points = new HashMap<String, LinkedList<Point3d>>();

    public DiscreteCubeMethodThread(int fromX_Incl, int toX_Excl, int a, float dist, HashMap<MultivectorComponent, Double> globalValues, ControlFlowGraph graph) {
        this.fromX_Incl = fromX_Incl;
        this.toX_Excl = toX_Excl;
        this.a = a;
        this.dist = dist;
        this.globalValues = globalValues;
        this.graph = graph;
    }
    
    @Override
    public void run() {
        Point3d p = new Point3d(0, 0, 0);
        for (float x = fromX_Incl; x < toX_Excl; x += dist) {
            for (float y = -a; y <= a; y += dist) {
                for (float z = -a; z <= a; z += dist) {

                    p.x = x;
                    p.y = y;
                    p.z = z;

                    HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>(globalValues);
                    values.put(new MultivectorComponent("_V_X", 0), p.x);
                    values.put(new MultivectorComponent("_V_Y", 0), p.y);
                    values.put(new MultivectorComponent("_V_Z", 0), p.z);

                    Evaluater evaluater = new Evaluater(values);
                    graph.accept(evaluater);

                    HashMap<String, Double> squaredAndSummedValues = new HashMap<String, Double>();
                    for (MultivectorComponent mvC : values.keySet()) {

                        String name = mvC.getName();
                        if (name.startsWith("_V_PRODUCT")) {
                            if (!squaredAndSummedValues.containsKey(name)) {
                                squaredAndSummedValues.put(name, new Double(0));
                            }

                            double value = values.get(mvC);
                            squaredAndSummedValues.put(name, squaredAndSummedValues.get(name) + value * value);
                        }
                    }
                    for (String key : squaredAndSummedValues.keySet()) {
                        if (Math.sqrt(squaredAndSummedValues.get(key)) <= EPSILON) {
                            //output point!
                            if (!points.containsKey(key)) {
                                points.put(key, new LinkedList<Point3d>());
                            }
                            points.get(key).add(new Point3d(p));
                        }
                    }
                }
            }
        }
    }
    
}
