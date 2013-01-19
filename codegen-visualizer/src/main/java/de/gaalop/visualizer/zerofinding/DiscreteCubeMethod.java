package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a zero finder method, which samples a cube
 * @author christian
 */
public class DiscreteCubeMethod extends ZeroFinder {

    @Override
    public boolean isPositionVariable(String name) {
        if (name.equals("_V_X")) return true;
        if (name.equals("_V_Y")) return true;
        if (name.equals("_V_Z")) return true;
        
        return false;
    }

    @Override
    public boolean isRayMethod() {
        return false;
    }

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> assignmentNodes) {
        HashMap<String, LinkedList<Point3d>> points = new HashMap<String, LinkedList<Point3d>>();

        int a = (int) cubeEdgeLength;
        float dist = density;
        
        int processorCount = Runtime.getRuntime().availableProcessors();
        
        DiscreteCubeMethodThread[] threads = new DiscreteCubeMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            int from = (i*2*a)/processorCount - a;
            int to = ((i != processorCount-1) ? ((i+1)*2*a)/processorCount : 2*a) - a; 

            threads[i] = new DiscreteCubeMethodThread(from, to, a, dist, globalValues, assignmentNodes);
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
}
