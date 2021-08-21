package de.gaalop.julia;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

/**
 * This class implements the CFG and DFG visitor that generate Julia code.
 * Heavily inspired by CppVisitor.java
 */

public class JuliaVisitor extends DefaultCodeGeneratorVisitor {

    private boolean forGrassmann;

    public JuliaVisitor(boolean forGrassmann) {
        this.forGrassmann = forGrassmann;
    }

    private void not_implemented(String nodeType) {
        throw new UnsupportedOperationException("The Julia backend does not support \"" + nodeType + "\"!");
    }
    
    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

        StringList locals = graph.getLocals();

        if (forGrassmann) {
            code.append("# using StaticArrays, Grassmann\n\n");
        }

        code.append("function "+graph.getSource().getName().split("\\.")[0]+"(");
        
        StringList inputs = graph.getInputs();
        
        if (forGrassmann) {
            inputs.addFirst("V");
        }
        code.append(inputs.join());

        code.append(")\n");

        for (String var : locals) {
            code.append('\t');
            code.append(var);
            code.append(" = ");
            if (forGrassmann) {
                code.append("@MArray ");
            }
            code.append("zeros(");
            code.append(bladeCount);
            code.append(")\n");
        }

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
            code.append(" # ");
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
        code.append('\t');
        StringList results = OutputsCollector.getOutputsFromGraphAsSortedList(graph);
        StringList list = new StringList();
        for (String var : results) {
            if (forGrassmann) {
                list.add("MultiVector{V}("+var+")");
            } else 
                list.add(var);
        }
        code.append(list.join()+"\n");
        code.append("end\n");
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
        code.append(functionName);
        code.append("(");
        node.getOperand().accept(this);
        code.append(")");
    }

    @Override
    public void visit(Variable node) {
        code.append(node.getName());
    }

    @Override
    public void visit(MultivectorComponent node) {
        //code.append(node.getName().replace(suffix, ""));
        code.append(node.getName());
        code.append("[");
        code.append(node.getBladeIndex() + 1); // Indices start at 1 in Julia
        code.append("]");
    }

    @Override
    public void visit(Exponentiation node) {
        code.append("(");
        node.getLeft().accept(this);
        code.append(") ^ (");
        node.getRight().accept(this);
        code.append(")");
    }

    @Override
    public void visit(FloatConstant node) {
        code.append(Double.toString(node.getValue()));
        code.append("f0");
    }

    @Override
    public void visit(Negation node) {
        code.append("(-");
        node.getOperand().accept(this);
        code.append(")");
    }

}
