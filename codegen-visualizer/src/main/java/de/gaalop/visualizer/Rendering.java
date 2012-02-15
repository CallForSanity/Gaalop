package de.gaalop.visualizer;

import java.util.HashMap;


/**
 *
 * @author christian
 */
public interface Rendering {
    
    public boolean isNewDataSetAvailable();
    
    public HashMap<String, PointCloud> getDataSet();
    
}
