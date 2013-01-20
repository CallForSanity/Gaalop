package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import java.util.LinkedList;

/**
 * Represents a piece of code, i.e. a list of assignment nodes, 
 * with a name of the multivector, which is rendered in this piece of code
 * @author Christian Steinmetz
 */
public class CodePiece extends LinkedList<AssignmentNode> {
    
    public String nameOfMultivector;

}
