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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        int processorCount = Runtime.getRuntime().availableProcessors();
        
        DiscreteCubeMethodThread[] threads = new DiscreteCubeMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            int from = (i*2*a)/processorCount - a;
            int to = ((i != processorCount-1) ? ((i+1)*2*a)/processorCount : 2*a) - a; 

            threads[i] = new DiscreteCubeMethodThread(from, to, a, dist, globalValues, in);
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
