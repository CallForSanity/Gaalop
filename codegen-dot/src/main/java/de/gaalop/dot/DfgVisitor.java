package de.gaalop.dot;

import de.gaalop.dfg.*;

import java.util.Map;
import java.util.IdentityHashMap;

/**
 * This class exports an expression and its children as a DOT graph.
 */
public class DfgVisitor implements ExpressionVisitor {

    private String idPrefix = "node";

    private StringBuilder result = new StringBuilder();

    private Map<Object, String> idMap = new IdentityHashMap<Object, String>();

    private int highestId = 0;

    public String toString() {
        return result.toString();
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    private String getId(Object obj) {
        if (!idMap.containsKey(obj)) {
            highestId++;
            idMap.put(obj, idPrefix + highestId);
        }

        return idMap.get(obj);
    }

    private void addEdge(Object n1, Object n2) {
        result.append('\t');
        result.append(getId(n1));
        result.append(" -> ");
        result.append(getId(n2));
        result.append(";\n");
    }

    private void addNode(Object node, String label) {
        result.append("\t");
        result.append(getId(node));
        result.append(" [label=\"");
        result.append(label);
        result.append("\", shape=\"box\"];\n");
    }

    private void addBinaryOp(BinaryOperation op, String label) {
        addNode(op, label);
        op.getLeft().accept(this);
        op.getRight().accept(this);
        addEdge(op.getLeft(), op);
        addEdge(op.getRight(), op);
    }

    @Override
    public void visit(Subtraction subtraction) {
        addBinaryOp(subtraction, "-");
    }

    @Override
    public void visit(Addition addition) {
        addBinaryOp(addition, "+");
    }

    @Override
    public void visit(Division division) {
        addBinaryOp(division, "/");
    }

    @Override
    public void visit(InnerProduct innerProduct) {
        addBinaryOp(innerProduct, ".");
    }

    @Override
    public void visit(Multiplication multiplication) {
        addBinaryOp(multiplication, "*");
    }

    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        addNode(mathFunctionCall, mathFunctionCall.getFunction().toString());
        mathFunctionCall.getOperand().accept(this);
        addEdge(mathFunctionCall.getOperand(), mathFunctionCall);
    }

    @Override
    public void visit(BaseVector baseVector) {
        addNode(baseVector, baseVector.toString());
    }

    @Override
    public void visit(Negation negation) {
        addUnaryOp(negation, "-");
    }

    @Override
    public void visit(Reverse node) {
        addUnaryOp(node, "~");
    }

    private void addUnaryOp(UnaryOperation node, String operator) {
        addNode(node, operator);
        node.getOperand().accept(this);
        addEdge(node.getOperand(), node);
    }

    @Override
    public void visit(Variable variable) {
        addNode(variable, variable.toString());
    }

    @Override
    public void visit(MultivectorComponent component) {
        addNode(component, component.toString());
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        addBinaryOp(exponentiation, "^");
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        addNode(floatConstant, floatConstant.toString());
    }

    @Override
    public void visit(OuterProduct outerProduct) {
        addBinaryOp(outerProduct, "^");
    }

    @Override
    public void visit(LogicalOr node) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void visit(LogicalAnd node) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void visit(Equality node) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void visit(Inequality node) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void visit(Relation relation) {
      // TODO Auto-generated method stub
      
    }
}
