package de.gaalop.gappdot;

import de.gaalop.cfg.*;
import de.gaalop.dfg.Expression;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This class exports a control flow graph to the DOT language used by GraphViz.
 */
public class CfgVisitor implements ControlFlowVisitor {

    private StringBuilder result = new StringBuilder();

    private Map<Object, String> idMap = new HashMap<Object, String>();
    private Stack<LoopNode> nestedLoops = new Stack<LoopNode>();

    private int highestId = 0;
    private static final String EDGE_COLOR_FORWARD = "darkolivegreen1";
    private static final String EDGE_COLOR_BACKWARDS = "darkorange1";

    public String getResult() {
        return result.toString();
    }

    private String getId(Object obj) {
        if (!idMap.containsKey(obj)) {
            highestId++;
            idMap.put(obj, "node" + highestId);
        }

        return idMap.get(obj);
    }

    private void addForwardEdge(Object n1, Object n2) {
        addEdge(n1, n2, EDGE_COLOR_FORWARD);
    }

    private void addBackwardsEdge(Object n1, Object n2) {
        addEdge(n1, n2, EDGE_COLOR_BACKWARDS);
    }

    private void addEdge(Object n1, Object n2, String color) {
    	if (n2 instanceof BlockEndNode) {
    		return;
    	}
        result.append('\t');
        result.append(getId(n1));
        result.append(" -> ");
        result.append(getId(n2));
        result.append(" [color=\"").append(color).append("\"]");
        result.append(";\n");
    }

    private void addNode(Node node, String label) {
        result.append("\t");
        result.append(getId(node));
        result.append(" [label=\"");
        result.append(label);
        result.append("\"];\n");

        addPredecessorEdges(node);
    }

    private void addPredecessorEdges(Node node) {
        // Add edges back to predecessors
        for (Node predecessor : node.getPredecessors()) {
            addBackwardsEdge(node, predecessor);
        }
    }

    @Override
    public void visit(StartNode startNode) {
        result.append("digraph {\n");

        addNode(startNode, "Start");
        addForwardEdge(startNode, startNode.getSuccessor());

        startNode.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode assignmentNode) {
        addNode(assignmentNode, "Assignment:\\n" + assignmentNode.getVariable());
        addForwardEdge(assignmentNode, assignmentNode.getSuccessor());

        String subnodePrefix = getId(assignmentNode) + "_";
        String stmtList = getCode(assignmentNode.getValue(), subnodePrefix);
        result.append(stmtList);
        result.append("\t");
        result.append(subnodePrefix);
        result.append("1 -> ");
        result.append(getId(assignmentNode));
        result.append(";\n");

        assignmentNode.getSuccessor().accept(this);
    }
	
	@Override
	public void visit(ExpressionStatement node) {
		addNode(node, "Expression:\\n" + node.getExpression());
		node.getSuccessor().accept(this);
	}

    @Override
	public void visit(StoreResultNode node) {
	    addNode(node, "Output:\\n" + node.getValue());
	    addForwardEdge(node, node.getSuccessor());
	    node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		String label = "if\\n" + node.getCondition().toString();
		addNode(node, label);
		addForwardEdge(node, node.getPositive());
		node.getPositive().accept(this);
		if (!(node.getNegative() instanceof BlockEndNode)) {
			addForwardEdge(node, node.getNegative());
			node.getNegative().accept(this);
		}
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		addNode(node, "loop");
		addForwardEdge(node, node.getBody());
		nestedLoops.push(node);
		node.getBody().accept(this);
		nestedLoops.pop();
		
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BreakNode node) {
		addNode(node, "break");
		addForwardEdge(node, nestedLoops.peek().getSuccessor());
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BlockEndNode node) {
		SequentialNode base = node.getBase();
		if (base instanceof LoopNode) {
			for (Node p : node.getPredecessors()) {
				if (idMap.containsKey(p)) {
					addForwardEdge(p, base);
				}
			}
		} else {
			addForwardEdge(base, base.getSuccessor());
		}
	}

	@Override
	public void visit(EndNode endNode) {
	    addNode(endNode, "End");
	    result.append("}\n");
	}

	private String getCode(Expression expression, String prefix) {
        DfgVisitor visitor = new DfgVisitor();
        visitor.setIdPrefix(prefix);
        expression.accept(visitor);
        return visitor.toString();
    }

	@Override
	public void visit(Macro node) {
		throw new IllegalArgumentException("Macros should have been inlined.");
	}

	@Override
	public void visit(ColorNode node) {
		node.getSuccessor().accept(this);
	}
}