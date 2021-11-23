package de.gaalop.mathematica;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;


/**
 * This class implements the CFG and DFG visitor that generate Mathematica code.
 * Heavily inspired by CppVisitor.java
 */

public class MathematicaVisitor extends DefaultCodeGeneratorVisitor {

    private void not_implemented(String nodeType) {
        throw new UnsupportedOperationException("The Mathematica backend does not support \"" + nodeType + "\"!");
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

        StringList locals = graph.getLocals();

        code.append(graph.getSource().getName().split("\\.")[0]+"[");

        StringList list1 = new StringList();
        for (String var : graph.getInputs()) {
            list1.add(var+"_");
        }
        code.append(list1.join());
        
        code.append("] := Module[{");

        code.append(locals.join());

        code.append("},\n");

        // We iterate through local variables a second time, in order to initialize them.
        for (String var : locals) {
            code.append(var);
            code.append(" = ConstantArray[0,");
            code.append(bladeCount);
            code.append("];\n");
        }

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getVariable().accept(this);
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";");

        if (node.getVariable() instanceof MultivectorComponent) {
            code.append(" (*");
            code.append(graph.getBladeString((MultivectorComponent) node.getVariable()));
            code.append("*)");
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
        code.append("Return[{");
        code.append(graph.getOutputs().join());
        code.append("}];]");
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
                functionName = "Sqrt";
                break;
            case ABS:
                functionName = "Abs";
                break;
            case COS:
                functionName = "Cos";
                break;
            case SIN:
                functionName = "Sin";
                break;
            case EXP:
                functionName = "Exp";
                break;
            default:
                not_implemented("specific MathFunctionCall");
        }
        code.append(functionName);
        code.append("[");
        node.getOperand().accept(this);
        code.append("]");
    }

    @Override
    public void visit(Variable node) {
        code.append(node.getName());
    }

    @Override
    public void visit(MultivectorComponent node) {
        //code.append(node.getName().replace(suffix, ""));
        code.append(node.getName());
        code.append("[[");
        code.append(node.getBladeIndex() + 1); // Indices start at 1 in Mathematica
        code.append("]]");
    }

    @Override
    public void visit(Exponentiation node) {
        code.append("Power(");
        node.getLeft().accept(this);
        code.append(", ");
        node.getRight().accept(this);
        code.append(")");
    }

    @Override
    public void visit(FloatConstant node) {
        code.append(Double.toString(node.getValue()));
    }

    @Override
    public void visit(Negation node) {
        code.append("(-");
        node.getOperand().accept(this);
        code.append(")");
    }
}
