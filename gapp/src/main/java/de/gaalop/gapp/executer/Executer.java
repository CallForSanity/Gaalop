package de.gaalop.gapp.executer;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.visitor.CFGGAPPVisitor;
import de.gaalop.tba.UseAlgebra;
import java.util.Arrays;
import java.util.HashMap;

/**
 * GAPP Visitor to execute (simulate) a GAPP Program
 * @author Christian Steinmetz
 */
public class Executer extends CFGGAPPVisitor {

    /**
     * Maps names of multivectors to their values
     */
    private HashMap<String,MultivectorWithValues> values = new HashMap<String,MultivectorWithValues>();

    /**
     * Maps names of vectors to their values
     */
    private HashMap<String,VectorWithValues> vectors = new HashMap<String,VectorWithValues>();

    /**
     * The currently used algebra
     */
    private UseAlgebra usedAlgebra;

    /**
     * Maps the scalar inputs to their values
     */
    private HashMap<String,Float> inputValues;

    /**
     * Returns the MultivectorWithValues object which stores the current values
     * of the multivector with a specific name
     * @param name The name of the multivector whose values were returned
     * @return The values of the multivector with the given name
     */
    public MultivectorWithValues getValue(String name) {
        return values.get(name);
    }

    public HashMap<String, MultivectorWithValues> getValues() {
        return values;
    }

    public Executer(UseAlgebra usedAlgebra, HashMap<String,Float> inputValues) {
        this.inputValues = inputValues;
        this.usedAlgebra = usedAlgebra;
    }


    /**
     * Returns the MultivectorWithValues object which stores the current values
     * of the multivector with a specific name, if it exits in values map, or create a new values object otherwise.
     *
     * The possible new values object will be putted in the values map
     *
     * The difference to the public method getValue is that this method creates a new values object,
     * if the multivector with the given name is not in values map
     *
     * @param name The name of the multivector whose values were returned
     * @return The values of the multlivector with the given name
     */
    private MultivectorWithValues getMultivector(String name) {
        if (values.containsKey(name))
            return values.get(name);
        else {
            MultivectorWithValues newMv = new MultivectorWithValues(usedAlgebra.getBladeCount());
            values.put(name, newMv);
            return newMv;
        }
    }

    /**
     * Returns the VectorWithValues object which stores the current values
     * of the vector with a specific name, if it exits in vectors map, or create a new values object otherwise.
     *
     * The possible new values object will be putted in the vectors map
     *
     * @param name The name of the vector whose values were returned
     * @return The values of the vector with the given name
     */
    private VectorWithValues getVector(String name) {
        if (vectors.containsKey(name))
            return vectors.get(name);
        else {
            VectorWithValues newVec = new VectorWithValues();
            vectors.put(name, newVec);
            return newVec;
        }
    }

