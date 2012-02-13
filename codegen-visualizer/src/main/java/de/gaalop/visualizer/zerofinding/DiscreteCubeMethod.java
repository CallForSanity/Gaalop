/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.visualizer.zerofinding.ZeroFinder;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class DiscreteCubeMethod implements ZeroFinder {
    
    private static final double EPSILON = 1E-1;

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(ControlFlowGraph in, HashMap<MultivectorComponent, Double> globalValues) {
        HashMap<String, LinkedList<Point3d>> points = new HashMap<String, LinkedList<Point3d>>();

        int a = 5;
        float dist = 0.1f;

        Point3d p = new Point3d(0, 0, 0);
        for (float x = -a; x <= a; x += dist) {
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
                    in.accept(evaluater);

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
        
        return points;
    }

    @Override
    public void prepareGraph(ControlFlowGraph in) {
        //Do nothing
    }
    
    @Override
    public boolean isPositionVariable(String name) {
        if (name.equals("_V_X")) return true;
        if (name.equals("_V_Y")) return true;
        if (name.equals("_V_Z")) return true;
        
        return false;
    }
}
