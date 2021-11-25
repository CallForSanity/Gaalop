package de.gaalop.vis2d;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class MultivectorBuilder extends EmptyControlFlowVisitor {
    
    private HashMap<String, Multivector> multivectors = new HashMap<String, Multivector>();
    
    public static HashMap<String, Multivector> buildMultivectors(ControlFlowGraph graph) {
        MultivectorBuilder builder = new MultivectorBuilder();
        graph.accept(builder);
        return builder.multivectors;
    }

    @Override
    public void visit(AssignmentNode node) {
        String name = node.getVariable().getName();
        Multivector mv;
        if (!multivectors.containsKey(name)) {
            mv = new Multivector(16);
            multivectors.put(name, mv);
        } else 
            mv = multivectors.get(name);
        
        MultivectorComponent mvC = (MultivectorComponent) node.getVariable();
        mv.entries[mvC.getBladeIndex()] = ((FloatConstant) node.getValue()).getValue();

        super.visit(node);
    }

}
