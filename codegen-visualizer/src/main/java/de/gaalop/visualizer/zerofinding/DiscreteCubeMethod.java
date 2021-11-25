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
 * @author Christian Steinmetz
 */
public class DiscreteCubeMethod extends ZeroFinder {

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> assignmentNodes, HashMap<String, String> mapSettings, boolean renderIn2d) {
        HashMap<String, LinkedList<Point3d>> points = new HashMap<String, LinkedList<Point3d>>();

        int a = Integer.parseInt(mapSettings.get("cubeEdgeLength"));
        float dist = Float.parseFloat(mapSettings.get("density"));
        double epsilon = Double.parseDouble(mapSettings.get("epsilon"));
        
        int processorCount = Runtime.getRuntime().availableProcessors();
        
        DiscreteCubeMethodThread[] threads = new DiscreteCubeMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            int from = (i*2*a)/processorCount - a;
            int to = ((i != processorCount-1) ? ((i+1)*2*a)/processorCount : 2*a) - a; 

            threads[i] = new DiscreteCubeMethodThread(from, to, a, dist, globalValues, assignmentNodes, epsilon, renderIn2d);
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
    public String getName() {
        return "Discrete Cube Method";
    }

    @Override
    public HashMap<String, String> getSettings() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("cubeEdgeLength", "5");
        result.put("density", "0.1");
        result.put("epsilon", "1E-1");
        return result;
    }
    
    
}
