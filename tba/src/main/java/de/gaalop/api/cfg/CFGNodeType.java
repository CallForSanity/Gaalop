package de.gaalop.api.cfg;

/**
 * Declares all types of nodes in a ControlFlowGraph
 * @author Christian Steinmetz
 */
public enum CFGNodeType {

    StartNode, AssignmentNode, StoreResultNode,
    IfThenElseNode, BlockEndNode, LoopNode, BreakNode,
    Macro, ExpressionStatement, EndNode, ColorNode
}
