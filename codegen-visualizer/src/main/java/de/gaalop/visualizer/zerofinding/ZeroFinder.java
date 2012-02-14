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
public interface ZeroFinder {
    
    public void prepareGraph(ControlFlowGraph in);
    
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(ControlFlowGraph in, HashMap<MultivectorComponent, Double> globalValues);

    public boolean isPositionVariable(String name);

}
