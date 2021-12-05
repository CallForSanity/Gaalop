package datapath.graph;

import datapath.graph.modlib.Module;
import datapath.graph.modlib.Wire;
import datapath.graph.modlib.WireIO;
import datapath.graph.operations.Operation;
import datapath.graph.operations.ParentInput;
import datapath.graph.operations.ParentOutput;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jh
 */
public class TopLevelDummyCreator {

    private Writer writer;
    private Graph graph;

    private TopLevelDummyCreator(Graph g, Writer w) {
        writer = w;
        graph = g;
    }

    public static void create(Graph g, Writer w) {
        TopLevelDummyCreator c = new TopLevelDummyCreator(g, w);
        c.create();
    }

    private int maxInput() {
        int max = 0;
        for(ParentInput input : graph.getInput()) {
            max = Math.max(max, input.getOutputBitsize());
        }
        return max;
    }

    private int maxOutput() {
        int max = 0;
        for(ParentOutput output : graph.getOutput()) {
            max = Math.max(max, output.getOutputBitsize());
        }
        return max;
    }

    private void create() {
        Wire in = new Wire("in");
        Wire select = new Wire("select");
        Wire out = new Wire("out");
        Wire CLK = new Wire("CLK");
        Wire RESET = new Wire("RESET");
        Wire CE = new Wire("CE");
        Module m = new Module("graph"+graph.getId(), graph.getId());
        in.setSize(maxInput());
        out.setSize(maxOutput());
        select.setSize(10);
        writeLine("module topLevelDummy(");
        writeLine(String.format("input wire %s,", CLK.withSize()));
        writeLine(String.format("input wire %s,", CE.withSize()));
        writeLine(String.format("input wire %s,", RESET.withSize()));
        writeLine(String.format("output reg %s,", out.withSize()));
        writeLine(String.format("input wire %s, input wire %s);",in.withSize(),select.withSize()));
        HashSet<Operation> ops = new HashSet<Operation>();
        ops.addAll(graph.getInput());
        ops.addAll(graph.getOutput());
        for(ParentInput op : graph.getInput()) {
            writeLine(String.format("reg [%d:0] reg%d;",op.getOutputBitsize()-1,op.getNumber()));
        }
        for(ParentOutput op : graph.getOutput())
            writeLine(String.format("wire [%d:0] reg%d;",op.getOutputBitsize()-1,op.getNumber()));
        writeLine(String.format("always @(posedge %s) begin",CLK.toString()));
        writeLine(String.format("case(%s)", select));
        int i = 0;
        for(ParentInput input : graph.getInput()) {
            m.addIO(new WireIO(new Wire("reg"+input.getNumber()), "dw_op"+input.getSource().getNumber()));
            writeLine(String.format("%d: reg%d = in;",i,input.getNumber()));
            i++;
        }
        writeLine("endcase");
        writeLine(String.format("case(%s)", select));
        i = 0;
        for(ParentOutput output : graph.getOutput()) {
            m.addIO(new WireIO(new Wire("reg"+output.getNumber()), "dw_op"+output.getNumber()));
            writeLine(String.format("%d: out = reg%d;",i,output.getNumber()));
            i++;
        }
        writeLine("endcase");
        writeLine("end");

        m.addIO(new WireIO(CLK, "CLK"));
        m.addIO(new WireIO(new Wire("1'b1"), "INIT"));
        m.addIO(new WireIO(RESET, "RESET"));
        m.addIO(new WireIO(CE, "CE"));

        writeLine(Modlib.module(m));


        writeLine("endmodule");
        try {
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(TopLevelDummyCreator.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    private void writeLine(String line) {
        try {
            writer.write(line);
            writer.write("\n");
        } catch (IOException ex) {
            System.err.println("Cannot write testbench");
        }
    }
}
