package datapath.graph;

import datapath.graph.modlib.Module;
import datapath.graph.modlib.Wire;
import datapath.graph.modlib.WireIO;
import datapath.graph.operations.ParentInput;
import datapath.graph.operations.ParentOutput;
import datapath.graph.type.FixedPoint;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeSet;
/**
 * Class to create a (very simple) testbench for one datapath.
 * @author jh
 */
public class TestbenchCreator {

    private Graph graph;
    private Writer writer;

    public static boolean fixpoint = true;

    private TestbenchCreator(Graph graph, Writer writer) {
        this.graph = graph;
        this.writer = writer;
    }

    public static void writeTestbench(Graph graph, Writer writer) throws IOException {
        TestbenchCreator creator = new TestbenchCreator(graph, writer);
        creator.create();
        writer.close();
    }

    private void create() {
        HashMap<Wire, String> ios = new HashMap();
        HashSet<Wire> in = new HashSet<Wire>();
        HashSet<Wire> regs = new HashSet<Wire>();
        HashSet<Wire> wires = new HashSet<Wire>();
        writeLine("`timescale 1ns / 1ns");
        writeLine("module testbench();");
        regs.add(new Wire("RESULT_ACCEPT"));
        regs.add(new Wire("CANCEL"));
        regs.add(new Wire("START"));
        regs.add(new Wire("START_CTRL"));
        regs.add(new Wire("CLK"));
        regs.add(new Wire("RESET"));
        regs.add(new Wire("INIT"));
        wires.add(new Wire("END"));
        regs.add(new Wire("CE"));
        TreeSet<ParentInput> input = new TreeSet<ParentInput>(new SortByNumber());
        input.addAll(graph.getInput());
        TreeSet<ParentOutput> output = new TreeSet<ParentOutput>(
                new SortByNumber());
        output.addAll(graph.getOutput());
        Module m = new Module("graph" + graph.getId(), "g", graph.getId());
        for (Wire reg : regs) {
            writeLine("reg " + reg.withSize() + ";");
            m.addIO(new WireIO(reg, reg.toString()));
        }
        for (Wire wire : wires) {
            writeLine("wire " + wire.withSize() + ";");
            m.addIO(new WireIO(wire, wire.toString()));
        }
        for (ParentInput inp : input) {
            Wire io = new Wire(inp.getName());
            in.add(io);
            io.setSize(inp.getOutputBitsize());
            ios.put(io, ModlibWriter.getDataWire(inp.getSource()));
            writeLine("reg " + io.withSize() + ";");
        }
        for (ParentOutput out : output) {
            Wire io = new Wire(out.getName());
            io.setSize(out.getOutputBitsize());
            ios.put(io, ModlibWriter.getDataWire(out));
            writeLine("wire " + io.withSize() + ";");
        }
        for (Entry<Wire, String> entry : ios.entrySet()) {
            m.addIO(new WireIO(entry.getKey(), entry.getValue()));
        }
        writeLine(Modlib.module(m));
        writeLine("integer file;");
        writeLine("integer error;");
        writeLine("initial begin");
        for (Wire reg : regs) {
            writeLine(reg.toString() + " = 0;");
        }
        writeLine("file = $fopen(\"inputdata\", \"r\");");
        writeLine("$ferror(file, error);");
        writeLine("if (error != 0) begin");
        writeLine("// input data begins here");
        for (Wire inp : in) {
            writeLine(String.format("%s = %d'd0;", inp.toString() ,inp.getSize()));
        }
        writeLine("// end of input data");
        writeLine("end");
        writeLine("else begin");
        writeLine("`include \"inputdata\"");
        writeLine("end");
        writeLine("#20 RESET=1;");
        writeLine("#10 RESET=0;");
        writeLine("#4");
        writeLine("INIT = 1;");
        writeLine("CE = 1;");
        writeLine("#5 INIT = 0;");
        if(fixpoint) {
            addFixpointOutput();
        }
        writeLine("end");
        writeLine("// clock with cycle length 10");
        writeLine("always begin");
        writeLine("#5 CLK=~CLK;");
        writeLine("end");
        writeLine("endmodule");
    }

    private void writeLine(String line) {
        try {
            writer.write(line);
            writer.write("\n");
        } catch (IOException ex) {
            System.err.println("Cannot write testbench");
        }
    }

    private void addFixpointOutput() {
        TreeSet<ParentOutput> output = new TreeSet<ParentOutput>(
                new SortByNumber());
        output.addAll(graph.getOutput());
        TreeSet<ParentInput> input = new TreeSet<ParentInput>(new SortByNumber());
        input.addAll(graph.getInput());
        writeLine("@(posedge END) begin");
        for(ParentInput in : input) {
            assert in.getType() instanceof FixedPoint;
            int frac = ((FixedPoint)in.getType()).getFractionlength();
            int bits = in.getType().getBitsize();
            writeLine(fixpoint(in.getName(), frac, bits,in.isSigned()));
        }
        for(ParentOutput out : output) {
            assert out.getType() instanceof FixedPoint;
            int frac = ((FixedPoint)out.getType()).getFractionlength();
            int bits = out.getType().getBitsize();
            writeLine(fixpoint(out.getName(), frac, bits,out.isSigned()));
        }
        writeLine("end");
    }

    private static String fixpoint(String name, int frac, int bits, boolean signed) {
        bits = bits -1;
        if(signed)
            return String.format("$display(\"%s: %%s %%f\", (%s[%d:%d] == 1 )? \"-\": \" \", $itor((%s[%d:%d] == 1)? -%s:%s) / $itor((%d'b1 << %d)));",name,name,bits,bits,name,bits,bits,name,name,bits+2,frac);
        else
            return String.format("$display(\"%s: %%f\", %s / $itor((%d'b1 << %d)));",name,name,bits+2,frac);
    }
}
