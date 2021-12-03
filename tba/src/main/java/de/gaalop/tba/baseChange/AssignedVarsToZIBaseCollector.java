package de.gaalop.tba.baseChange;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.productComputer.AlgebraPC;
import java.util.HashSet;

/**
 * Filters all vars from a given variableNames set, that has at least on base element, that is mapped to the plus-minus-base,
 * e.g in CGA einf or e0.
 * The filtered set is stored in  the member "relevantVars".
 * @author Christian Steinmetz
 */
public class AssignedVarsToZIBaseCollector extends EmptyControlFlowVisitor {

    public HashSet<String> variableNames;
    public AlgebraDefinitionFile alFile;
    public AlgebraPC alPC;
    
    public HashSet<String> relevantVars = new HashSet<>();
    
    public AssignedVarsToZIBaseCollector(HashSet<String> variableNames, AlgebraDefinitionFile alFile, AlgebraPC alPC) {
        this.variableNames = variableNames;
        this.alFile = alFile;
        this.alPC = alPC;
    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = node.getVariable().getName();
        if (variableNames.contains(varName) && !relevantVars.contains(varName)) {
            if (node.getVariable() instanceof MultivectorComponent) {
                if (bladeIndexContainsBasisTransformation(((MultivectorComponent) node.getVariable()).getBladeIndex()))
                    relevantVars.add(varName);
            } else {
                // Should not be the case...
            }
        }
        super.visit(node); 
    }
    
    /**
     * Checks if the blade expression of a given blade index contains a base element, that is mapped to the plus-minus-base,
     * e.g in CGA einf or e0
     * @param bladeIndex The blade index to check
     * @return <value>true</value>, if the blade index contains such a base element, otherwise <value>false</value>
     */
    private boolean bladeIndexContainsBasisTransformation(int bladeIndex) {
        String bladeString = alFile.getBladeString(bladeIndex);
        bladeString = bladeString.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
        
        // split blade expression, e.g. e1^einf^e0 into base elements
        String[] parts = bladeString.split("\\^");
        for (String part: parts) 
            if (alPC.mapToPlusMinus.containsKey(part)) 
                return true;
        
        return false;
    }

}
