package de.gaalop.visualizer.zerofinding;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements a zero finder method thread, which samples a cube and searches at
 * every sample point in a neighborhood along the gradient a zero point
 * @author Christian Steinmetz
 */
public class GradientMethodThread extends Thread {
    
    private float fromOX_Incl;
    private float toOX_Excl;
    
    private float a ;
    private float dist;
    
    private HashMap<MultivectorComponent, Double> globalValues;
    
    private CodePiece codePiece;
            
    public LinkedList<Point3d> points = new LinkedList<Point3d>();
    
    private int max_n;
    private double epsilon;
    
    private boolean renderIn2d;

    public GradientMethodThread(float fromOX_Incl, float toOX_Excl, float a, float dist, HashMap<MultivectorComponent, Double> globalValues, CodePiece codePiece, double epsilon, int max_n, boolean renderIn2d) {
        this.fromOX_Incl = fromOX_Incl;
        this.toOX_Excl = toOX_Excl;
        this.a = a;
        this.dist = dist;
        this.globalValues = globalValues;
        this.codePiece = codePiece;
        this.epsilon = epsilon;
        this.max_n = max_n;
        this.renderIn2d = renderIn2d;
    }

    @Override
    public void run() {
        if (renderIn2d)
            run2d();
        else 
            run3d();
    }
    
    private void run2d() {
        for (float ox = fromOX_Incl; ox <= toOX_Excl; ox += dist)
            for (float oy = -a; oy <= a; oy += dist) {
                float[] resultSearch = searchInNeighborhood(ox,oy,0);
                if (resultSearch != null) 
                    points.add(new Point3d(resultSearch[0],resultSearch[1],resultSearch[2]));
            }
    }
    
    private void run3d() {
        for (float ox = fromOX_Incl; ox <= toOX_Excl; ox += dist)
            for (float oy = -a; oy <= a; oy += dist)
                for (float oz = -a; oz <= a; oz += dist) {
                    float[] resultSearch = searchInNeighborhood(ox,oy,oz);
                    if (resultSearch != null) 
                        points.add(new Point3d(resultSearch[0],resultSearch[1],resultSearch[2]));
                }
    }
    
    /**
     * Searches zero points in the neighborhood of a certain point ox,oy,oz along the gradient
     * @param ox The x-coordinate of the point
     * @param oy The y-coordinate of the point
     * @param oz The z-coordinate of the point
     * @return null, if no zero point was found, other an array with the three coordinates of the zero point
     */
    private float[] searchInNeighborhood(float ox, float oy, float oz) {
        double distDir = 1;
        EvaluationResult eval;
        eval = evaluate(ox, oy, oz);
        int n = 1;
        double glastx=eval.gradient.x;
        double glasty=eval.gradient.y;
        double glastz=eval.gradient.z;
        while (Math.abs(eval.f)>epsilon && n<=max_n) {
            //Gradient: [eval.dx,eval.dy,eval.dz]
            eval.gradient.normalize();
            if (glastx*eval.gradient.x+glasty*eval.gradient.y+glastz*eval.gradient.z<0) {
                distDir/=2;
            }
            
            
            //Go into direction of negative gradient
            ox -= eval.gradient.x*distDir;
            oy -= eval.gradient.y*distDir;
            oz -= eval.gradient.z*distDir;
            
            
            glastx=eval.gradient.x;
            glasty=eval.gradient.y;
            glastz=eval.gradient.z;
            eval = evaluate(ox, oy, oz);
            n++;
        }
        
        if (Math.abs(eval.f)<=epsilon) 
            return new float[] {ox,oy,oz};
        else
            return null;
    }
    
    /**
     * Evaluates the code piece on a special point ox,oy,oz and returns the result
     * @param ox The x-coordinate of the point
     * @param oy The y-coordinate of the point
     * @param oz The z-coordinate of the point
     * @return The evaluation result
     */
    private EvaluationResult evaluate(float ox, float oy, float oz) {
        final String productName = codePiece.nameOfMultivector;
        HashMap<MultivectorComponent, Double> valuesIn = new HashMap<MultivectorComponent, Double>(globalValues);
        valuesIn.put(new MultivectorComponent("_V_ox", 0), (double) ox);
        valuesIn.put(new MultivectorComponent("_V_oy", 0), (double) oy);
        valuesIn.put(new MultivectorComponent("_V_oz", 0), (double) oz);
        Evaluater evaluater = new Evaluater(valuesIn);
        evaluater.evaluate(codePiece);
        HashMap<MultivectorComponent, Double> valuesOut = evaluater.getValues();
        
        EvaluationResult result = new EvaluationResult();
        result.f = valuesOut.get(new MultivectorComponent(productName,0));
        result.gradient = new VecN3(
                valuesOut.get(new MultivectorComponent(productName+"Dx",0)),
                valuesOut.get(new MultivectorComponent(productName+"Dy",0)),
                valuesOut.get(new MultivectorComponent(productName+"Dz",0))
                );
        return result;
    }
    
    

}
