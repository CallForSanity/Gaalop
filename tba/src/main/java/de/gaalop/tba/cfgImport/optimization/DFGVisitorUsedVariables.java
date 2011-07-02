package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christian
 */
public class DFGVisitorUsedVariables extends DFGTraversalVisitor {

    private LinkedList<VariableComponent> variables = new LinkedList<VariableComponent>();

    public LinkedList<VariableComponent> getVariables() {
        return variables;
    }

    @Override
    public void visit(Variable node) {
        try {
            throw new Exception("Variables aren't allowed in the imported graph");
        } catch (Exception ex) {
            Logger.getLogger(DFGVisitorUsedVariables.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visit(MultivectorComponent node) {
        variables.add(new VariableComponent(node.getName(), node.getBladeIndex(),node));
    }

}
