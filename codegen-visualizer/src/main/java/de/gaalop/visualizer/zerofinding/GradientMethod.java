package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a zero finder method, which samples a cube and searches at
 * every sample point in a neighborhood along the gradient a zero point
 * 
 * @author Christian Steinmetz
 */
public class GradientMethod extends PrepareZerofinder {

    /**
     * Replaces x by ox+t, y by oy and z by oz in a list of assignment nodes
     * @param nodes The list of assignment nodes
     */
    public static void replace(LinkedList<AssignmentNode> nodes) {
        //replace x=ox+t,y=oy,z=oz
        ReplaceVisitor visitor = new ReplaceVisitor() {

            private void visitVar(Variable node) {
                if (node.getName().equals("_V_X"))
                    result = new MultivectorComponent("_V_ox", 0);
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
    
    /**
     * Differentiate the codepieces with respect to ox,oy,z
     * @param codePieces 
     */
    private void diffentiateCodePieces(LinkedList<CodePiece> codePieces) {
        //differentiate each item of codePieces with respect to ox,oy,oz with the help of maxima  to _V_PRODUCT_SDx/y/z
        for (CodePiece cp: codePieces) {
            //Differntiate with respect to ox
            LinkedList<AssignmentNode> derived;
            derived = differentiater.differentiate(cp, new MultivectorComponent("_V_ox",0));
            if (derived != null)
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"Dx", 0));
                    cp.add(d);
                }
            //Differntiate with respect to oy
            derived = differentiater.differentiate(cp, new MultivectorComponent("_V_oy",0));
            if (derived != null)
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"Dy", 0));
                    cp.add(d);
                }
            //Differntiate with respect to oz
            derived = differentiater.differentiate(cp, new MultivectorComponent("_V_oz",0));
            if (derived != null)
                for (AssignmentNode d: derived) {
                    d.setVariable(new MultivectorComponent(d.getVariable().getName()+"Dz", 0));
                    cp.add(d);
                }
        }
    }
    
    /**
     * Prepares the graph, given by a list of assignment nodes, i.e. create code pieces, ...
     * @param nodes The list of nodes
     * @return The generated code pieces
     */
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
        
        //differentiate each item of codePieces with respect to ox,oy,oz with the help of maxima  to _V_PRODUCT_SDx/y/z
        diffentiateCodePieces(codePieces);
        
        return codePieces;
    }

    @Override
    public HashMap<String, LinkedList<Point3d>> findZeroLocations(HashMap<MultivectorComponent, Double> globalValues, LinkedList<AssignmentNode> assignmentNodes, HashMap<String, String> mapSettings, boolean renderIn2d) {
        int a = Integer.parseInt(mapSettings.get("cubeEdgeLength"));
        float dist = Float.parseFloat(mapSettings.get("density"));
        double epsilon = Double.parseDouble(mapSettings.get("epsilon"));
        int max_n = Integer.parseInt(mapSettings.get("max_n"));

        LinkedList<CodePiece> codePieces = prepareGraph(assignmentNodes);
        
        HashMap<String, LinkedList<Point3d>> result = new HashMap<String, LinkedList<Point3d>>();
        for (CodePiece cp: codePieces) {
            //search zero locations of mv cp.name in every CodePiece cp
            LinkedList<Point3d> points = searchZeroLocations(cp, globalValues, a, dist, epsilon, max_n, renderIn2d);
            result.put(cp.nameOfMultivector, points);
        }
        return result;
    }

    @Override
    public String getName() {
        return "Gradient Method";
    }
    
    /**
     * Searches zero locations in a neigboorhood in a code piece,
     * starts a number of search threads
     * @param cp The code piece
     * @param globalValues The global initialised values
     * @return The zero locations points
     */
    private LinkedList<Point3d> searchZeroLocations(CodePiece cp, HashMap<MultivectorComponent, Double> globalValues, int a, float dist, double epsilon, int max_n, boolean renderIn2d) {
        LinkedList<Point3d> points = new LinkedList<Point3d>();

        int processorCount = Runtime.getRuntime().availableProcessors();
        
        GradientMethodThread[] threads = new GradientMethodThread[processorCount];
        for (int i=0;i<processorCount;i++) {
            float from = (i*2*a)/((float) processorCount) - a;
            float to = ((i != processorCount-1) ? ((i+1)*2*a)/((float) processorCount) : 2*a) - a; 

            threads[i] = new GradientMethodThread(from, to, a, dist, globalValues, cp, epsilon, max_n, renderIn2d);
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
    public HashMap<String, String> getSettings() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("cubeEdgeLength", "5");
        result.put("density", "1");
        result.put("epsilon", "1E-4");
        result.put("max_n", "50");
        return result;
    }
    
}
