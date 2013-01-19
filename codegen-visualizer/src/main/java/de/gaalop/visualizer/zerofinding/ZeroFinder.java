package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.api.cfg.AssignmentNodeCollector;
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
    protected String maximaCommand;
    
    public LinkedList<AssignmentNode> graphNodes;
    
    public void setMaximaCommand(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }
    
    public final void loadGraph(ControlFlowGraph in) {
        AssignmentNodeCollector collector = new AssignmentNodeCollector();
        in.accept(collector);
        graphNodes = collector.getAssignmentNodes();
    }

    public abstract void prepareGraph(ControlFlowGraph in);
    
    public abstract HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, boolean findOnlyIn2d);

    public abstract boolean isPositionVariable(String name);

    public abstract boolean isRayMethod();

}
