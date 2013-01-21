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

    protected String maximaCommand;
    
    public LinkedList<AssignmentNode> graphNodes;
    
    public void setMaximaCommand(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }
    
    /**
     * Loads a graph into the internal representation, i.e. a list of assginmentnodes
     * @param graph The graph
     */
    public final void loadGraph(ControlFlowGraph graph) {
        AssignmentNodeCollector collector = new AssignmentNodeCollector();
        graph.accept(collector);
        graphNodes = collector.getAssignmentNodes();
    }
    
    /**
     * Finds the zero locations in a list of assignmentnodes, given a global values set
     * @param globalValues The set of global values
     * @param assignmentNodes The list of assignmentnodes
     * @param mapSettings A map with all settings for the zero finder
     * @return The map name of multivector to list of zero locations
     */
    public abstract HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> assignmentNodes, HashMap<String, String> mapSettings);

    /**
     * Returns the name of the zerofinding method
     * @return The name
     */
    protected abstract String getName();

    @Override
    public String toString() {
        return getName();
    }
    
    public abstract HashMap<String, String> getSettings();
    
}
