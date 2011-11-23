package de.gaalop.productComputer.dataStruct.visitor;

import java.util.HashMap;
import java.util.LinkedList;
import de.gaalop.algorithms.IntArray;
import de.gaalop.algorithms.Permutation;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;

/**
 * Changes the base in a TCExpression data structure
 * @author Christian Steinmetz
 */
public class TCBaseChangeVisitor extends TCReplaceVisitor {

    private HashMap<String, TCSum> map;

    public TCBaseChangeVisitor(HashMap<String, TCSum> map) {
        this.map = map;
    }

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        int[] lengths = new int[tcBlade.getBase().length];
        boolean containsConvertable = false;
        for (int i=0;i<lengths.length;i++) {
            if (map.containsKey(tcBlade.getBase()[i])) {
                lengths[i] = map.get(tcBlade.getBase()[i]).getExpressions().length;
                containsConvertable = true;
            } else
                lengths[i] = 1;
        }
        
        if (containsConvertable) {
            LinkedList<IntArray> permutations = Permutation.getPermutations(lengths);
            TCExpression[] expressions = new TCExpression[permutations.size()];
            TCSum resultSum = new TCSum(expressions);
            result = resultSum;

            

            //fill expressions
            int p = 0;
            for (IntArray permutation: permutations) {
                TCConstant con = new TCConstant(1);
                boolean negated = false;
                String[] base = new String[permutation.getArray().length];
                for (int i=0;i<base.length;i++) {
                    if (lengths[i] == 1) {
                        base[i] = tcBlade.getBase()[i];
                    } else {
                        TCSum sum = map.get(tcBlade.getBase()[i]);
                        int index = permutation.getArray()[i];

                        TCExpression e = sum.getExpressions()[index];
                        if (e instanceof TCProduct) {
                            TCProduct pr = (TCProduct) e;

                            con.setValue(((TCConstant) pr.getExpressions()[0]).getValue()*con.getValue());
                            TCBlade blade = (TCBlade) pr.getExpressions()[1];
                            if (blade.isNegated()) negated = !negated;
                            base[i] = blade.getBase()[0];

                        } else {
                            TCBlade blade = (TCBlade) e;
                            if (blade.isNegated()) negated = !negated;
                            base[i] = blade.getBase()[0];
                        }
                    }
                }

                expressions[p] = new TCBlade(base, negated);

                if (Math.abs(con.getValue()-1) > 1E-10) 
                    expressions[p] = TCProduct.create(con, expressions[p]);

                p++;
            }

            

        }

        return null;
    }

}
