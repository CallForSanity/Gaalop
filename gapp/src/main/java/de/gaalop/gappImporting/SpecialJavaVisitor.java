/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Variable;
import de.gaalop.java.JavaVisitor;

/**
 *
 * @author christian
 */
public class SpecialJavaVisitor extends JavaVisitor {

    

        @Override
        public void visit(BaseVector baseVector) {
            // TODO Correctly handle the underlying algebra mode here
		code.append("e");
		switch (baseVector.getOrder()) {
		case 1:
		case 2:
		case 3:
			code.append(baseVector.getIndex());
			break;
		case 4:
			if (baseVector.getIndex().equals("inf")) {
				code.append("inf");
			} else {
				code.append('p');
			}
			break;
		case 5:
			if (baseVector.getIndex().equals("0")) {
				code.append(0);
			} else {
				code.append('m');
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid base vector index: " + baseVector.getIndex());
		}
        }

        @Override
        public void visit(InnerProduct innerProduct) {
            innerProduct.getLeft().accept(this);
            code.append(".");
            innerProduct.getRight().accept(this);
        }

        @Override
        public void visit(OuterProduct outerProduct) {
            outerProduct.getLeft().accept(this);
            code.append("^");
            outerProduct.getRight().accept(this);
        }

    @Override
    public void visit(Variable variable) {
         code.append(variable.getName());
    }


}
