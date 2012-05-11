package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.Point3d;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class RenderingEngine extends Thread {

    public float pointSize = 0.5f;
    public LinkedList<Point3d> points;
    
}
