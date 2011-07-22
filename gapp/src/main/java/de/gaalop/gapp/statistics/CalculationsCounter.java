/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.statistics;

import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculate;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.CFGGAPPVisitor;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Visitor who counts the number of instruction cycles and the number of calculations in a program
 * @author christian
 */
public class CalculationsCounter extends CFGGAPPVisitor {

    private long instructionCycleCount = 0;

    private HashMap<CalculationType,Long> calcCount = new HashMap<CalculationType, Long>();

    private HashMap<GAPPVector,Integer> vectorEntryCount = new HashMap<GAPPVector, Integer>();

    public long getInstructionCycleCount() {
        return instructionCycleCount;
    }

    public HashMap<CalculationType, Long> getCalcCount() {
        return calcCount;
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
       int countOfBlades = gappAddMv.getSelectorsSrc().size();
       instructionCycleCount += countOfBlades; // worst case
       return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
       int countOfBlades = gappAssignMv.getSelectors().size();
       instructionCycleCount += countOfBlades; // worst case
       return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        int countOfBlades = vectorEntryCount.get(gappDotVectors.getPart1());
        // from paper known: n + lg_2 (n)
        instructionCycleCount += countOfBlades + (int) Math.ceil(Math.log(countOfBlades)/Math.log(2));
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        instructionCycleCount += 1; //assumption that clearing can be made parallel
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
       int countOfBlades = gappSetMv.getSelectorsSrc().size();
       instructionCycleCount += countOfBlades; // worst case
       return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
       int countOfBlades = gappSetVector.getSelectorsSrc().size();
       vectorEntryCount.put(gappSetVector.getDestination(), countOfBlades);
       instructionCycleCount += countOfBlades; // worst case
       return null;
    }

    @Override
    public Object visitCalculate(GAPPCalculate gappCalculate, Object arg) {
        long count = (calcCount.containsKey(gappCalculate.getType())) ? calcCount.get(gappCalculate.getType()) : 0;

        count += (long) Math.ceil(gappCalculate.getUsed1().size()/4.0); // assuming doing 4 calculations in parallel

        calcCount.put(gappCalculate.getType(),count);
        return null;
    }

    public void printStatistics(OutputStream outputStream) {
        PrintStream out = new PrintStream(outputStream);

        out.println("#Cycles: "+instructionCycleCount);
        long sum = 0;
        for (CalculationType type: calcCount.keySet()) {
            long num = calcCount.get(type);
            out.println(type.toString()+": "+num);
            sum += num;
        }
        out.println("#Cycles with calculations: "+(instructionCycleCount+sum));

    }

}
