package de.gaalop.visualizer;

import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author christian
 */
public interface Rendering {
    
    public boolean isNewDataSetAvailable();
    
    public HashMap<String, PointCloud> getDataSet();
    
    public HashSet<String> getVisibleObjects();
    
}
