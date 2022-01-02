package datapath.graph;

import datapath.graph.display.Display;
import datapath.graph.display.DisplayEdge.Color;
import datapath.graph.display.DisplayEdge.Type;
import datapath.graph.display.DisplayFactory;
import datapath.graph.operations.Operation;
import datapath.graph.operations.ParentInput;
import datapath.graph.operations.ParentOutput;
import datapath.graph.operations.Predicate;
import datapath.graph.operations.UnaryOperation;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jh
 */
public class Graph {

    private Set<Operation> operations;
    private Set<Graph> innerLoops;
    private static int lastId;
    private int id;
    private int level;

    public Graph() {
        this(-1);
    }

    public Graph(int level) {
        operations = new HashSet<Operation>();
        innerLoops = new HashSet<Graph>();
        id = lastId++;
        this.level = level;
    }

    public void addOperation(Operation op) {
        operations.add(op);
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public int getId() {
        return id;
    }

    public Set<ParentInput> getInput() {
        HashSet<ParentInput> input = new HashSet<ParentInput>();
        for (Operation op : operations) {
            if (op instanceof ParentInput) {
                input.add((ParentInput) op);
            }
        }
        return input;
    }

    public Set<ParentOutput> getOutput() {
        HashSet<ParentOutput> output = new HashSet<ParentOutput>();
        for (Operation op : operations) {
            if (op instanceof ParentOutput) {
                output.add((ParentOutput) op);
            }
        }
        return output;
    }

    public static boolean isBackEdge(Operation source, Operation target) {
        return source.getExecutionOrdinal() > target.getExecutionOrdinal();
    }

    public static int getDistance(Operation source, Operation target) {
        return Math.abs(target.getSchedule() - source.getSchedule() -
                source.getDelay());
    }

    public void displayAll(DisplayFactory factory, String stage) {
        for (Graph graph : innerLoops) {
            graph.displayAll(factory);
        }
        display(factory);
    }

    public void displayAll(DisplayFactory factory) {
        displayAll(factory, "");
    }

    public void display(DisplayFactory factory, String stage) {
        Display disp = factory.display("Datapath Graph " + this.toString());

        // display.newGraph(context, false);
        for (Operation op : operations) {
            disp.addNode(factory.displayNode(op));
        }

        for (Operation op : operations) {
            if(op.getUse().size() == 0) {
                System.out.println("warning: op has no use "+op);
            }
            for (Operation target : op.getUse()) {
                if (isBackEdge(op, target)) {
                    disp.addEdge(factory.displayEdge(op, target, Type.Solid,
                            Color.RED));
                } else {
                    disp.addEdge(factory.displayEdge(op, target, Type.Solid,
                            Color.BLACK));
                }
            }
            for (Predicate pred : op.getPredicates()) {
                disp.addEdge(factory.displayEdge(pred, op, Type.Dashed,
                        pred.getEdgeColor()));
            }
        }
        disp.display(stage);
    }

    public void display(DisplayFactory factory, ParentOutput output) {
        Display disp = factory.display("Datapath Output " +
                output.getDisplayLabel());

        HashSet<Operation> ops = new HashSet<Operation>();

        String name = output.getName();
        ops.add(output);
        ops.addAll(output.dependsOnOperations(true));

        boolean changed;
        do {
            changed = false;
            for (Operation op : ops.toArray(new Operation[0])) {
                if(!op.getDebugMessage().equals("") && !op.getDebugMessage().equals(name))
                    continue;
                changed |= ops.addAll(op.dependsOnOperations(true));
            }
        } while (changed);

        for(Operation op : ops) {
            disp.addNode(factory.displayNode(op));
        }

        for (Operation op : ops) {
            for (Operation target : op.getUse()) {
                if(!ops.contains(target))
                    continue;
                if (isBackEdge(op, target)) {
                    disp.addEdge(factory.displayEdge(op, target, Type.Solid,
                            Color.RED));
                } else {
                    disp.addEdge(factory.displayEdge(op, target, Type.Solid,
                            Color.BLACK));
                }
            }
//            for (Predicate pred : op.getPredicates()) {
//                disp.addEdge(factory.displayEdge(pred, op, Type.Dashed,
//                        pred.getEdgeColor()));
//            }
        }
        disp.display(output.getName());
    }

    public void display(DisplayFactory factory) {
        display(factory, "");
    }

    public void remove(Operation op) {
        assert operations.contains(op);
        operations.remove(op);
    }

    public void addInnerLoop(Graph g) {
        innerLoops.add(g);
    }

    public Set<Graph> getInnerLoops() {
        return innerLoops;
    }

    public int numOfOperation(boolean includeInnerLoops,
            Class<? extends Operation> type) {
        int numOperation = 0;
        if (includeInnerLoops) {
            for (Graph innerLoop : innerLoops) {
                numOperation += innerLoop.numOfOperation(includeInnerLoops, type);
            }
        }
        for (Operation op : operations) {
            if (op.getClass() == type) {
                numOperation++;
            }
        }
        return numOperation;
    }

    @Override
    public String toString() {
        return String.format("Graph%d level:%d", id, level);
    }

    public boolean isTopLevel() {
        return level == 0;
    }

    public int getLatestSchedule() {
        int lastOp = 0;
        for (Operation op : operations) {
            lastOp = Math.max(lastOp, op.getSchedule());
        }
        return lastOp;
    }

    /**
     * Inserts an operation (node) between two other.
     * @param toInsert Operation to insert. Must be a UnaryOperation (one input, one output).
     * @param pred After which node toInsert should be placed.
     * @param succ Before which node toInsert should be place.
     */
    public void insertNode(UnaryOperation toInsert, Operation pred,
            Operation succ) {
        //toInsert.addUse(succ);
        toInsert.setData(pred);

        addOperation(toInsert);
        succ.replace(pred, toInsert);
        //pred.removeUse(succ);
        //pred.addUse(toInsert);
    }
}
