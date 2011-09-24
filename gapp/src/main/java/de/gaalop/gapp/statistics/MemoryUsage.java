package de.gaalop.gapp.statistics;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
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

    private MemoryUsage() {
    }

    public static void printMemoryUsage(ControlFlowGraph graph) {
        MemoryUsage visitor = new MemoryUsage();
        graph.accept(visitor);

        System.out.println("#Instructions: "+visitor.curLine);
        System.out.println("#Multivectors: "+visitor.liveStatistics.size());

        long sum = 0;
        for (LiveStatistics live: visitor.liveStatistics.values())
            sum += live.getSize();

        System.out.println("Sum of multivectors sizes (liveness start until end): "+sum);

        System.out.println("Maximum of used space: "+visitor.maximumUsedSpace());

    }

    private long maximumUsedSpace() {
        return maximumUsedSpaceDivAndConq(1, curLine, liveStatistics.values());
    }

    private long maximumUsedSpaceDivAndConq(int lineStart, int lineEnd, Collection<LiveStatistics> list) {
        if (list.isEmpty()) return 0;

        if (lineEnd-lineStart == 0) {
            long sum = 0;
            for (LiveStatistics stat: list)
                sum += stat.getSize();
            return sum;
        } else {
            int centerlastIntLeft = (lineStart+lineEnd)/2;

            LinkedList<LiveStatistics> leftList = new LinkedList<LiveStatistics>();
            LinkedList<LiveStatistics> rightList = new LinkedList<LiveStatistics>();

            for (LiveStatistics element: list) {
                Interval interval = element.getInterval();
                int from = interval.getFrom();
                int to = interval.getTo();

                if (from <= centerlastIntLeft) {
                    leftList.add(element);
                    if (to > centerlastIntLeft)
                        rightList.add(element);
                } else
                    rightList.add(element);
            }

            long leftMax = maximumUsedSpaceDivAndConq(lineStart, centerlastIntLeft, leftList);
            long rightMax = maximumUsedSpaceDivAndConq(centerlastIntLeft+1, lineEnd, rightList);

            return Math.max(leftMax,rightMax);
        }
    }

    private void access(GAPPMultivector multivector) {
        if (!liveStatistics.containsKey(multivector.getName())) {
            System.err.println("Multivector "+multivector.getName()+" was not reseted!");
        } else 
            liveStatistics.get(multivector.getName()).getInterval().setTo(curLine);
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        curLine++;
        access(gappAddMv.getDestinationMv());
        access(gappAddMv.getSourceMv());
        return null;
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
        access(gappSetMv.getDestinationMv());
        access(gappSetMv.getSourceMv());
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        curLine++;
        access(gappSetVector.getSourceMv());
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        curLine++;
        access(gappCalculateMv.getTarget());
        access(gappCalculateMv.getOperand1());
        if (gappCalculateMv.getOperand2() != null)
            access(gappCalculateMv.getOperand2());
        return null;
    }

}
