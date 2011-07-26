package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.UseAlgebra;

/**
 * Changes the control flow graph by building the mvExpressions.
 * This is the main transformer class for TBA plugin
 *
 * @author christian
 */
public class CFGImporter extends MvExpressionsBuilder {

    public CFGImporter(UseAlgebra usedAlgebra) {
        super(usedAlgebra);
    }

    @Override
    protected AssignmentNode changeGraph(AssignmentNode node, MvExpressions mvExpr, Variable variable) {
        AssignmentNode lastNode = node;

        // At first, output all assignments
        for (int i = 0; i < bladeCount; i++) {

            Expression e = mvExpr.bladeExpressions[i];

            if (e != null) {
                AssignmentNode insNode = new AssignmentNode(node.getGraph(), new MultivectorComponent(variable.getName(), i), e);

                lastNode.insertAfter(insNode);
                lastNode = insNode;
            }

        }

        // zero all null expressions
        // isn't necessary, because MultipleAssignments aren't allowed
                /*
        for (int i=0;i<cfgExpressionVisitor.bladeCount;i++)
        {

        Expression e = mvExpr.bladeExpressions[i];

        if (e==null)
        {
        AssignmentNode insNode = new AssignmentNode(node.getGraph(), new MultivectorComponent(variable.getName(),i),new FloatConstant(0.0f));

        lastNode.insertAfter(insNode);
        lastNode = insNode;
        }

        }
         */

        node.getGraph().removeNode(node);

        return lastNode;
    }
}
