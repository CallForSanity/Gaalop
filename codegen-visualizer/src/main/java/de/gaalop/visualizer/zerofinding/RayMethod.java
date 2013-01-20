package de.gaalop.visualizer.zerofinding;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.*;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaDifferentiater;
import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;

/**
 * Implements a zero finder method, which uses rays
 * @author christian
 */
public class RayMethod extends ZeroFinder {


    
    @Override
    public boolean isPositionVariable(String name) {
        if (name.equals("_V_X")) return true;
        if (name.equals("_V_Y")) return true;
        if (name.equals("_V_Z")) return true;
        
        return false;
    }

    @Override
    public boolean isRayMethod() {
        return true;
    }
    
    private void replace(LinkedList<AssignmentNode> nodes) {
        //replace x=ox+t,y=oy,z=oz
        ReplaceVisitor visitor = new ReplaceVisitor() {

            private void visitVar(Variable node) {
                if (node.getName().equals("_V_X"))
                    result = new Addition(new MultivectorComponent("_V_ox", 0), new MultivectorComponent("_V_t", 0));
                if (node.getName().equals("_V_Y"))
                    result = new MultivectorComponent("_V_oy", 0);
                if (node.getName().equals("_V_Z"))
                    result = new MultivectorComponent("_V_oz", 0);
            }
            
            @Override
            public void visit(MultivectorComponent node) {
                visitVar(node);
            }

            @Override
            public void visit(Variable node) {
                visitVar(node);
            }
            
        };
        for (AssignmentNode node: nodes) {
            node.setVariable((Variable) visitor.replace(node.getVariable()));
            node.setValue(visitor.replace(node.getValue()));
        }
    }
    
    private LinkedList<AssignmentNode> createSumOfSquares(LinkedList<AssignmentNode> nodes) {
        //search _V_PRODUCT and apply the sum of the squares = _V_PRODUCT_S
        //and store the result in myNodes
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
    
    private LinkedList<CodePiece> optimizeCodePieces(LinkedList<AssignmentNode> myNodes) {
        //Optimize pieces of code for each multivector to be rendered
        LinkedList<CodePiece> codePieces = new LinkedList<CodePiece>();
        HashMap<String, CodePiece> mapCodePieces = new HashMap<String, CodePiece>();
        for (AssignmentNode node: myNodes) {
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
    
    private void diffentiateCodePieces(LinkedList<CodePiece> codePieces) {
        //differentiate each item of codePieces with respect to t with the help of maxima  to _V_PRODUCT_SD
        for (CodePiece cp: codePieces) {
            MaximaDifferentiater differentiater = new MaximaDifferentiater();
            LinkedList<AssignmentNode> derived;
            try {
                derived = differentiater.differentiate(cp, maximaCommand, "_V_t");
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"D", 0));
                    cp.add(d);
                }
            } catch (OptimizationException ex) {
                Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RecognitionException ex) {
                Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private LinkedList<CodePiece> prepareGraph(LinkedList<AssignmentNode> nodes) {
        //replace x=ox+t,y=oy,z=oz
        replace(nodes);
        
        //Insert expressions, like Maxima,  !!!
        InsertingExpression.insertExpressions(nodes);
        
        //search _V_PRODUCT and apply the sum of the squares = _V_PRODUCT_S
        //and store the result in myNodes
        LinkedList<AssignmentNode> myNodes = createSumOfSquares(nodes);

        //Optimize pieces of code for each multivector to be rendered
        LinkedList<CodePiece> codePieces = optimizeCodePieces(myNodes);
        
        //differentiate each item of codePieces with respect to t with the help of maxima  to _V_PRODUCT_SD
        diffentiateCodePieces(codePieces);
        
        return codePieces;
    }
    
    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> assignmentNodes) {
        LinkedList<CodePiece> codePieces = prepareGraph(assignmentNodes);
        
        HashMap<String, LinkedList<Point3d>> result = new HashMap<String, LinkedList<Point3d>>();
        for (CodePiece cp: codePieces) {
            //search zero locations of mv cp.name in every CodePiece cp
            LinkedList<Point3d> points = searchZeroLocations(cp, globalValues);
            result.put(cp.nameOfMultivector, points);
        }
        return result;
    }
    
    private void listInsertBefore(LinkedList<AssignmentNode> list, AssignmentNode toInsert, AssignmentNode before) {
        LinkedList<AssignmentNode> listCopy = new LinkedList<AssignmentNode>(list);
        list.clear();
        for (AssignmentNode node: listCopy) {
            if (node == before)
                list.add(toInsert);
            list.add(node);
        }
    } 
    
    private LinkedList<Point3d> searchZeroLocations(CodePiece cp, HashMap<MultivectorComponent, Double> globalValues) {
        LinkedList<Point3d> points = new LinkedList<Point3d>();
        float a = cubeEdgeLength;
        float dist = density;
        
        int processorCount = Runtime.getRuntime().availableProcessors();
        
        RayMethodThread[] threads = new RayMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            float from = (i*2*a)/((float) processorCount) - a;
            float to = ((i != processorCount-1) ? ((i+1)*2*a)/((float) processorCount) : 2*a) - a; 

            threads[i] = new RayMethodThread(from, to, a, dist, globalValues, cp);
            threads[i].start();
        }

        for (int i=0;i<threads.length;i++) {
            try {
                threads[i].join();
                points.addAll(threads[i].points);
            } catch (InterruptedException ex) {
                Logger.getLogger(DiscreteCubeMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return points;
    }
    
    @Override
    public String toString() {
        return "Ray Method";
    }
}
