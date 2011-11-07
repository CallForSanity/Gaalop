package de.gaalop.gapp.importing.optimization;

import de.gaalop.cfg.EndNode;
import de.gaalop.gapp.visitor.EmptyCFGGAPPVisitor;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Optimizes the GAPP Code by
 * merging setMv instructions with equal multivector name, if possible.
 * 
 * @author Christian Steinmetz
 */
public class GAPPSetMvMerger extends EmptyCFGGAPPVisitor {

    private HashSet<GAPPSetMv> toRemove = new HashSet<GAPPSetMv>();

    public HashSet<GAPPSetMv> getToRemove() {
        return toRemove;
    }
    private String curDestination;
    private HashMap<String, LinkedList<GAPPSetMv>> mapSetMv = new HashMap<String, LinkedList<GAPPSetMv>>();

    @Override
    public void visit(AssignmentNode node) {
        String varName = node.getVariable().getName();
        if (curDestination != null) {
            if (!curDestination.equals(varName)) {
                prepareMap();
                mapSetMv.clear();
            }
        }

        curDestination = varName;

        super.visit(node);
    }

    @Override
    public void visit(EndNode node) {
        if (!mapSetMv.isEmpty()) {
            prepareMap();
            mapSetMv.clear();
        }
        super.visit(node);
    }

    /**
     * Merges mergable GAPPSetMv to the first GAPPSetMv in mapSetMv
     */
    private void prepareMap() {
        for (LinkedList<GAPPSetMv> list : mapSetMv.values()) {
            if (list.size() > 1) {
                //merge setMv instructions into the first GAPPSetMv instruction
                GAPPSetMv first = list.removeFirst();
                for (GAPPSetMv cur : list) {
                    first.getSelectorsDest().addAll(cur.getSelectorsDest());
                    first.getSelectorsSrc().addAll(cur.getSelectorsSrc());
                }

                //and remove other instructions
                toRemove.addAll(list);
            }
        }
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        if (gappSetMv.getDestination().getName().equals(curDestination)) {
            String src = gappSetMv.getSource().getName();
            if (!mapSetMv.containsKey(src)) {
                mapSetMv.put(src, new LinkedList<GAPPSetMv>());
            }

            mapSetMv.get(src).add(gappSetMv);
        }

        return super.visitSetMv(gappSetMv, arg);
    }
}
