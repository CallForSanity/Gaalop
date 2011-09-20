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
import de.gaalop.gapp.variables.GAPPMultivector;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This visitor creates assignments from a parallelObjects data structure
 * @author Christian Steinmetz
 */
public class AssignmentCreator implements ParallelObjectVisitor {
    // arg is the resulting MultivectorComponent; 

    private LinkedList<Assignment> assignments = new LinkedList<Assignment>();
    private int curTmpIndex = 0;

    private String createTMP() {
        curTmpIndex++;
        return "sTmp"+curTmpIndex;
    }

    public LinkedList<Assignment> getAssignments() {
        return assignments;
    }

        // if this is the root object, the GAPPMvComponent is created from name = (String) arg.
        // otherwise a new GAPPScalarVariable with a temporary name is created
    private GAPPMultivectorComponent castMv(Object arg) {
        if (arg == null)
            return new GAPPMultivectorComponent(createTMP(),0);
        else {
            MultivectorComponent mvC = (MultivectorComponent) arg;
            return new GAPPMultivectorComponent(mvC.getName(),mvC.getBladeIndex());
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

        GAPPMultivector mv = new GAPPMultivector(dest.getName(), null);
        assignments.add(new Assignment(mv,dest.getBladeIndex(), mapSummandScalarproduct));
        return null;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        Scalarproduct result = new Scalarproduct();
        for (ParallelObject object: product.getFactors()) {
            Scalarproduct scalarproduct = (Scalarproduct) object.accept(this, null);
            result.getObjects().addAll(scalarproduct.getObjects());
        }

        GAPPMultivector mv = new GAPPMultivector(((MultivectorComponent) arg).getName(), null);
        int index = ((MultivectorComponent) arg).getBladeIndex();
        if (arg != null) return assignScalarproductToMvComponent(result, product, mv, index);
        return result;
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        Scalarproduct result = createScalarproductFromOneParallelObject(extCalculation);

        if (arg != null) {
            GAPPMultivector mv = new GAPPMultivector(((MultivectorComponent) arg).getName(), null);
            int index = ((MultivectorComponent) arg).getBladeIndex();
            return assignScalarproductToMvComponent(result, extCalculation, mv, index);
        }
        else {
                GAPPMultivector mv = new GAPPMultivector(createTMP(), null);
                return assignScalarproductToMvComponent(result, extCalculation, mv, 0);
        }
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        Scalarproduct result = createScalarproductFromOneParallelObject(constant);
        GAPPMultivector mv = new GAPPMultivector(((MultivectorComponent) arg).getName(), null);
        int index = ((MultivectorComponent) arg).getBladeIndex();
        if (arg != null) return assignScalarproductToMvComponent(result, constant, mv, index);
        return result;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        Scalarproduct result = createScalarproductFromOneParallelObject(mvComponent);
        GAPPMultivector mv = new GAPPMultivector(((MultivectorComponent) arg).getName(), null);
        int index = ((MultivectorComponent) arg).getBladeIndex();
        if (arg != null) return assignScalarproductToMvComponent(result, mvComponent, mv, index);
        return result;
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

    private Object assignScalarproductToMvComponent(Scalarproduct scalarproduct, ParallelObject object, GAPPMultivector arg, int index) {
        HashMap<SignedSummand,Scalarproduct> map = new HashMap<SignedSummand, Scalarproduct>();
        map.put(new SignedSummand(true, object),scalarproduct);
        assignments.add(new Assignment(arg, index, map));
        return null;
    }


}
