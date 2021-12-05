package de.gaalop.matlab;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++
 * code.
 */
public class MatlabVisitor extends DefaultCodeGeneratorVisitor {

    protected String variableType = "float";
    
    protected String filename;

    public MatlabVisitor(boolean useDouble, String filename) {
        this.filename = filename;
        if (useDouble) {
            variableType = "double";
        }
    }

    public MatlabVisitor(String variableType) {
        this.variableType = variableType;
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        
        code.append("function [");        
        code.append(graph.getOutputs().join());
        code.append("] = "+filename+"("); 

        code.append(graph.getInputs().join());

        code.append(")\n");
        indentation++;

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        appendIndentation();
        node.getVariable().accept(this);
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";");

        if (node.getVariable() instanceof MultivectorComponent) {
            code.append(" % ");
            code.append(graph.getBladeString((MultivectorComponent) node.getVariable()));
        }

        code.append('\n');

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        appendIndentation();
        node.getExpression().accept(this);
        code.append(";\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        indentation--;
        code.append("end\n");
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        String funcName;
        switch (mathFunctionCall.getFunction()) {
            case ABS:
                funcName = "abs";
                break;
            case SQRT:
                funcName = "sqrt";
                break;
            default:
                funcName = mathFunctionCall.getFunction().toString().toLowerCase();
        }
        code.append(funcName);
        code.append('(');
        mathFunctionCall.getOperand().accept(this);
        code.append(')');
    }

    @Override
    public void visit(Variable variable) {
        // usually there are no variables
        code.append(variable.getName());
    }

    @Override
    public void visit(MultivectorComponent component) {
        code.append(component.getName());
        code.append('(');
        code.append(component.getBladeIndex()+1);   //+1 because MATLAB arrays are one-based.
        code.append(')');
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            code.append("power(");
            exponentiation.getLeft().accept(this);
            code.append(',');
            exponentiation.getRight().accept(this);
            code.append(')');
        }
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        code.append(Double.toString(floatConstant.getValue()));
    }

    @Override
    public void visit(Negation negation) {
        code.append('(');
        code.append('-');
        addChild(negation, negation.getOperand());
        code.append(')');
    }
}
