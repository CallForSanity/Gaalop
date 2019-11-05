package de.gaalop.rust;

import de.gaalop.cfg.*;
import de.gaalop.cpp.OperatorPriority;
import de.gaalop.dfg.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * This class implements the CFG and DFG visitor that generate Rust code.
 * Heavily inspired by CppVisitor.java
 */

public class RustVisitor implements ControlFlowVisitor, ExpressionVisitor {

    protected Log log = LogFactory.getLog(RustVisitor.class);

    protected StringBuilder code;
    private ControlFlowGraph graph;

    private final String variableType = "f32"; // f64 would also be a valid option

    public RustVisitor() {
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
        throw new UnsupportedOperationException("The Rust backend does not support \"" + nodeType + "\"!");
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

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

        List<Variable> localVariables = sortVariables(graph.getLocalVariables());
        List<Variable> inputParameters = sortVariables(graph.getInputVariables());

        code.append("fn calculate(");

        for (Variable var : inputParameters) {
            code.append(var.getName());
            code.append(": ").append(variableType); // The assumption here is that they all are normal scalars
            code.append(", ");
        }

        for (Variable var : localVariables) {
            code.append(var.getName());
            // The local variables have an array type of fixed size ( = bladeCount).
            code.append(": &mut [").append(variableType).append("; ").append(bladeCount).append("]");
            code.append(", ");
        }

        // Remove the last ", " if we have any variables in the function header.
        if (graph.getLocalVariables().size() > 0) {
            code.setLength(code.length() - 2);
        }

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
            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            code.append(node.getGraph().getAlgebraDefinitionFile().getBladeString(component.getBladeIndex()));
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
        code.append("}\n");
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