    /**
     * Returns the value of the scalar input variable with a specific name
     * @param name The name of the scalar input variable
     * @return The value of the calar input variable with the given name
     */
    private float getVariableValue(String name) {
        return inputValues.get(name);
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {

        MultivectorWithValues destination = getMultivector(gappAddMv.getDestinationMv().getName());
        MultivectorWithValues source = getMultivector(gappAddMv.getSourceMv().getName());

        Selectorset selSrc = gappAddMv.getSelectorsSrc();
        Selectorset selDest = gappAddMv.getSelectorsDest();

        int selCount = gappAddMv.getSelectorsSrc().size();
        for (int sel=0;sel<selCount;sel++) {
            Selector sDest = selDest.get(sel);
            Selector sSrc = selSrc.get(sel);

            destination.getEntries()[sDest.getIndex()] += sDest.getSign()*sSrc.getSign()*source.getEntries()[sSrc.getIndex()];
        }

        return null;
    }
    
    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        getMultivector(gappResetMv.getDestinationMv().getName()).clear();
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        MultivectorWithValues destination = getMultivector(gappSetMv.getDestinationMv().getName());
        MultivectorWithValues source = getMultivector(gappSetMv.getSourceMv().getName());

        Selectorset selSrc = gappSetMv.getSelectorsSrc();
        Selectorset selDest = gappSetMv.getSelectorsDest();

        int selCount = gappSetMv.getSelectorsSrc().size();
        for (int sel=0;sel<selCount;sel++) {
           Selector sDest = selDest.get(sel);
            Selector sSrc = selSrc.get(sel);

            destination.getEntries()[sDest.getIndex()] = sDest.getSign()*sSrc.getSign()*source.getEntries()[sSrc.getIndex()];
        }

        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        VectorWithValues vector1 = getVector(gappDotVectors.getPart1().getName());
        VectorWithValues vector2 = getVector(gappDotVectors.getPart2().getName());

        MultivectorWithValues destination = getMultivector(gappDotVectors.getDestination().getName());

        // calculate the dot product
        float sum = 0;
        int size = vector1.getEntries().length;
        for (int slot = 0;slot<size;slot++) 
            sum += vector1.getEntry(slot) * vector2.getEntry(slot);

        Selector sDest = gappDotVectors.getDestSelector();

        destination.setEntry(sDest.getIndex(), sDest.getSign()*sum);
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {

        VectorWithValues destination = getVector(gappSetVector.getDestination().getName());
        MultivectorWithValues source = getMultivector(gappSetVector.getSourceMv().getName());
        Selectorset selSrc = gappSetVector.getSelectorsSrc();

        int selCount = gappSetVector.getSelectorsSrc().size();
        destination.setEntries(new float[selCount]);
        for (int sel=0;sel<selCount;sel++) {
            Selector sSrc = selSrc.get(sel);

            destination.setEntry(sel,sSrc.getSign()*source.getEntries()[sSrc.getIndex()]);
        }

        return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        MultivectorWithValues destination = getMultivector(gappAssignMv.getDestinationMv().getName());

        Selectorset selector = gappAssignMv.getSelectors();
        int selCount = selector.size();
        for (int sel=0;sel<selCount;sel++) {
            GAPPValueHolder scalarVar = gappAssignMv.getValues().get(sel);
            float value = (scalarVar.isVariable())
                    ? getVariableValue(((GAPPVariable) scalarVar).getName())
                    : ((GAPPConstant) scalarVar).getValue();
            destination.getEntries()[selector.get(sel).getIndex()] = selector.get(sel).getSign()*value;
        }

        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculate, Object arg) {

        MultivectorWithValues mv1 = getMultivector(gappCalculate.getOperand1().getName());

        MultivectorWithValues mv2 = null;
        if (gappCalculate.getOperand2() != null)
            mv2 = getMultivector(gappCalculate.getOperand2().getName());

        Selectorset sel = gappCalculate.getUsed1();
        Selectorset sel2 = gappCalculate.getUsed2();

        for (int j = 0;j<sel.size();j++) {
            int component = Math.abs(sel.get(j).getIndex());
            float op1 = mv1.getEntry(component)*sel.get(j).getSign();

            float op2 = 0;
            if (gappCalculate.getOperand2() != null)
                op2 = mv2.getEntry(Math.abs(sel2.get(j).getIndex()))*sel2.get(j).getSign();

            MultivectorWithValues target = getMultivector(gappCalculate.getTarget().getName());

            float result;

            switch (gappCalculate.getType()) {
                case ABS:
                    result = Math.abs(op1);
                    break;
                case ACOS:
                    result = (float) Math.acos(op1);
                    break;
                case ASIN:
                    result = (float) Math.asin(op1);
                    break;
                case ATAN:
                    result = (float) Math.atan(op1);
                    break;
                case CEIL:
                    result = (float) Math.ceil(op1);
                    break;
                case COS:
                    result = (float) Math.cos(op1);
                    break;
                case DIVISION:
                    result = op1 / op2;
                    break;
                case EXP:
                    result = (float) Math.exp(op1);
                    break;
                case EXPONENTIATION:
                    result = (float) Math.pow(op1,op2);
                    break;
                case FACT:
                    result = 1;
                    for (int i=2;i<=(int) op1;i++)
                        result *= i;
                    break;
                case FLOOR:
                    result = (float) Math.floor(op1);
                    break;
                case LOG:
                    result = (float) Math.log(op1);
                    break;
                case SIN:
                    result = (float) Math.sin(op1);
                    break;
                case SQRT:
                    result = (float) Math.sqrt(op1);
                    break;
                case TAN:
                    result = (float) Math.tan(op1);
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }

            target.setEntry(component, result);


        }

        return null;


    }

    /**
     * Prints all values, vectors and their contents.
     * 
     * This is used commonly for debug purposes.
     */
    public void printAllValues() {
        String[] keys = values.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String m: keys) {
            System.out.println(m+" = "+Arrays.toString(values.get(m).getEntries()));
        }

        keys = vectors.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String m: keys) {
            System.out.println(m+" = "+Arrays.toString(vectors.get(m).getEntries()));
        }
    }

}
