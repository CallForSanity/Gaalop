package de.gaalop.visualizer.engines;

import de.gaalop.visualizer.PointCloud;
import java.util.HashMap;

/**
 * Defines an abstract rendering engine,
 * which is able to render point clouds.
 * @author Christian Steinmetz
 */
public abstract class RenderingEngine {

    /**
     * Renders point clouds
     * @param clouds The clouds
     */
    public abstract void render(HashMap<String, PointCloud> clouds);

}
