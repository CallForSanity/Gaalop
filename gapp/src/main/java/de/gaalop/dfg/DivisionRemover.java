package de.gaalop.dfg;

import de.gaalop.dfg.ExpressionRemover;
import de.gaalop.api.dfg.DFGNodeType;
import de.gaalop.api.dfg.DFGNodeTypeGetter;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Reverse;

/**
 * Replaces all divisions of a expression and a no-floatconstant
 * with the GA pendant.
 *
 * If a and b are i.e. multivectors and a/b exists in the DFG,
 * then it will be replaced with a*(b/(a.(a~))), where ~ is the reverse.
 *
 * @author christian
 */
public class DivisionRemover extends ExpressionRemover {

    @Override
    public void visit(Division node) {
        if (DFGNodeTypeGetter.getTypeOfDFGNode(node.getRight()) != DFGNodeType.FloatConstant) 
            resultExpr = new Multiplication(node.getLeft(), new Division(node.getRight(), new InnerProduct(node.getRight(), new Reverse(node.getRight()))));
    }

}
