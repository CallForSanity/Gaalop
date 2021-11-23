package de.gaalop.clucalc.output;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.dfg.*;

/**
 * Creates CLUCalc code for a single expression. The code can afterwards be
 * queried via {@link #getCode()}.
 */
public class DfgVisitor implements ExpressionVisitor {

    private StringBuilder code = new StringBuilder();
    private final String optSuffix;
    private final String suffix;
    private final ClucalcOperatorPriority operatorPriority = new ClucalcOperatorPriority();

    public DfgVisitor(String suffix, String optSuffix) {
        this.suffix = suffix;
        this.optSuffix = optSuffix;
    }

    public String getCode() {
        return code.toString();
    }

    private void handleInfix(BinaryOperation operation, String operator, boolean withSpaces) {
        addChild(operation, operation.getLeft());
        if (withSpaces) {
            code.append(' ');
            code.append(operator);
            code.append(' ');
        } else {
            code.append(operator);
        }
        addChild(operation, operation.getRight());
    }

    private void addChild(Expression parent, Expression child) {
        if (operatorPriority.hasLowerPriority(parent, child)) {
            code.append('(');
            child.accept(this);
            code.append(')');
        } else {
            child.accept(this);
        }
    }

    @Override
    public void visit(Subtraction subtraction) {
        handleInfix(subtraction, "-", true);
    }

    @Override
    public void visit(Addition addition) {
        handleInfix(addition, "+", true);
    }

    @Override
    public void visit(Division division) {
        handleInfix(division, "/", true);
    }

    @Override
    public void visit(InnerProduct innerProduct) {
        handleInfix(innerProduct, ".", true);
//		throw new UnsupportedOperationException("Inner product is unsupported for code generation.");
    }

    @Override
    public void visit(Multiplication multiplication) {
        handleInfix(multiplication, "*", true);
    }

    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        code.append(mathFunctionCall.getFunction().toString());
        code.append('(');
        mathFunctionCall.getOperand().accept(this);
        code.append(')');
    }

    @Override
    public void visit(Variable variable) {
        if (variable.globalAccess()) {
            code.append("::");
        }
        code.append(variable.getName().replace(optSuffix, suffix));
    }

    @Override
    public void visit(MultivectorComponent component) {
        code.append(component.getName().replace(optSuffix, suffix));
        code.append('(');
        code.append(component.getBladeIndex() + 1);
        code.append(')');
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        handleInfix(exponentiation, "^^", false);
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        code.append(Double.toString(floatConstant.getValue()).replace('E', 'e'));
    }

    @Override
    public void visit(OuterProduct outerProduct) {
        handleInfix(outerProduct, "^", false);
    }

    @Override
    public void visit(BaseVector baseVector) {
        code.append(baseVector.toString());
    }

    @Override
    public void visit(Negation negation) {
        code.append('(');
        code.append('-');
        addChild(negation, negation.getOperand());
        code.append(')');
    }

    @Override
    public void visit(Reverse node) {
        code.append('~');
        addChild(node, node.getOperand());
    }

    @Override
    public void visit(LogicalOr node) {
        handleInfix(node, "||", true);
    }

    @Override
    public void visit(LogicalAnd node) {
        handleInfix(node, "&&", true);
    }

    @Override
    public void visit(LogicalNegation node) {
        code.append("!!");
        addChild(node, node.getOperand());
    }

    @Override
    public void visit(Equality node) {
        handleInfix(node, "==", true);
    }

    @Override
    public void visit(Inequality node) {
        handleInfix(node, "!=", true);
    }

    @Override
    public void visit(Relation relation) {
        handleInfix(relation, relation.getTypeString(), false);
    }

    @Override
    public void visit(FunctionArgument node) {
        code.append("_P(");
        code.append(node.getIndex());
        code.append(")");
    }

    @Override
    public void visit(MacroCall node) {
        // make sure access is always global (no restriction)
        code.append("::");
        code.append(node.getName());
        code.append("(");
        for (Expression arg : node.getArguments()) {
            arg.accept(this);
            code.append(", ");
        }
        if (node.getArguments().size() > 0) {
            code.delete(code.length() - 2, code.length());
        }
        code.append(")");
    }

}
