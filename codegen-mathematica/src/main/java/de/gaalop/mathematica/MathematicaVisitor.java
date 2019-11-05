package de.gaalop.mathematica;

import de.gaalop.cfg.*;
import de.gaalop.cpp.OperatorPriority;
import de.gaalop.dfg.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * This class implements the CFG and DFG visitor that generate Mathematica code.
 * Heavily inspired by CppVisitor.java
 */

public class MathematicaVisitor implements ControlFlowVisitor, ExpressionVisitor {

    protected Log log = LogFactory.getLog(MathematicaVisitor.class);

    protected StringBuilder code;
    private ControlFlowGraph graph;

    public MathematicaVisitor() {
        code = new StringBuilder();
    }

    // Stolen from CppVisitor.java. Keeping the order of the variables deterministic is a good idea.
    private List<Variable> sortVariables(Set<Variable> inputVariables) {
        List<Variable> variables = new ArrayList<Variable>(inputVariables);
        Comparator<Variable> comparator = new Comparator<Variable>() {

            @Override
            public int compare(Variable o1, Variable o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        };

        Collections.sort(variables, comparator);
        return variables;
    }

    private void not_implemented(String nodeType) {
        throw new UnsupportedOperationException("The Mathematica backend does not support \"" + nodeType + "\"!");
    }

    private void macro_error() {
        throw new UnsupportedOperationException("Macros should have been removed already!");
    }

    protected void addBinaryInfix(BinaryOperation op, String operator) {
        addChild(op, op.getLeft());
        code.append(operator);
        addChild(op, op.getRight());
    }

    protected void addChild(Expression parent, Expression child) {
        if (OperatorPriority.hasLowerPriority(parent, child)) {
            code.append('(');
            child.accept(this);
            code.append(')');
        } else {
            child.accept(this);
        }
    }

    private Set<Variable> collectStoreNodeVariables(ControlFlowGraph graph) {
        Set<Variable> storeNodeVariables = new HashSet<Variable>();
        Node node = graph.getStartNode();
        while (! (node instanceof EndNode)) {
            if (node instanceof StoreResultNode) {
                StoreResultNode storeNode = (StoreResultNode) node;
                storeNodeVariables.add(storeNode.getValue());
            }
            if (node instanceof SequentialNode) {
                SequentialNode seqNode = (SequentialNode) node;
                node = seqNode.getSuccessor();
            } else {
                break;
            }
        }
        return storeNodeVariables;
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

        List<Variable> localVariables = sortVariables(graph.getLocalVariables());
        List<Variable> inputParameters = sortVariables(graph.getInputVariables());

        code.append("calculate[");

        for (Variable var : inputParameters) {
            code.append(var.getName());
            code.append("_, ");
        }

        // Remove the last ", " (two spaces) if we have any input variables.
        if (graph.getInputVariables().size() > 0) {
            code.setLength(code.length() - 2);
        }

        code.append("] := Module[{");

        for (Variable var : localVariables) {
            code.append(var.getName());
            code.append(", ");
        }

        // Remove the last ", " if we have any local variables.
        if (graph.getLocalVariables().size() > 0) {
            code.setLength(code.length() - 2);
        }

        code.append("},\n");

        // We iterate through local variables a second time, in order to initialize them.
        for (Variable var : localVariables) {
            code.append(var.getName());
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
            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            code.append(node.getGraph().getAlgebraDefinitionFile().getBladeString(component.getBladeIndex()));
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
    public void visit(IfThenElseNode node) {
        not_implemented("IfThenElseNode");
    }

    @Override
    public void visit(BlockEndNode node) {
        not_implemented("BlockEndNode");
    }

    @Override
    public void visit(LoopNode node) {
        not_implemented("LoopNode");
    }

    @Override
    public void visit(BreakNode node) {
        not_implemented("BreakNode");
    }

    @Override
    public void visit(Macro node) {
        macro_error();
    }

    @Override
    public void visit(ExpressionStatement node) {
        not_implemented("ExpressionStatement");
    }

    @Override
    public void visit(EndNode node) {
        List<Variable> resultsToStore = sortVariables(collectStoreNodeVariables(graph));

        code.append("Return[{");
        for (Variable var : resultsToStore) {
            code.append(var.getName());
            code.append(", ");
        }

        // Remove the last ", " if we have any variables to store.
        if (resultsToStore.size() > 0) {
            code.setLength(code.length() - 2);
        }

        code.append("}];]");
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(Subtraction node) {
        addBinaryInfix(node, " - ");
    }

    @Override
    public void visit(Addition node) {
        addBinaryInfix(node, " + ");
    }

    @Override
    public void visit(Division node) {
        addBinaryInfix(node, " / ");
    }

    @Override
    public void visit(InnerProduct node) {
        not_implemented("InnerProduct");
    }

    @Override
    public void visit(Multiplication node) {
        addBinaryInfix(node, " * ");
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
    public void visit(OuterProduct node) {
        not_implemented("OuterProduct");
    }

    @Override
    public void visit(BaseVector node) {
        not_implemented("BaseVector");
    }

    @Override
    public void visit(Negation node) {
        code.append("(-");
        node.getOperand().accept(this);
        code.append(")");
    }

    @Override
    public void visit(Reverse node) {
        not_implemented("Reverse");
    }

    @Override
    public void visit(LogicalOr node) {
        not_implemented("LogicalOr");
    }

    @Override
    public void visit(LogicalAnd node) {
        not_implemented("LogicalAnd");
    }

    @Override
    public void visit(LogicalNegation node) {
        not_implemented("LogicalNegation");
    }

    @Override
    public void visit(Equality node) {
        not_implemented("Equality");
    }

    @Override
    public void visit(Inequality node) {
        not_implemented("Inequality");
    }

    @Override
    public void visit(Relation relation) {
        not_implemented("Relation");
    }

    @Override
    public void visit(FunctionArgument node) {
        macro_error();
    }

    @Override
    public void visit(MacroCall node) {
        macro_error();
    }

    public String getCode() {
        return code.toString();
    }
}
