package de.gaalop.visualizer.zerofinding;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaDifferentiater;
import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;

/**
 * Implements a zero finder method, which samples a cube
 * @author christian
 */
public class GradientMethod extends ZeroFinder {

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
    //TODO prepare methods auslagern in Zwischenbasisklasse
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
        //differentiate each item of codePieces with respect to ox,oy,oz with the help of maxima  to _V_PRODUCT_SDx/y/z
        for (CodePiece cp: codePieces) {
            //Differntiate with respect to ox
            MaximaDifferentiater differentiater = new MaximaDifferentiater();
            LinkedList<AssignmentNode> derived;
            try {
                derived = differentiater.differentiate(cp, maximaCommand, "_V_ox");
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"Dx", 0));
                    cp.add(d);
                }
            } catch (OptimizationException ex) {
                Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RecognitionException ex) {
                Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Differntiate with respect to oy
            differentiater = new MaximaDifferentiater();
            try {
                derived = differentiater.differentiate(cp, maximaCommand, "_V_oy");
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"Dy", 0));
                    cp.add(d);
                }
            } catch (OptimizationException ex) {
                Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RecognitionException ex) {
                Logger.getLogger(RayMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Differntiate with respect to oz
            differentiater = new MaximaDifferentiater();
            try {
                derived = differentiater.differentiate(cp, maximaCommand, "_V_oz");
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"Dz", 0));
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
       
        //Insert expressions, like Maxima,  !!!
        InsertingExpression.insertExpressions(nodes);
        
        //search _V_PRODUCT and apply the sum of the squares = _V_PRODUCT_S
        //and store the result in myNodes
        LinkedList<AssignmentNode> myNodes = createSumOfSquares(nodes);

        //Optimize pieces of code for each multivector to be rendered
        LinkedList<CodePiece> codePieces = optimizeCodePieces(myNodes);
        
        //differentiate each item of codePieces with respect to ox,oy,oz with the help of maxima  to _V_PRODUCT_SDx/y/z
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

    @Override
    public String toString() {
        return "Gradient Method";
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
        
        GradientMethodThread[] threads = new GradientMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            float from = (i*2*a)/((float) processorCount) - a;
            float to = ((i != processorCount-1) ? ((i+1)*2*a)/((float) processorCount) : 2*a) - a; 

            threads[i] = new GradientMethodThread(from, to, a, dist, globalValues, cp);
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
    
}
