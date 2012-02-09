package de.gaalop.visualizer;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Defines an abstract rendering engine,
 * which is able to render point clouds with (different) colors.
 * @author Christian Steinmetz
 */
public abstract class RenderingEngine {

    /**
     * Renders point clouds
     * @param colors The colors
     * @param pointsToRender The points
     */
    public abstract void render(HashMap<String, Color> colors, HashMap<String, LinkedList<Point3d>> pointsToRender);

}
