package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Differentiater;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements basic methods for zero finder methods
 * @author Christian Steinmetz
 */
public abstract class PrepareZerofinder extends ZeroFinder {
    
    /**
     * Search _V_PRODUCT in a list of assignment nodes, 
     * applies the sum of the squares to _V_PRODUCT_S
     * and returns the result
     * @param nodes The list of assignment nodes
     * @return The new list of assignment nodes
     */
    protected static LinkedList<AssignmentNode> createSumOfSquares(LinkedList<AssignmentNode> nodes) {
        HashMap<String, LinkedList<AssignmentNode>> collect = new HashMap<String, LinkedList<AssignmentNode>>();
        
        for (AssignmentNode node: nodes) {
            MultivectorComponent m = (MultivectorComponent) node.getVariable();
            String name = m.getName();
            if (!collect.containsKey(name)) 
                collect.put(name, new LinkedList<AssignmentNode>());

            collect.get(name).add(node);
        }
        
        LinkedList<AssignmentNode> myNodes = new LinkedList<AssignmentNode>();
        for (String s: collect.keySet()) 
            if (s.startsWith("_V_PRODUCT")) {
                Expression sumOfSquares = null; 
                
                for (AssignmentNode node: collect.get(s)) {
                    Expression square = new Exponentiation(node.getValue(), new FloatConstant(2));
                    
                    if (sumOfSquares == null) 
                        sumOfSquares = square;
                    else 
                        sumOfSquares = new Addition(sumOfSquares, square);
                }
                
                AssignmentNode newNode = new AssignmentNode(null, new MultivectorComponent(s+"_S", 0), sumOfSquares);
                myNodes.add(newNode);
                listInsertBefore(myNodes, newNode, collect.get(s).getFirst());

                for (AssignmentNode node: collect.get(s)) 
                    myNodes.remove(node);
            }
        return myNodes;
    }
    
    /**
     * Optimizes the code pieces by creating a code piece for every multivector to be rendered
     * @param nodes The list of assignment nodes
     * @return The list of code pieces which is created
     */
    protected static LinkedList<CodePiece> optimizeCodePieces(LinkedList<AssignmentNode> nodes) {
        //Optimize pieces of code for each multivector to be rendered
        LinkedList<CodePiece> codePieces = new LinkedList<CodePiece>();
        HashMap<String, CodePiece> mapCodePieces = new HashMap<String, CodePiece>();
        for (AssignmentNode node: nodes) {
            String name = node.getVariable().getName();
            if (!mapCodePieces.containsKey(name)) {
                CodePiece cp = new CodePiece();
                cp.nameOfMultivector = name;
                mapCodePieces.put(name, cp);
                codePieces.add(cp);
            }
            mapCodePieces.get(name).add(node);
        }
        return codePieces;
    }
    
    /**
     * Inserts an element in a list before a certain element
     * @param list The list to insert in
     * @param toInsert The element to be insert
     * @param before The suceeding element
     */
    protected static void listInsertBefore(LinkedList<AssignmentNode> list, AssignmentNode toInsert, AssignmentNode before) {
        LinkedList<AssignmentNode> listCopy = new LinkedList<AssignmentNode>(list);
        list.clear();
        for (AssignmentNode node: listCopy) {
            if (node == before)
                list.add(toInsert);
            list.add(node);
        }
    } 

}
