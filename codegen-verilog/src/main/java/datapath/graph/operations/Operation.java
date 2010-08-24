package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import datapath.graph.type.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author jh
 */
public abstract class Operation {

    private Set<Operation> usage;
    private Set<Predicate> predicates;
    private static int lastOp = 0;
    private int number;
    private int executionOrdinal;
    private int schedule;
    private boolean executionOrdinalSet;
    protected String debugMessage;
    private int visit;
    private static int nextVisit;
    private Type type;

    protected Operation() {
        usage = new HashSet<Operation>();
        predicates = new HashSet<Predicate>();
        number = lastOp++;
        debugMessage = "";
        executionOrdinalSet = false;
        schedule = -1;
    }

    public boolean isHardwareOperation() {
        return true;
    }

    public static void nextVisit() {
        nextVisit++;
    }

    public void setVisited() {
        visit = nextVisit;
    }

    public boolean isVisited() {
        return visit == nextVisit;
    }

    public abstract void replace(Operation oldOp, Operation newOp);

    /**
     * Adds a use of this operation by the given operation.
     * @param op
     */
    public void addUse(Operation op) {
        usage.add(op);
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    /**
     * Returns the type of the output data of this operation
     * @return
     */
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getExecutionOrdinal() {
        //assert executionOrdinalSet : "execution ordinal not set"+this;
        return executionOrdinal;
    }

    public void setExecutionOrdinal(int executionOrdinal) {
        this.executionOrdinal = executionOrdinal;
        executionOrdinalSet = true;
    }

    public void addPredicate(Predicate pred) {
        predicates.add(pred);
        pred.addUse(this);
    }

    public void removeUse(Operation op) {
        //assert usage.contains(op);
        usage.remove(op);
    }

    public String getDisplayName() {
        return "OP" + number + ":" +
                (executionOrdinalSet ? executionOrdinal : "-1") + ":" +
                getOutputBitsize() + "bit" + ":" + schedule;
    }

    public int getNumber() {
        return number;
    }

    public String getDisplayLabel() {
        return this.getClass().getName();
    }

    @Override
    public String toString() {
        return "OP" + number + " " + getDisplayLabel();
    }


    public Set<Operation> getUse() {
        return Collections.unmodifiableSet(usage);
    }

    @Deprecated
    public Set<Operation> getUse(boolean skipControl) {
        Set<Operation> use = new HashSet<Operation>();
        for (Operation op : usage) {
            if (!skipControl || op.isHardwareOperation()) {
                use.add(op);
            } else {
                use.addAll(op.getUse(skipControl));
            }
        }
        return use;
    }

    public Set<Predicate> getPredicates() {
        return predicates;
    }

    public void setDebugMessage(String message) {
        debugMessage = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    public void postOrderUpwardVisit(OperationVisitor visitor) {
        this.setVisited();
        for (Operation op : dependsOnOperations(true)) {
          if (!op.isVisited()) {
            op.postOrderUpwardVisit(visitor);
          }
        }
        this.visit(visitor);
    }

    public void postOrderDownwardVisit(OperationVisitor visitor) {
        this.setVisited();
        for (Operation op : getUse()) {
          if (!op.isVisited()) {
            op.postOrderUpwardVisit(visitor);
          }
        }
        this.visit(visitor);
    }

    public boolean isSigned() {
        if(type == null)
            return false;
        return type.isSigned();
    }

    /**
     * Returns <code>true</code> if the operation has a fixed delay.
     * @return
     */
    public boolean isFixedDelay() {
        return true;
    }

    public int getDelay() {
        return 0;
    }

    /**
     * Returns 1 if the output is buffered, 0 if the output is used combinatorical.
     * @return
     */
    public int getStageDelay() {
        return 1;
    }

    public int getOutputBitsize() {
        if(type == null)
            return 0;
        return type.getBitsize();
    }

    public abstract Set<Operation> dependsOnOperations(boolean includeBackedges);
}
