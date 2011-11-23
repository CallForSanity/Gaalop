package de.gaalop.productComputer.bladeProdComputing;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.transformer.GetVariableVisitor;
import de.gaalop.tba.Algebra;

/**
 * Calculates the inner product of two blades
 * @author Christian Steinmetz
 */
public class InnerProductCalculator extends ProductCalculator {

    public InnerProductCalculator(Algebra algebra, AlgebraDefinitionTC algebraDefinition) {
        super(algebra, algebraDefinition);
    }


    @Override
    public Expression calculateProductBlades(TCBlade blade1, TCBlade blade2) {
        String[] base1 = blade1.getBase();
        String[] base2 = blade2.getBase();

        if (base1.length == 1) {
            if (base2.length == 1)
                return calculateProductBlades(base1[0], base2[0]);
            else
                return calculateProductBlades(base1[0], base2);
        } else {
            if (base2.length == 1)
                return calculateProductBlades(base1, base2[0]);
            else
                return calculateProductBlades(base1, base2);
        }

    }

    /**
     * Calculates a product of two bases, which have both more than one base element
     * @param base1 The first base
     * @param base2 The second base
     */
    private Expression calculateProductBlades(String[] base1, String[] base2) {
        

        if (base1.length > base2.length) {
            // Bl*ak, k<l

            Expression result = calculateProductBlades(base1, base2[0]);
            int k = base2.length;
            for (int i=1;i<k;i++) 
                result = stringDotMultivector(result, base2[i]);
            

            return result;
        } else {
            // ak*bl, k<l
            int k = base1.length;
            Expression result = calculateProductBlades(base1[k-1], base2);
            
            for (int i=k-2;i>=0;i--)
                result = stringDotMultivector(base1[i], result);


            return result;
        }

    }

    /**
     * Computes the inner product of a base element with an expression
     * @param base The base element
     * @param expr The expression
     * @return The inner product as Expression
     */
    private Expression stringDotMultivector(String base, Expression expr) {
        Variable v = GetVariableVisitor.getVariableInExpression(expr);
        if (v != null) {
            String[] baseArr = algebra.getBlade(Integer.parseInt(v.getName().substring(1))).getBases().toArray(new String[0]);
            Expression e = calculateProductBlades(base, baseArr);
            if (v == expr) {
                return e;
            } else {
                expr.replaceExpression(v, e);
                return expr;
            }
        } else {
            return new FloatConstant(0);
        }
    }

    /**
     * Computes the inner product of an expression with an base element
     * @param expr The expression
     * @param base The base element
     * @return The inner product as Expression
     */
    private Expression stringDotMultivector(Expression expr, String base) {
        Variable v = GetVariableVisitor.getVariableInExpression(expr);
        if (v != null) {
            String[] baseArr = algebra.getBlade(Integer.parseInt(v.getName().substring(1))).getBases().toArray(new String[0]);
            Expression e = calculateProductBlades(baseArr, base);
            if (v == expr) {
                return e;
            } else {
                expr.replaceExpression(v, e);
                return expr;
            }
        } else {
            return new FloatConstant(0);
        }
    }

    /**
     * Returns the index of a string in an string arr
     * @param search The searched string
     * @param arr The array to search in
     * @return The index, -1 if the array doesn't containt the searched string
     */
    private static int getIndexOfString(String search, String[] arr) {
        for (int i=0;i<arr.length;i++)
            if (search.equals(arr[i]))
                return i;
        return -1;
    }

    /**
     * Calculates a product of a base element and a base
     * @param base1 The base element
     * @param base2 The base
     * @return The inner product as Expression
     */
    private Expression calculateProductBlades(String base1, String[] base2) {
        int index = getIndexOfString(base1, base2);
        if (index > -1) {
            String[] arr = new String[base2.length-1];
            int j=0;
            for (int i=0;i<base2.length;i++)
                if (i != index)
                    arr[j++] = base2[i];

            Variable var = new Variable("G"+getIndexFromBase(arr));
            if (algebraDefinition.baseSquares.get(base1) == -1)
                return ((index%2) == 1) ? var: new Negation(var);
            else
                return ((index%2) == 1) ? new Negation(var): var;

        } else 
            return new FloatConstant(0);
    }

    /**
     * Calculates a product of a base and a base element
     * @param base1 The base
     * @param base2 The base element
     * @return The inner product as Expression
     */
    private Expression calculateProductBlades(String[] base1, String base2) {
        int index = getIndexOfString(base2, base1);
        if (index > -1) {
            String[] arr = new String[base1.length-1];
            int j=0;
            for (int i=0;i<base1.length;i++)
                if (i != index)
                    arr[j++] = base1[i];

            Variable var = new Variable("G"+getIndexFromBase(arr));
            if (algebraDefinition.baseSquares.get(base2) == -1) {
                return (((base1.length-index-1) %2 ) == 1) ? var: new Negation(var);
            } else
                return (((base1.length-index-1) %2 ) == 1) ? new Negation(var): var;
        } else
            return new FloatConstant(0);
    }

    /**
     * Calculates a product two base elements
     * @param base1 The base element
     * @param base2 The base element
     * @return The inner product as Expression
     */
    private Expression calculateProductBlades(String base1, String base2) {
        if (base1.equals(base2)) 
            if (!base1.equals("1"))
                return new FloatConstant(algebraDefinition.baseSquares.get(base1));
        return new FloatConstant(0);
    }

}
