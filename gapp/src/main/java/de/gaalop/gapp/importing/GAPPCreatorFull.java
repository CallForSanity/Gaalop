package de.gaalop.gapp.importing;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectType;
import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPCreatorFull extends GAPPBaseCreator {

    public GAPPCreatorFull(GAPP gapp, int bladeCount) {
        super(gapp, bladeCount);
    }

    private int curTmpIndex = 0;

    private String createTMP() {
        curTmpIndex++;
        return "xTmp"+curTmpIndex;
    }

    @Override
    public void createGAPPInstructionsFromAssignment(Assignment assignment) {

        HashMap<SignedSummand, Scalarproduct> summands = assignment.getSummands();
        int maxNumber = 0;
        for (SignedSummand summand: summands.keySet()) {
            Scalarproduct scalarproduct = summands.get(summand);
            maxNumber = Math.max(maxNumber, scalarproduct.getObjects().size());
        }

        ParallelVector[] vectors = new ParallelVector[maxNumber];
        for (int i=0;i<vectors.length;i++)
            vectors[i] = new ParallelVector();

        
        for (SignedSummand summand: summands.keySet()) {
            int v = 0;
            Scalarproduct scalarproduct = summands.get(summand);
            for (ParallelObject object: scalarproduct.getObjects()) {
                if (!summand.isPositiveSigned() && v == 0) {
                    object.setNegatedInSum(true);
                }
                vectors[v].getSlots().add(object);
                v++;
            }


            while (v < maxNumber) {
                vectors[v].getSlots().add(new Constant(1));
                v++;
            }
        }

        LinkedList<GAPPVector> parts = new LinkedList<GAPPVector>();

        // vectors to GAPP
        for (ParallelVector vector: vectors) 
            parts.add(createVector(vector));


        GAPPDotVectors dotVectors = new GAPPDotVectors(new GAPPMultivector(assignment.getName(), null), new Selector(assignment.getIndex(),(byte) 1), parts);
        gapp.addInstruction(dotVectors);
        
    }

    private GAPPVector createVector(ParallelVector vector) {

        // TODO assumption: restricted to bladeCount <= vector.size

        // Parallelvector to Multivector
        GAPPMultivector mvTmp = new GAPPMultivector(createTMP(), new GAPPValueHolder[vector.getSlots().size()]);
        int j = 0;
        for (ParallelObject obj: vector.getSlots()) {
            Selector sel = new Selector(j,(byte) (obj.isNegatedInSum() ? -1 : 1));
            Selectorset selSet = new Selectorset();
            selSet.add(sel);

            switch (ParallelObjectType.getType(obj)) {
                case constant:
                    Variableset varSet = new Variableset();
                    varSet.add(new GAPPConstant(((Constant) obj).getValue()));
                    gapp.addInstruction(new GAPPAssignMv(mvTmp, selSet, varSet));
                    break;
                case extCalculation:
                    ExtCalculation extCalc = (ExtCalculation) obj;

                    GAPPMultivector target = obj.isNegatedInSum()
                            ? new GAPPMultivector(createTMP(), null)
                            : mvTmp;

                    GAPPMultivector mvC1 = getGAPPMultivector(extCalc.getOperand1());

                    GAPPMultivector mvC2 = (extCalc.getOperand2() != null)
                            ? getGAPPMultivector(extCalc.getOperand2())
                            : null;

                    gapp.addInstruction(new GAPPCalculateMv(extCalc.getType(), target, mvC1, mvC2));

                    if (obj.isNegatedInSum())
                        gapp.addInstruction(new GAPPSetMv(mvTmp, target, selSet, selSet));

                    //TODO ? maybe
                    break;
                case factors:
                   throw new IllegalStateException("Factors should have been removed");
                case mvComponent:
                    Selectorset selSrc = new Selectorset();
                    MvComponent mvc = (MvComponent) obj;

                    selSrc.add(new Selector(mvc.getMultivectorComponent().getBladeIndex(), (byte) 1));
                    GAPPMultivector mvSrc = new GAPPMultivector(mvc.getMultivectorComponent().getName(), null);

                    gapp.addInstruction(new GAPPSetMv(mvTmp, mvSrc, selSet, selSrc));
                    break;
                case summands:
                    throw new IllegalStateException("Summands should have been removed");
            }
            

            //gapp.addInstruction(new GAPPSetMv(mvTmp, src, new Selector(j,(byte) 1), srcSel));
            j++;
        }


        // Multivector to vector
        GAPPVector result = new GAPPVector(createTMP(), new LinkedList<GAPPValueHolder>());

        Selectorset sels = new Selectorset();
        for (int i=0;i<vector.getSlots().size();i++)
            sels.add(new Selector(i, (byte) 1));
        GAPPSetVector setVector = new GAPPSetVector(result, mvTmp, sels);
        gapp.addInstruction(setVector);
        return result;
    }

    private GAPPMultivector getGAPPMultivector(ParallelObject obj) {
        switch (ParallelObjectType.getType(obj)) {
                case constant:
                    Variableset varSet = new Variableset();
                    varSet.add(new GAPPConstant(((Constant) obj).getValue()));
                    GAPPMultivector mvTmp = new GAPPMultivector(createTMP(), null);
                    Selectorset selSet = new Selectorset();
                    selSet.add(new Selector(0, (byte) 1));
                    gapp.addInstruction(new GAPPAssignMv(mvTmp, selSet, varSet));
                    return mvTmp;
                case extCalculation:
                    ExtCalculation extCalc = (ExtCalculation) obj;

                    GAPPMultivector target = new GAPPMultivector(createTMP(), null);

                    GAPPMultivector mvC1 = getGAPPMultivector(extCalc.getOperand1());

                    GAPPMultivector mvC2 = (extCalc.getOperand2() != null)
                            ? getGAPPMultivector(extCalc.getOperand2())
                            : null;

                    gapp.addInstruction(new GAPPCalculateMv(extCalc.getType(), target, mvC1, mvC2));
                    return target;
                case factors:
                   throw new IllegalStateException("Factors should have been removed");
                case mvComponent:
                    Selectorset selSrc = new Selectorset();
                    MvComponent mvc = (MvComponent) obj;

                    selSrc.add(new Selector(mvc.getMultivectorComponent().getBladeIndex(), (byte) 1));
                    return new GAPPMultivector(mvc.getMultivectorComponent().getName(), null);
                case summands:
                    throw new IllegalStateException("Summands should have been removed");
            }
        return null;
    }

}
