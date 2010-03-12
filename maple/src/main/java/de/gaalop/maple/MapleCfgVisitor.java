package de.gaalop.maple;

import de.gaalop.cfg.*;
import de.gaalop.dfg.Expression;
import de.gaalop.maple.engine.MapleEngine;
import de.gaalop.maple.engine.MapleEngineException;
import de.gaalop.maple.parser.MapleLexer;
import de.gaalop.maple.parser.MapleParser;
import de.gaalop.maple.parser.MapleTransformer;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * This visitor creates code for Maple.
 */
public class MapleCfgVisitor implements ControlFlowVisitor {

  private Log log = LogFactory.getLog(MapleCfgVisitor.class);

  private MapleEngine engine;

  public MapleCfgVisitor(MapleEngine engine) {
    this.engine = engine;
  }

  private String generateCode(Expression expression) {
    MapleDfgVisitor visitor = new MapleDfgVisitor();
    expression.accept(visitor);
    return visitor.getCode();
  }

  @Override
  public void visit(StartNode startNode) {
    startNode.getSuccessor().accept(this);
  }

  @Override
  public void visit(AssignmentNode assignmentNode) {
    String variableCode = generateCode(assignmentNode.getVariable());
    // If you want to simplify (and keep) the last assignment to every variable
    // uncomment the following statement:
    // simplifyBuffer.add(variableCode);

    StringBuilder codeBuffer = new StringBuilder();
    codeBuffer.append(variableCode);
    codeBuffer.append(" := ");
    codeBuffer.append(generateCode(assignmentNode.getValue()));
    codeBuffer.append(";\n");

    try {
      engine.evaluate(codeBuffer.toString());
    } catch (MapleEngineException e) {
      throw new RuntimeException("Unable to simplify using Maple.", e);
    }

    Node successor = assignmentNode.getSuccessor();
    assignmentNode.getGraph().removeNode(assignmentNode); // FIXME: really remove each assignment? what if it is not prefixed by ?
    successor.accept(this);
  }

  @Override
  public void visit(StoreResultNode node) {
    String evalResult = simplify(node.getValue());
    log.debug("Maple simplification of " + node.getValue() + ": " + evalResult);

    List<SequentialNode> newNodes = parseMapleCode(node.getGraph(), evalResult);

    for (SequentialNode newNode : newNodes) {
      node.insertBefore(newNode);
    }

    node.getSuccessor().accept(this);
  }

  @Override
  public void visit(IfThenElseNode node) {
    // TODO: take condition into account
    node.getPositive().accept(this);
    node.getNegative().accept(this);
    node.getSuccessor().accept(this);
  }

  @Override
  public void visit(BlockEndNode node) {
    // nothing to do
  }

  @Override
  public void visit(EndNode endNode) {
  }

  /**
   * Parses a snippet of maple code and returns a list of CFG nodes that implement the returned maple expressions.
   * 
   * @param graph The control flow graph the new nodes should be created in.
   * @param mapleCode The code returned by Maple.
   * @return A list of control flow nodes modeling the returned code.
   */
  private List<SequentialNode> parseMapleCode(ControlFlowGraph graph, String mapleCode) {
    MapleLexer lexer = new MapleLexer(new ANTLRStringStream(mapleCode));
    MapleParser parser = new MapleParser(new CommonTokenStream(lexer));
    try {
      MapleParser.program_return result = parser.program();
      MapleTransformer transformer = new MapleTransformer(new CommonTreeNodeStream(result.getTree()));
      return transformer.script(graph);
    } catch (RecognitionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Simplifies a single Expression node.
   * 
   * @param expression The data flow graph that should be simplified
   * @return The code Maple returned as the simplification.
   */
  private String simplify(Expression expression) {
    StringBuilder codeBuffer = new StringBuilder();
    codeBuffer.append("gaalop(");
    codeBuffer.append(generateCode(expression));
    codeBuffer.append(");\n");

    try {
      return engine.evaluate(codeBuffer.toString());
    } catch (MapleEngineException e) {
      throw new RuntimeException("Unable to simplify using Maple.", e);
    }
  }

//  private void removeNode(SequentialNode node) {
//    // Make a copy so we don't get concurrent modification exceptions
//    List<Node> predecessors = new ArrayList<Node>(node.getPredecessors());
//    Node successor = node.getSuccessor();
//
//    // Remove the node we are about to remove from its successors list of predecessors.
//    successor.removePredecessor(node);
//
//    // For all predecessors of the node we are removing
//    for (Node predecessor : predecessors) {
//      // Redirect the outgoing connection to the successor of removeNode
//      predecessor.replaceSuccessor(node, successor);
//
//      // And add the predecessor to the list of predecessors of the current successor
//      successor.addPredecessor(predecessor);
//      
//      // TODO: remove node from if-then-else node (positive or negative case)?
//    }
//  }
}
