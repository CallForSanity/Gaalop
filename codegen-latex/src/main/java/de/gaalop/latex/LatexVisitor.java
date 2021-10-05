package de.gaalop.latex;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static java.lang.Double.compare;

/**
 * This class implements the CFG and DFG visitor that generate LaTeX code.
 */
public class LatexVisitor extends DefaultCodeGeneratorVisitor {

    private Pattern INDEXED_NUMBER = Pattern.compile("^(\\w+)(\\d+)$");

    private final static FloatConstant HALF = new FloatConstant(0.5f);

    private final static Negation MINUS_HALF = new Negation(HALF);

    private final static Division ONE_HALF = new Division(new FloatConstant(1.0f), new FloatConstant(2.0f));

    private final static Negation MINUS_ONE_HALF = new Negation(ONE_HALF);

    @Override
    public void visit(StartNode node) {

        code.append("\\begin{align*}\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getVariable().accept(this);
        code.append("&= ");
        node.getValue().accept(this);
        code.append("\\\\\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.getExpression().accept(this);
        code.append("\\\\\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        code.append('?');
        code.append(node.getValue());
        code.append("\\\\\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(IfThenElseNode node) {
        code.append("\\text{IF } (");
        node.getCondition().accept(this);
        code.append(") \\\\\n");
        node.getPositive().accept(this);
        if (!(node.getNegative() instanceof BlockEndNode)) {
            code.append("\\text{ELSE} \\\\\n");
            node.getNegative().accept(this);
        }
        code.append("\\text{END IF} \\\\\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(LoopNode node) {
        code.append("\\text{LOOP} \\\\\n");
        node.getBody().accept(this);
        code.append("\\text{END LOOP} \\\\\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(BreakNode breakNode) {
        code.append("\\text{break}\\\\");
        breakNode.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        code.append("\\end{align*}\n");
    }

    @Override
    public void visit(Subtraction subtraction) {
        addBinaryInfix(subtraction, "-");
    }

    @Override
    public void visit(Addition addition) {
        addBinaryInfix(addition, "+");
    }

    @Override
    public void visit(Division division) {
        code.append("\\cfrac{");
        division.getLeft().accept(this);
        code.append("}{");
        division.getRight().accept(this);
        code.append("}");
    }

    @Override
    public void visit(InnerProduct innerProduct) {
        addBinaryInfix(innerProduct, "\\cdot");
    }

    @Override
    public void visit(Multiplication multiplication) {
        if (multiplication.getLeft().equals(HALF)) {
            ONE_HALF.accept(this);
            multiplication.getRight().accept(this);
        } else if (multiplication.getRight().equals(HALF)) {
            ONE_HALF.accept(this);
            multiplication.getLeft().accept(this);
        } else if (multiplication.getLeft().equals(MINUS_HALF)) {
            MINUS_ONE_HALF.accept(this);
            multiplication.getRight().accept(this);
        } else if (multiplication.getRight().equals(MINUS_HALF)) {
            MINUS_ONE_HALF.accept(this);
            multiplication.getLeft().accept(this);
        } else {
            addBinaryInfix(multiplication, "*");
        }
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
        String name = variable.getName();
        code.append(name.replace("_", "\\_"));
    }

    private void addIdentifier(String name) {
        Matcher matcher = INDEXED_NUMBER.matcher(name);
        if (matcher.matches()) {
            code.append(matcher.group(1).replace("_", "\\_"));
            code.append("_{");
            code.append(matcher.group(2));
            code.append("}");
        } else {
            code.append(name.replace("_", "\\_"));
        }
    }

    @Override
    public void visit(MultivectorComponent component) {
        addIdentifier(component.getName().replace("_opt", ""));
        code.append("_{");
        code.append(component.getBladeIndex());
        code.append('}');
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        exponentiation.getLeft().accept(this);
        code.append("^{");
        exponentiation.getRight().accept(this);
        code.append('}');
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        if (Double.isNaN(floatConstant.getValue())) {
            code.append("undefined");
        } else if (compare(floatConstant.getValue(), Math.floor(floatConstant.getValue())) == 0) {
            code.append((int) floatConstant.getValue());
        } else {
            code.append(Double.toString(floatConstant.getValue()));
        }
    }

    @Override
    public void visit(OuterProduct outerProduct) {
        addBinaryInfix(outerProduct, "\\wedge");
    }

    @Override
    public void visit(BaseVector baseVector) {
        code.append("e_{");
        code.append(baseVector.getIndex());
        code.append('}');
    }

    @Override
    public void visit(Negation negation) {
        code.append('-');
        negation.getOperand().accept(this);
    }

    @Override
    public void visit(Reverse node) {
        code.append('~');
        node.getOperand().accept(this);
    }

    @Override
    public void visit(LogicalOr node) {
        addBinaryInfix(node, " \\vee ");
    }

    @Override
    public void visit(LogicalAnd node) {
        addBinaryInfix(node, " \\wedge ");
    }

    @Override
    public void visit(LogicalNegation node) {
        code.append("!");
        node.getOperand().accept(this);
    }

    @Override
    public void visit(Equality node) {
        addBinaryInfix(node, " == ");
    }

    @Override
    public void visit(Inequality node) {
        addBinaryInfix(node, " \neq ");
    }

    @Override
    public void visit(Relation relation) {
        addBinaryInfix(relation, relation.getTypeString());
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }
}
