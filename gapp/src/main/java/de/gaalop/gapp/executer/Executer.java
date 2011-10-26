package de.gaalop.gapp.executer;

import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.CFGGAPPVisitor;
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
    private HashMap<String, MultivectorWithValues> values = new HashMap<String, MultivectorWithValues>();
    /**
     * Maps the scalar inputs to their values
     */
    private HashMap<String, Float> inputValues;

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

    public Executer(HashMap<String, Float> inputValues) {
        this.inputValues = inputValues;
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
        if (values.containsKey(name)) {
            return values.get(name);
        } else {
            System.err.println("Multivector " + name + " does not exist!");
            return null;
        }
    }

    /**
     * Creates a vector with a name and a size
     * @param name The name
     * @param size The size
     */
    private void createVector(String name, int size) {
        values.put(name, new MultivectorWithValues(size));
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
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        MultivectorWithValues mv = new MultivectorWithValues(gappResetMv.getSize());
        values.put(gappResetMv.getDestinationMv().getName(), mv);
        mv.clear();
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        MultivectorWithValues destination = getMultivector(gappSetMv.getDestination().getName());
        MultivectorWithValues source = getMultivector(gappSetMv.getSource().getName());

        Selectorset selSrc = gappSetMv.getSelectorsSrc();
        PosSelectorset selDest = gappSetMv.getSelectorsDest();

        int selCount = gappSetMv.getSelectorsSrc().size();
        for (int sel = 0; sel < selCount; sel++) {
            PosSelector sDest = selDest.get(sel);
            Selector sSrc = selSrc.get(sel);

            destination.getEntries()[sDest.getIndex()] = sSrc.getSign() * source.getEntries()[sSrc.getIndex()];
        }

        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        MultivectorWithValues destination = getMultivector(gappDotVectors.getDestination().getName());

        // calculate the dot product
        float sum = 0;
        int size = getMultivector(gappDotVectors.getParts().getFirst().getName()).getEntries().length;
        for (int slot = 0; slot < size; slot++) {
            float prod = 1;

            for (GAPPVector part : gappDotVectors.getParts()) {
                prod *= getMultivector(part.getName()).getEntry(slot);
            }

            sum += prod;
        }

        Selector sDest = gappDotVectors.getDestSelector();

        destination.setEntry(sDest.getIndex(), sDest.getSign() * sum);
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        int size = gappSetVector.getSelectorsSrc().size();

        String destName = gappSetVector.getDestination().getName();
        createVector(destName, size);
        MultivectorWithValues destination = getMultivector(destName);
        MultivectorWithValues source = getMultivector(gappSetVector.getSource().getName());
        Selectorset selSrc = gappSetVector.getSelectorsSrc();


        destination.setEntries(new float[size]);
        for (int sel = 0; sel < size; sel++) {
            Selector sSrc = selSrc.get(sel);

            destination.setEntry(sel, sSrc.getSign() * source.getEntries()[sSrc.getIndex()]);
        }

        return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        MultivectorWithValues destination = getMultivector(gappAssignMv.getDestinationMv().getName());

        PosSelectorset selector = gappAssignMv.getSelectors();
        int selCount = selector.size();
        for (int sel = 0; sel < selCount; sel++) {
            GAPPValueHolder scalarVar = gappAssignMv.getValues().get(sel);
            float value = (scalarVar.isVariable())
                    ? getVariableValue(((GAPPVariable) scalarVar).getName())
                    : ((GAPPConstant) scalarVar).getValue();
            destination.getEntries()[selector.get(sel).getIndex()] = value;
        }

        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculate, Object arg) {

        MultivectorWithValues mv1 = getMultivector(gappCalculate.getOperand1().getName());
        float op1 = mv1.getEntry(0);

        MultivectorWithValues mv2 = null;
        float op2 = 0;
        if (gappCalculate.getOperand2() != null) {
            mv2 = getMultivector(gappCalculate.getOperand2().getName());
            op2 = mv2.getEntry(0);
        }

        MultivectorWithValues target = getMultivector(gappCalculate.getDestination().getName());

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
                result = (float) Math.pow(op1, op2);
                break;
            case FACT:
                result = 1;
                for (int i = 2; i <= (int) op1; i++) {
                    result *= i;
                }
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
                throw new UnsupportedOperationException("Executer: " + gappCalculate.getType() + " is not supported yet.");
        }

        target.setEntry(0, result);

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
        for (String m : keys) {
            System.out.println(m + " = " + Arrays.toString(values.get(m).getEntries()));
        }
    }

    @Override
    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg) {
        int size = gappAssignVector.getValues().size();
        String name = gappAssignVector.getDestination().getName();

        createVector(name, size);
        MultivectorWithValues destination = getMultivector(name);

        for (int sel = 0; sel < size; sel++) {
            GAPPValueHolder scalarVar = gappAssignVector.getValues().get(sel);
            float value = (scalarVar.isVariable())
                    ? getVariableValue(((GAPPVariable) scalarVar).getName())
                    : ((GAPPConstant) scalarVar).getValue();
            destination.getEntries()[sel] = value;
        }

        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateCoeff, Object arg) {

        MultivectorWithValues mv1 = getMultivector(gappCalculateCoeff.getOperand1().getName());
        float op1 = mv1.getEntry(0);

        MultivectorWithValues mv2 = null;
        float op2 = 0;
        if (gappCalculateCoeff.getOperand2() != null) {
            mv2 = getMultivector(gappCalculateCoeff.getOperand2().getName());
            op2 = mv2.getEntry(0);
        }

        MultivectorWithValues target = getMultivector(gappCalculateCoeff.getDestination().getName());

        float result;

        switch (gappCalculateCoeff.getType()) {
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
                result = (float) Math.pow(op1, op2);
                break;
            case FACT:
                result = 1;
                for (int i = 2; i <= (int) op1; i++) {
                    result *= i;
                }
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
                throw new UnsupportedOperationException("Executer: " + gappCalculateCoeff.getType() + " is not supported yet.");
        }

        target.setEntry(gappCalculateCoeff.getDestination().getBladeIndex(), result);

        return null;
    }
}
