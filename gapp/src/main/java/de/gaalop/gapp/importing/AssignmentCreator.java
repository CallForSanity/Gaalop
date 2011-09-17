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
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVariable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This visitor creates assignments from a parallelObjects data structure
 * @author Christian Steinmetz
 */
public class AssignmentCreator implements ParallelObjectVisitor {
    // arg is the resulting MultivectorComponent; or null, if the object is not the root object

    private LinkedList<Assignment> assignments = new LinkedList<Assignment>();
    private int curTmpIndex = 0;

    private String createTMP() {
        curTmpIndex++;
        return "sTmp"+curTmpIndex;
    }

    public LinkedList<Assignment> getAssignments() {
        return assignments;
    }

    @Override
    public Object visitSum(Sum sum, Object arg) {
        //This is the only method, which creates a new temporary variable!

        // create the GAPPVariable of the destination variable of sum:
        // if this is the root object, the GAPPMvComponent is created from name = (String) arg.
        // otherwise a new GAPPScalarVariable with a temporary name is created
        MultivectorComponent mvC = (MultivectorComponent) arg;
        GAPPVariable dest = (arg != null)
                ? new GAPPMultivectorComponent(mvC.getName(),mvC.getBladeIndex())
                : new GAPPScalarVariable(createTMP());

        HashMap<SignedSummand,Scalarproduct> mapSummandScalarproduct = new HashMap<SignedSummand, Scalarproduct>();

        //fill HashMap and determine max number of vectors in a scalarproduct
        int maxVectors = 0;
        for (SignedSummand summand: sum.getSummands()) {
            Scalarproduct scalarproduct = (Scalarproduct) summand.getParallelObject().accept(this, null);
            mapSummandScalarproduct.put(summand, scalarproduct);
            maxVectors = Math.max(maxVectors,scalarproduct.getVectors().size());
        }

        assignments.add(new Assignment(dest, mapSummandScalarproduct));
        return null;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        Scalarproduct result = new Scalarproduct();
        for (ParallelObject object: product.getFactors()) {
            Scalarproduct scalarproduct = (Scalarproduct) object.accept(this, null);
            result.getVectors().addAll(scalarproduct.getVectors());
        }

        if (arg != null) return assignScalarproductToMvComponent(result, product, arg);
        return result;
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        Scalarproduct result = createScalarproductFromOneParallelObject(extCalculation);
        if (arg != null) return assignScalarproductToMvComponent(result, extCalculation, arg);
        return result;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        Scalarproduct result = createScalarproductFromOneParallelObject(constant);
        if (arg != null) return assignScalarproductToMvComponent(result, constant, arg);
        return result;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        Scalarproduct result = createScalarproductFromOneParallelObject(mvComponent);
        if (arg != null) return assignScalarproductToMvComponent(result, mvComponent, arg);
        return result;
    }

    /**
     * Creates a Scalarproduct instance from one scalar-significant ParallelObject instance
     * @param object The ParallelObject instance
     * @return The created Scalarproduct instance
     */
    private Scalarproduct createScalarproductFromOneParallelObject(ParallelObject object) {
        LinkedList<ParallelVector> vectors = new LinkedList<ParallelVector>();
        ParallelVector vector = new ParallelVector();
        vector.getSlots().add(object);
        vectors.add(vector);
        return new Scalarproduct(vectors);
    }

    private Object assignScalarproductToMvComponent(Scalarproduct scalarproduct, ParallelObject object, Object arg) {
        HashMap<SignedSummand,Scalarproduct> map = new HashMap<SignedSummand, Scalarproduct>();
        map.put(new SignedSummand(true, object),scalarproduct);
        
        MultivectorComponent mvC = (MultivectorComponent) arg;
        assignments.add(new Assignment(new GAPPMultivectorComponent(mvC.getName(),mvC.getBladeIndex()), map));
        return null;
    }


}
