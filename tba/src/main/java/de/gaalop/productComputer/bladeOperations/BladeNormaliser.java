package de.gaalop.productComputer.bladeOperations;

import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.algorithms.BubbleSortComp;
import java.util.Comparator;
import java.util.HashMap;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.visitor.TCTraversalVisitor;

/**
 * Normalizes blades
 * @author Christian Steinmetz
 */
public class BladeNormaliser extends TCTraversalVisitor {

    private HashMap<String,Integer> indices = new HashMap<String,Integer>();

    public BladeNormaliser(AlgebraDefinitionTC algebraDefinition) {
        indices = algebraDefinition.indices;
    }

    /**
     * Normalizes blades in an expression in the 5d
     * @param expression The expression
     */
    public static void normalize(TCExpression expression, AlgebraDefinitionTC algebraDefinition) {
        BladeNormaliser normalizer = new BladeNormaliser(algebraDefinition);
        expression.accept(normalizer, null);
    }

    /**
     * Normalizes the base vectors in an array
     * @param arr The array
     * @return The sorted array and the number of exchanges
     */
    public byte normalize(String[] arr) {
        BubbleSortComp<String> sort = new BubbleSortComp<String>();
        int exchangeCount = sort.doBubbleSort(arr, comparatorIndices);
        if (exchangeCount % 2 == 1)
            return -1;
        else
            return 1;
    }

    public HashMap<String, Integer> getIndices() {
        return indices;
    }

    public final Comparator<String> comparatorIndices = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return indices.get(o1) - indices.get(o2);
        }
    };

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        String[] base = tcBlade.getBase();
        
        BubbleSortComp<String> sort = new BubbleSortComp<String>();
        int exchangeCount = sort.doBubbleSort(base, comparatorIndices);
        if (exchangeCount % 2 == 1)
            tcBlade.setNegated(!tcBlade.isNegated());
        
        return null; 
    }
}
