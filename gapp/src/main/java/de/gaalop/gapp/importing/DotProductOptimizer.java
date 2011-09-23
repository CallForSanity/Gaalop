package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectType;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Optimizes the order of the elements of a row in a DotProduct instance,
 * so that less as possible multivectors are in one column
 * @author Christian Steinmetz
 */
public class DotProductOptimizer {

    private int height;

    private Constant[] constantsVector = null;
    private HashMap<String, LinkedList<MvComponent[]>> mapVectors = new HashMap<String, LinkedList<MvComponent[]>>();


    /**
    * Optimizes the order of the elements of a row in a DotProduct instance,
    * so that less as possible multivectors are in one column
     * @param dotProduct The DotProduct instance
     */
    public void optimizeOrder(DotProduct dotProduct) {
        DotProduct optimized = new DotProduct();
        height = dotProduct.getHeight();
        
        splitToNameVectors(dotProduct);

        if (constantsVector != null)
            optimized.getFactors().add(arrToVector(constantsVector));

        for (String key: mapVectors.keySet())
            for (ParallelObject[] element: mapVectors.get(key))
                optimized.getFactors().add(arrToVector(element));

        optimized.computeWidthAndHeight();
        dotProduct.setFactors(optimized.getFactors());
        dotProduct.computeWidthAndHeight();
        
    }

    /**
     * Splits the DotProduct instance to factors.
     * Each factor has only components from a single multivector
     * @param dotProduct The DotProduct instance
     */
    private void splitToNameVectors(DotProduct dotProduct) {
        for (ParallelVector vector: dotProduct.getFactors()) {
            int row = 0;
            for (ParallelObject object: vector.getSlots()) {
                switch (ParallelObjectType.getType(object)) {
                    case constant:
                        Constant constant = (Constant) object;
                        if (Math.abs(constant.getValue()-1) > 1E-04)
                            insertConstant(row, constant);
                        break;
                    case mvComponent:
                        insertMvComponent(row, (MvComponent) object);
                        break;
                    default:
                        System.err.println("The type "+ParallelObjectType.getType(object)+" should not appear in a dotProoduct!");
                }
                row++;
            }
        }
    }


    /**
     * Inserts a Constant in a row
     * @param row The row
     * @param constant The Constant
     */
    private void insertConstant(int row, Constant constant) {
        if (constantsVector == null) constantsVector = new Constant[height];

        if (constantsVector[row] == null)
            constantsVector[row] = new Constant(constant.getValue());
        else
            constantsVector[row].setValue(constantsVector[row].getValue()*constant.getValue());

        if (constant.isNegated()) constantsVector[row].negate();
    }

    /**
     * Inserts a MvComponent in a row
     * @param row The row
     * @param mvComponent The MvComponent
     */
    private void insertMvComponent(int row, MvComponent mvComponent) {
        String name = mvComponent.getMultivectorComponent().getName();

        if (!mapVectors.containsKey(name)) 
            mapVectors.put(name, new LinkedList<MvComponent[]>());
        
        LinkedList<MvComponent[]> list = mapVectors.get(name);
        for (MvComponent[] element: list) 
            if (element[row] == null) {
                element[row] = mvComponent;
                return;
            }

        MvComponent[] arr = new MvComponent[height];
        arr[row] = mvComponent;
        list.add(arr);
    }

    /**
     * Creates a ParallelVector from an array of ParallelObjects
     * @param arr The array
     * @return The ParallelVector
     */
    private ParallelVector arrToVector(ParallelObject[] arr) {
        ParallelVector vector = new ParallelVector();

        for (ParallelObject obj: arr)
            vector.getSlots().add((obj != null) ? obj: new Constant(1));
        
        return vector;
    }

}
