package datapath.graph.display.dot;

import datapath.graph.display.Display;
import datapath.graph.operations.Operation;
import datapath.graph.type.FixedPoint;
import datapath.graph.type.Type;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jh
 */
public class DotDisplay extends Display<DisplayEdge, DisplayNode> {

    public DotDisplay(String name) {
        super(name);
        this.nodes = new HashSet<DisplayNode>();
        this.edges = new HashSet<DisplayEdge>();
    }
    private HashSet<DisplayNode> nodes;
    private HashSet<DisplayEdge> edges;

    @Override
    public void addNode(DisplayNode node) {
        nodes.add(node);
    }

    @Override
    public void addEdge(DisplayEdge edge) {
        edges.add(edge);
    }

    @Override
    public void display(String stage) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("junk_"+stage+".dot"));
            out.write("digraph {\n");
            for(DisplayNode node : nodes){
              Type t = node.getOperation().getType();
              if (t == null)
                t = new FixedPoint(0, 0, false);
              String n = String.format("%s [label=\"%s %s Bits: %s Schedule: %d Cycles: %d\\nType: %s\\ndebug: %s\"];\n", node.getID(), node.getDisplayLabel(), node.getID(),
                      t.getBitsize(), node.getOperation().getSchedule(), node.getOperation().getDelay(), t.toString().replace("\n", "\\n"), node.getOperation().getDebugMessage());
              out.write(n);
            }
            for(DisplayEdge edge : edges){
                String n = String.format("%s -> %s;\n", edge.getSource().getID(),edge.getTarget().getID());
                out.write(n);
            }

//            for(HashSet<DisplayNode> set : getOperationScheduled().values()){
//                out.write("{rank = same;");
//                for(DisplayNode node : set ){
//                    out.write(node.getID()+";");
//                }
//                out.write("}\n");
//            }


            out.write("}\n");
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(DotDisplay.class.getName()).log(Level.SEVERE, null,
                    ex);
        }


    }

    @Override
    public void display() {
        display("");
    }

    private HashMap<Integer,HashSet<DisplayNode>> getOperationScheduled() {
      HashMap<Integer, HashSet<DisplayNode>> map = new HashMap<Integer, HashSet<DisplayNode>>();
      for(DisplayNode n : nodes) {
          Operation op = n.getOperation();
          HashSet<DisplayNode> set = map.get(op.getSchedule());
          if(set == null) {
              set = new HashSet<DisplayNode>();
              map.put(op.getSchedule(), set);
          }
          set.add(n);
      }
      return map;
  }
}
