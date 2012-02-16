package de.gaalop.visualizer;

import de.gaalop.visualizer.gui.DrawSettingsCodeGen;
import de.gaalop.dfg.MultivectorComponent;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implemtns a facade thread for finding points
 * @author christian
 */
public class FindPointsThread extends Thread {

    private HashMap<MultivectorComponent, Double> globalValues;
    
    public HashMap<String, LinkedList<Point3d>> pointsToRender;
    
    private DrawSettingsCodeGen draw;

    public FindPointsThread(HashMap<MultivectorComponent, Double> globalValues, DrawSettingsCodeGen draw) {
        this.globalValues = globalValues;
        this.draw = draw;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        pointsToRender = draw.finder.findZeroLocations(draw.graph, globalValues);

        draw.dataSet = new HashMap<String, PointCloud>();
        long sum = 0;
        for (String key : pointsToRender.keySet()) {
            sum += pointsToRender.get(key).size();
            //System.out.println(pointsToRender.get(key).size());
            String myKey = (key.endsWith("_S"))? key.substring(0, key.length()-2): key; 
            draw.dataSet.put(key, new PointCloud(draw.colors.get(myKey), pointsToRender.get(key)));
        }

        draw.available = true;  
        draw.jLabel_Info.setText(sum + " points, time = "+(System.currentTimeMillis()-start)/1000f+" s");
        draw.findingComplete();
    }

}
