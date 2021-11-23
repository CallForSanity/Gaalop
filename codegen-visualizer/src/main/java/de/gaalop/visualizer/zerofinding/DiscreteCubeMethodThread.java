package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements a zero finder thread, which samples a cube
 * @author Christian Steinmetz
 */
public class DiscreteCubeMethodThread extends Thread {
    
    private double epsilon;
    
    private int fromX_Incl;
    private int toX_Excl;
    
    private int a;
    private float dist;
    private LinkedList<AssignmentNode> assignmentNodes;
    
    private HashMap<MultivectorComponent, Double> globalValues;
    
    private boolean renderIn2d;
    
    public HashMap<String, LinkedList<Point3d>> points = new HashMap<String, LinkedList<Point3d>>();

    public DiscreteCubeMethodThread(int fromX_Incl, int toX_Excl, int a, float dist, HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> assignmentNodes, double epsilon, boolean renderIn2d) {
        this.fromX_Incl = fromX_Incl;
        this.toX_Excl = toX_Excl;
        this.a = a;
        this.dist = dist;
        this.globalValues = globalValues;
        this.assignmentNodes = assignmentNodes;
        this.epsilon = epsilon;
        this.renderIn2d = renderIn2d;
    }
    
    @Override
    public void run() {
        if (renderIn2d)
            run2d();
        else 
            run3d();
    }
    
    private void run2d() {
        Point3d p = new Point3d(0, 0, 0);
        
        HashMap<MultivectorComponent, Double> globalValuesL = new HashMap<MultivectorComponent, Double>(globalValues);
        globalValuesL.put(new MultivectorComponent("_V_Z", 0), p.z);
        
        for (float x = fromX_Incl; x < toX_Excl; x += dist) {
            for (float y = -a; y <= a; y += dist) {

                    p.x = x;
                    p.y = y;

                    HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>(globalValuesL);
                    values.put(new MultivectorComponent("_V_X", 0), p.x);
                    values.put(new MultivectorComponent("_V_Y", 0), p.y);

                    Evaluater evaluater = new Evaluater(values);
                    evaluater.evaluate(assignmentNodes);

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
                        if (Math.sqrt(squaredAndSummedValues.get(key)) <= epsilon) {
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
    
    private void run3d() {
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
                    evaluater.evaluate(assignmentNodes);

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
                        if (Math.sqrt(squaredAndSummedValues.get(key)) <= epsilon) {
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
