package de.gaalop.rust;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

/**
 * This class implements the CFG and DFG visitor that generate Rust code.
 * Heavily inspired by CppVisitor.java
 */

public class RustVisitor extends DefaultCodeGeneratorVisitor {

    private final String variableType = "f32"; // f64 would also be a valid option
    
    private void not_implemented(String nodeType) {
        throw new UnsupportedOperationException("The Rust backend does not support \"" + nodeType + "\"!");
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

        code.append("fn "+graph.getSource().getName().split("\\.")[0]+"(");

        StringList list = new StringList();
        for (String var: graph.getInputs()) {
            list.add(var + ": "+variableType);
        }

        for (String var : graph.getLocals()) {
            list.add(var + ": &mut [" + variableType+"; "+bladeCount+"]");

        }

        code.append(list.join());

        code.append(") {\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        code.append("\t");
        node.getVariable().accept(this);
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";");

        if (node.getVariable() instanceof MultivectorComponent) {
            code.append(" // ");
            code.append(graph.getBladeString((MultivectorComponent) node.getVariable()));
        }

        code.append("\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        not_implemented("ExpressionStatement");
    }

    @Override
    public void visit(EndNode node) {
        code.append("}\n");
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(MathFunctionCall node) {
        String functionName = "";
        switch (node.getFunction()) {
            case SQRT:
                functionName = "sqrt";
                break;
            case ABS:
                functionName = "abs";
                break;
            case COS:
                functionName = "cos";
                break;
            case SIN:
                functionName = "sin";
                break;
            case EXP:
                functionName = "exp";
                break;
            default:
                not_implemented("specific MathFunctionCall");
        }
        code.append("(");
        node.getOperand().accept(this);
        code.append(").");
        code.append(functionName);
        code.append("()");
    }

    @Override
    public void visit(Variable node) {
        code.append(node.getName());
    }

    @Override
    public void visit(MultivectorComponent node) {
        //code.append(node.getName().replace(suffix, ""));
        code.append(node.getName());
        code.append('[');
        code.append(node.getBladeIndex());
        code.append(']');
    }

    @Override
    public void visit(Exponentiation node) {
        code.append("(");
        node.getLeft().accept(this);
        code.append(").pow(");
        node.getRight().accept(this);
        code.append(")");
    }

    @Override
    public void visit(FloatConstant node) {
        code.append(Double.toString(node.getValue()));
        code.append("_f32"); // Otherwise, we can't apply functions to numbers
    }

    @Override
    public void visit(Negation node) {
        code.append("(-");
        node.getOperand().accept(this);
        code.append(")");
    }
}
