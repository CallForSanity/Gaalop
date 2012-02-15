package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Definies an interface for an zero finding method
 * @author christian
 */
public abstract class ZeroFinder {
    
    public float cubeEdgeLength = 5f;
    public float density = 0.1f;

    public abstract void prepareGraph(ControlFlowGraph in);
    
    public abstract HashMap<String, LinkedList<Point3d>> findZeroLocations(ControlFlowGraph in, HashMap<MultivectorComponent, Double> globalValues);

    public abstract boolean isPositionVariable(String name);

}
