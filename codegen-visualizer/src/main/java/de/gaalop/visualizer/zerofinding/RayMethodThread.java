package de.gaalop.visualizer.zerofinding;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.ia_math.RealInterval;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements a zero finder thread, which uses rays
 * @author Christian Steinmetz
 */
public class RayMethodThread extends Thread {
    
    private float fromOY_Incl;
    private float toOY_Excl;
    
    private float a;
    private float dist;
    private double epsilon;
    
    private HashMap<MultivectorComponent, Double> globalValues;
    
    private CodePiece codePiece;
            
    public LinkedList<Point3d> points = new LinkedList<Point3d>();
    
    private boolean renderIn2d;

    public RayMethodThread(float fromOY_Incl, float toOY_Excl, float a, float dist, HashMap<MultivectorComponent, Double> globalValues, CodePiece codePiece, double epsilon, boolean renderIn2d) {
        this.fromOY_Incl = fromOY_Incl;
        this.toOY_Excl = toOY_Excl;
        this.a = a;
        this.dist = dist;
        this.globalValues = globalValues;
        this.codePiece = codePiece;
        this.epsilon = epsilon;
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
        HashMap<MultivectorComponent, RealInterval> values = new HashMap<MultivectorComponent, RealInterval>();
        for (MultivectorComponent mvC: globalValues.keySet())
            values.put(mvC, new RealInterval(globalValues.get(mvC)));    
        values.put(new MultivectorComponent("_V_oz", 0), new RealInterval(0));
        
        float ox = -a;
        values.put(new MultivectorComponent("_V_ox", 0), new RealInterval(ox));
        
        for (float oy = fromOY_Incl; oy <= toOY_Excl; oy += dist) {
            values.put(new MultivectorComponent("_V_oy", 0), new RealInterval(oy));
            isolation(new RealInterval(0, 2*a),values);
        }
    }
    
    private void run3d() {
        HashMap<MultivectorComponent, RealInterval> values = new HashMap<MultivectorComponent, RealInterval>();
        for (MultivectorComponent mvC: globalValues.keySet())
            values.put(mvC, new RealInterval(globalValues.get(mvC)));        
        
        float ox = -a;
        values.put(new MultivectorComponent("_V_ox", 0), new RealInterval(ox));
        
        for (float oy = fromOY_Incl; oy <= toOY_Excl; oy += dist) {
            values.put(new MultivectorComponent("_V_oy", 0), new RealInterval(oy));
            for (float oz = -a; oz <= a; oz += dist) {
                values.put(new MultivectorComponent("_V_oz", 0), new RealInterval(oz));
                isolation(new RealInterval(0, 2*a),values);
            }
        }
    }
    
    /**
     * Splits an interval as long as more than one root exists in this interval
     * @param t The interval to be splitted
     * @param values The gloabal values
     */
    private void isolation(RealInterval t, HashMap<MultivectorComponent, RealInterval> values) {
        final String product = codePiece.nameOfMultivector;
        
        values.put(new MultivectorComponent("_V_t", 0), t);
        IntervalEvaluater evaluater = new IntervalEvaluater(values);
        evaluater.evaluate(codePiece);
        
        RealInterval f = values.get(new MultivectorComponent(product,0));
        if (f.lo() <= 0 && 0 <= f.hi()) {
            RealInterval df = values.get(new MultivectorComponent(product+"D",0));
            if (df.lo() <= 0 && 0 <= df.hi()) {
                if (t.hi()-t.lo() > 0.05) {
                    double center = (t.lo()+t.hi())/2.0d;
                    isolation(new RealInterval(t.lo(), center), values);
                    isolation(new RealInterval(center, t.hi()), values);
                } else {
                    double tCenter = (t.lo()+t.hi())/2.0d;
                    values.put(new MultivectorComponent("_V_t", 0), new RealInterval(tCenter));
                    evaluater = new IntervalEvaluater(values);
                    evaluater.evaluate(codePiece);
                    f = values.get(new MultivectorComponent(product,0));
                    if (Math.abs((f.lo()+f.hi())/2) <= epsilon)
                        points.add(new Point3d(
                                values.get(new MultivectorComponent("_V_ox", 0)).lo()+tCenter, 
                                values.get(new MultivectorComponent("_V_oy", 0)).lo(), 
                                values.get(new MultivectorComponent("_V_oz", 0)).lo()
                                ));
                }
            } else {
                refinement(t, values);
            }
        }

    }

    /**
     * Given an interval, where only one root exists, find the root.
     * @param t The interval
     * @param values The global values
     */
    private void refinement(RealInterval t, HashMap<MultivectorComponent, RealInterval> values) {
        final String product = codePiece.nameOfMultivector;
        
        MultivectorComponent pr = new MultivectorComponent(product, 0);
        boolean refine = true;
        double ce = 1000;
        while (refine) {
            
            double center = (t.lo()+t.hi())/2.0d;
            
            values.put(new MultivectorComponent("_V_t", 0), new RealInterval(t.lo()));
            
            IntervalEvaluater evaluater = new IntervalEvaluater(values);
            evaluater.evaluate(codePiece);

            double lo = values.get(pr).lo();
            
            values.put(new MultivectorComponent("_V_t", 0), new RealInterval(center));  
            
            evaluater = new IntervalEvaluater(values);
            evaluater.evaluate(codePiece);
            ce = values.get(pr).lo();
            
            if (Math.abs(ce) <= epsilon) refine = false;
            if (t.hi()-t.lo() < 0.001) return;
        
            if (ce*lo < 0) 
                t = new RealInterval(t.lo(),center);
            else
                t = new RealInterval(center,t.hi());

        }

        if (Math.abs(ce) <= epsilon)
            points.add(new Point3d(
                    values.get(new MultivectorComponent("_V_ox", 0)).lo()+(t.lo()+t.hi())/2.0d, 
                    values.get(new MultivectorComponent("_V_oy", 0)).lo(), 
                    values.get(new MultivectorComponent("_V_oz", 0)).lo()
                    ));

    }

}
