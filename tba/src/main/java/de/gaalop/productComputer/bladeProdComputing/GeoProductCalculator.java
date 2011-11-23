package de.gaalop.productComputer.bladeProdComputing;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeOperations.BladeNormaliser;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.tba.Algebra;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Calculates the geometric product of two blades
 * @author Christian Steinmetz
 */
public class GeoProductCalculator extends ProductCalculator {

    public GeoProductCalculator(Algebra algebra, AlgebraDefinitionTC algebraDefinition) {
        super(algebra, algebraDefinition);
    }

    @Override
    public Expression calculateProductBlades(TCBlade blade1, TCBlade blade2) {
        //approach method from the dissertation: Daniel Fontijne, "Efficient Implementation of Geometric Algebra" (2007)

        LinkedList<String> list1 = new LinkedList<String>(Arrays.asList(blade1.getBase()));
        LinkedList<String> list2 = new LinkedList<String>(Arrays.asList(blade2.getBase()));

        boolean negate = false;

        list1.remove("1");
        list2.remove("1");

        String element;
        while ((element = commonElement(list1, list2)) != null) {
            int disp1 = list1.size()-list1.indexOf(element)-1;
            int disp2 = list2.indexOf(element);
            if (((disp1+disp2) % 2) == 1)
                negate = !negate;
            list1.remove(element);
            list2.remove(element);
            if (algebraDefinition.baseSquares.get(element) == -1) negate = !negate;
        }

        LinkedList<String> merged = new LinkedList<String>();
        merged.addAll(list1);
        merged.addAll(list2);
        String[] arr = merged.toArray(new String[0]);
        
        BladeNormaliser normaliser = new BladeNormaliser(algebraDefinition);
        byte normalizeFactor = normaliser.normalize(arr);
        if (negate) normalizeFactor *= -1;
        int index = getIndexFromBase(arr);

        Variable variable = new Variable("G"+index);
        return (normalizeFactor == 1) ? variable : new Negation(variable);
    }

    /**
     * Returns a common element of two lists
     * @param list1 The first list
     * @param list2 The second list
     * @return A common element
     */
    private String commonElement(LinkedList<String> list1, LinkedList<String> list2) {
        for (String cur: list1)
            if (list2.contains(cur))
                return cur;
        return null;
    }


}
