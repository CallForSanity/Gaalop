package de.gaalop.productComputer.dataStruct.visitor;

import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCMultipleExpression;
import de.gaalop.productComputer.dataStruct.TCTerminal;

/**
 * Implements a traversal visitor
 * @author Christian Steinmetz
 */
public class TCTraversalVisitor extends TCTypeVisitor {

    @Override
    public Object visitMultipleExpression(TCMultipleExpression binaryExpression, Object arg) {
        for (TCExpression e: binaryExpression.getExpressions()) 
            e.accept(this, arg);
        return null;
    }

    @Override
    public Object visitTerminal(TCTerminal terminal, Object arg) {
        return null;
    }

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        return null;
    }

}
