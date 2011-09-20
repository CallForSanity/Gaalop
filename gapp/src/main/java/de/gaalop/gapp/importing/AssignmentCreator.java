package de.gaalop.gapp.importing;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectVisitor;
import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import de.gaalop.gapp.importing.parallelObjects.Sum;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This visitor creates assignments from a parallelObjects data structure
 * @author Christian Steinmetz
 */
public class AssignmentCreator implements ParallelObjectVisitor {
    // arg is the destination (GAPPMultivectorComponent);
    // if arg==null the destination can be created
    // the returned value is a Scalarproduct instance

    private LinkedList<Assignment> assignments = new LinkedList<Assignment>();
    private int curTmpIndex = 0;

    private String createTMP() {
        curTmpIndex++;
        return "sTmp"+curTmpIndex;
    }

    public LinkedList<Assignment> getAssignments() {
        return assignments;
    }

    private GAPPMultivectorComponent castMv(Object arg) {
        if (arg == null)
            return new GAPPMultivectorComponent(createTMP(),0);
        else {
            return (GAPPMultivectorComponent) arg;
        }

    }

    @Override
    public Object visitSum(Sum sum, Object arg) {
        // create the GAPPVariable of the destination variable of sum:
        GAPPMultivectorComponent dest = castMv(arg);

        HashMap<SignedSummand,Scalarproduct> mapSummandScalarproduct = new HashMap<SignedSummand, Scalarproduct>();

        //fill HashMap with the summands
        for (SignedSummand summand: sum.getSummands()) {
            Scalarproduct scalarproduct = (Scalarproduct) summand.getParallelObject().accept(this, null);
            mapSummandScalarproduct.put(summand, scalarproduct);
        }

        assignments.add(new Assignment(dest.getName(),dest.getBladeIndex(), mapSummandScalarproduct));

        if (arg == null) {
            Scalarproduct result = new Scalarproduct();
            result.getObjects().add(new MvComponent(new MultivectorComponent(dest.getName(),dest.getBladeIndex())));
            return result;
        } else
            return null;
    }

    private Object assignScalarproductToMvComponent(Scalarproduct scalarproduct, ParallelObject object, String name, int index) {
        HashMap<SignedSummand,Scalarproduct> map = new HashMap<SignedSummand, Scalarproduct>();
        map.put(new SignedSummand(true, object), scalarproduct);
        assignments.add(new Assignment(name, index, map));
        return null;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        GAPPMultivectorComponent dest = castMv(arg);

        Scalarproduct result = new Scalarproduct();
        for (ParallelObject object: product.getFactors()) {
            Scalarproduct scalarproduct = (Scalarproduct) object.accept(this, null);
            result.getObjects().addAll(scalarproduct.getObjects());
        }

        assignScalarproductToMvComponent(result, product, dest.getName(), dest.getBladeIndex());

        if (arg == null) {
            Scalarproduct resultS = new Scalarproduct();
            resultS.getObjects().add(new MvComponent(new MultivectorComponent(dest.getName(),dest.getBladeIndex())));
            return resultS;
        } else 
            return null;
        
    }

    /**
     * Creates a Scalarproduct instance from one scalar-significant ParallelObject instance
     * @param object The ParallelObject instance
     * @return The created Scalarproduct instance
     */
    private Scalarproduct createScalarproductFromOneParallelObject(ParallelObject object) {
        LinkedList<ParallelObject> vectors = new LinkedList<ParallelObject>();
        vectors.add(object);
        return new Scalarproduct(vectors);
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        GAPPMultivectorComponent dest = castMv(arg);

        Scalarproduct result = createScalarproductFromOneParallelObject(extCalculation);

        assignScalarproductToMvComponent(result, extCalculation, dest.getName(), dest.getBladeIndex());

        if (arg == null) {
            Scalarproduct resultS = new Scalarproduct();
            resultS.getObjects().add(new MvComponent(new MultivectorComponent(dest.getName(),dest.getBladeIndex())));
            return resultS;
        } else
            return null;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        GAPPMultivectorComponent dest = castMv(arg);

        Scalarproduct result = createScalarproductFromOneParallelObject(constant);

        assignScalarproductToMvComponent(result, constant, dest.getName(), dest.getBladeIndex());

        if (arg == null) {
            Scalarproduct resultS = new Scalarproduct();
            resultS.getObjects().add(new MvComponent(new MultivectorComponent(dest.getName(),dest.getBladeIndex())));
            return resultS;
        } else
            return null;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        GAPPMultivectorComponent dest = castMv(arg);
        
        Scalarproduct result = createScalarproductFromOneParallelObject(mvComponent);

        assignScalarproductToMvComponent(result, mvComponent, dest.getName(), dest.getBladeIndex());

        if (arg == null) {
            Scalarproduct resultS = new Scalarproduct();
            resultS.getObjects().add(new MvComponent(new MultivectorComponent(dest.getName(),dest.getBladeIndex())));
            return resultS;
        } else
            return null;
    }

}
