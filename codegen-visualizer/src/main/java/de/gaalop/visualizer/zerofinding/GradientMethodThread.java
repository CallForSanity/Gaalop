package de.gaalop.visualizer.zerofinding;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements a zero finder thread, which uses rays
 * @author christian
 */
public class GradientMethodThread extends Thread {
    
    private float fromOX_Incl;
    private float toOX_Excl;
    
    private float a ;
    private float dist;
    
    private HashMap<MultivectorComponent, Double> globalValues;
    
    private CodePiece codePiece;
            
    public LinkedList<Point3d> points = new LinkedList<Point3d>();

    public GradientMethodThread(float fromOX_Incl, float toOX_Excl, float a, float dist, HashMap<MultivectorComponent, Double> globalValues, CodePiece codePiece) {
        this.fromOX_Incl = fromOX_Incl;
        this.toOX_Excl = toOX_Excl;
        this.a = a;
        this.dist = dist;
        this.globalValues = globalValues;
        this.codePiece = codePiece;
    }

    @Override
    public void run() {
            for (float ox = fromOX_Incl; ox <= toOX_Excl; ox += dist) {
                for (float oy = -a; oy <= a; oy += dist) {
                    for (float oz = -a; oz <= a; oz += dist) {
                        float[] resultSearch = searchInNeighborhood(ox,oy,oz);
                        if (resultSearch != null) 
                            points.add(new Point3d(resultSearch[0],resultSearch[1],resultSearch[2]));
                    }
                }
            }
    }
    
    private static final int MAX_N = 10;
    private static final double EPSILON = 10E-04;
    
    private float[] searchInNeighborhood(float ox, float oy, float oz) {
        double distDir = dist/MAX_N;
        EvaluationResult eval;
        eval = evaluate(ox, oy, oz);
        int n = 1;
        while (Math.abs(eval.f)>EPSILON && n<=MAX_N) {
            //Gradient: [eval.dx,eval.dy,eval.dz]
            eval.gradient.normalize();
            if (eval.f > 0) {
                //Go into direction of negative gradient
                ox -= eval.gradient.x*distDir;
                oy -= eval.gradient.y*distDir;
                oz -= eval.gradient.z*distDir;
            } else {
                //Go into direction of positive gradient
                ox += eval.gradient.x*distDir;
                oy += eval.gradient.y*distDir;
                oz += eval.gradient.z*distDir;
            }
            
            
            eval = evaluate(ox, oy, oz);
            n++;
        }
        
        if (Math.abs(eval.f)<=EPSILON) 
            return new float[] {ox,oy,oz};
        else
            return null;
    }
    
    
    private EvaluationResult evaluate(float ox, float oy, float oz) {
        final String productName = codePiece.nameOfMultivector;
        HashMap<MultivectorComponent, Double> valuesIn = new HashMap<MultivectorComponent, Double>(globalValues);
        valuesIn.put(new MultivectorComponent("_V_X", 0), (double) ox);
        valuesIn.put(new MultivectorComponent("_V_Y", 0), (double) oy);
        valuesIn.put(new MultivectorComponent("_V_Z", 0), (double) oz);
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
