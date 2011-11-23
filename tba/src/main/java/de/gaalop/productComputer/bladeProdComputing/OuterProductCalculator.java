package de.gaalop.productComputer.bladeProdComputing;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeOperations.BladeNormaliser;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.tba.Algebra;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;



/**
 * Calculates the outer product of two blades
 * @author Christian Steinmetz
 */
public class OuterProductCalculator extends ProductCalculator {

    public OuterProductCalculator(Algebra algebra, AlgebraDefinitionTC algebraDefinition) {
        super(algebra, algebraDefinition);
    }

    @Override
    public Expression calculateProductBlades(TCBlade blade1, TCBlade blade2) {
        HashSet<String> keys = new HashSet<String>();
        LinkedList<String> list = new LinkedList<String>();

        String[] arrOne = {"1"};
        if (Arrays.equals(blade1.getBase(),arrOne) && Arrays.equals(blade2.getBase(),arrOne))
            return new FloatConstant(1);

        for (String base1: blade1.getBase()) {
            if (keys.contains(base1)) {
                return new FloatConstant(0);
            } else {
                if (base1.startsWith("e")) {
                    keys.add(base1);
                    list.add(base1);
                }
            }
        }
        for (String base2: blade2.getBase()) {
            if (keys.contains(base2))
                return new FloatConstant(0);
            else {
                if (base2.startsWith("e")) {
                    keys.add(base2);
                    list.add(base2);
                }
            }
        }
        if (keys.isEmpty()) return new FloatConstant(0);
        //normalise blade
        String[] arr = list.toArray(new String[0]);
        BladeNormaliser normaliser = new BladeNormaliser(algebraDefinition);
        byte normalizeFactor = normaliser.normalize(arr);
        int index = getIndexFromBase(arr);
        Variable variable = new Variable("G"+index);
        return (normalizeFactor == 1) ? variable : new Negation(variable);

    }

}
