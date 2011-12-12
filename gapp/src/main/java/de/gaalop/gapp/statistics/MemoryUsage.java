package de.gaalop.gapp.statistics;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSetOfVariables;
import de.gaalop.gapp.visitor.CFGGAPPVisitor;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * GAPP Visitor which shows the maximum memory usage in program
 * @author Christian Steinmetz
 */
public class MemoryUsage extends CFGGAPPVisitor {

    private HashMap<String, LiveStatistics> liveStatistics = new HashMap<String, LiveStatistics>();
    private int curLine = 0;

    private MemoryUsage() { //Make usage of static method mandatory
    }

    /**
     * Prints the memory usage of a control flow graph
     * @param graph The graph
     */
    public static void printMemoryUsage(ControlFlowGraph graph) {
        MemoryUsage visitor = new MemoryUsage();
        graph.accept(visitor);

        System.out.println("#Instructions: " + visitor.curLine);
        System.out.println("#Multivectors: " + visitor.liveStatistics.size());

        long sum = 0;
        for (LiveStatistics live : visitor.liveStatistics.values()) {
            sum += live.getSize();
        }

        System.out.println("Sum of multivectors sizes (liveness start until end): " + sum);

        System.out.println("Maximum of used space: " + visitor.maximumUsedSpace());

    }

    /**
     * Returns the maximum used space
     * @return The maximum used space
     */
    private long maximumUsedSpace() {
        // Divide and Conquer algorithm start
        return maximumUsedSpaceDivAndConq(1, curLine, liveStatistics.values());
    }

    /**
     * Returns the maximum used space of an interval
     * @param lineStart The interval start
     * @param lineEnd The interval end
     * @param list The list of LiveStatistics that overlap with the interval
     * @return The maximum used space of the interval
     */
    private long maximumUsedSpaceDivAndConq(int lineStart, int lineEnd, Collection<LiveStatistics> list) {
        if (list.isEmpty()) {
            return 0;
        }

        if (lineEnd - lineStart == 0) {
            //simple case
            long sum = 0;
            for (LiveStatistics stat : list) {
                sum += stat.getSize();
            }
            return sum;
        } else {
            //difficult case: split to simpler cases
            //create the separated lists
            int centerlastIntLeft = (lineStart + lineEnd) / 2;

            LinkedList<LiveStatistics> leftList = new LinkedList<LiveStatistics>();
            LinkedList<LiveStatistics> rightList = new LinkedList<LiveStatistics>();

            for (LiveStatistics element : list) {
                Interval interval = element.getInterval();
                int from = interval.getFrom();
                int to = interval.getTo();

                if (from <= centerlastIntLeft) {
                    leftList.add(element);
                    if (to > centerlastIntLeft) {
                        rightList.add(element);
                    }
                } else {
                    rightList.add(element);
                }
            }
            // recursion
            long leftMax = maximumUsedSpaceDivAndConq(lineStart, centerlastIntLeft, leftList);
            long rightMax = maximumUsedSpaceDivAndConq(centerlastIntLeft + 1, lineEnd, rightList);

            // merge
            return Math.max(leftMax, rightMax);
        }
    }

    /**
     * This method is called, if a GAPPSetOfVariables instance is accessed
     * @param gappSetOfVariables The GAPPSetOfVariables instance
     */
    private void access(GAPPSetOfVariables gappSetOfVariables) {
        if (!liveStatistics.containsKey(gappSetOfVariables.getName())) {
            System.err.println("Multivector " + gappSetOfVariables.getName() + " was not reseted!");
        } else {
            liveStatistics.get(gappSetOfVariables.getName()).getInterval().setTo(curLine);
        }
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        curLine++;
        access(gappAssignMv.getDestinationMv());
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        curLine++;
        access(gappDotVectors.getDestination());
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        curLine++;
        liveStatistics.put(gappResetMv.getDestinationMv().getName(),
                new LiveStatistics(curLine, gappResetMv.getSize()));
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        curLine++;
        access(gappSetMv.getDestination());
        access(gappSetMv.getSource());
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        curLine++;

        for (SetVectorArgument curArg: gappSetVector.getEntries())
            if (!curArg.isConstant()) {
                PairSetOfVariablesAndIndices p = (PairSetOfVariablesAndIndices) curArg;
                access(p.getSetOfVariable());
            }
            
        
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        curLine++;
        access(gappCalculateMv.getDestination());
        access(gappCalculateMv.getOperand1());
        if (gappCalculateMv.getOperand2() != null) {
            access(gappCalculateMv.getOperand2());
        }
        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        curLine++;
        access(new GAPPMultivector(gappCalculateMvCoeff.getDestination().getName()));
        access(gappCalculateMvCoeff.getOperand1());
        if (gappCalculateMvCoeff.getOperand2() != null) {
            access(gappCalculateMvCoeff.getOperand2());
        }
        return null;
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gAPPAssignInputsVector, Object arg) {
        curLine++;
        access(new GAPPMultivector("inputsVector"));
        return null;
    }
}
