package datapath.graph;

import datapath.graph.modlib.Module;
import datapath.graph.modlib.Wire;
import datapath.graph.modlib.WireAnd;
import datapath.graph.modlib.WireConcat;
import datapath.graph.modlib.WireIO;
import datapath.graph.modlib.WireNot;
import datapath.graph.modlib.WireOR;
import datapath.graph.modlib.parameter.*;
import datapath.graph.operations.*;

import datapath.graph.type.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backend to generate the Verilog output for each {@link Graph}.
 * @author jh
 */
public class ModlibWriter implements OperationVisitor {


    public static boolean mul_pipe = true;
    public static boolean mul_pipe_create = true;

    private Graph graph;
    private BufferedWriter writer;
    private Wire CE = new Wire("CE");
    private Wire CLK = new Wire("CLK");
    private Wire RESET = new Wire("RESET");
    private Wire INIT = new Wire("INIT");
    private Wire END = new Wire("END");
    private HashSet<Module> modules;

    private ModlibWriter(Graph graph, BufferedWriter writer) {
        this.writer = writer;
        modules = new HashSet<Module>();
    }

    private WireIO getWireIO(Operation source, String port) {
        Wire w = getWire(source);
        return new WireIO(w, port);
    }
    HashMap<Operation, Wire> wireSources = new HashMap();

    private Wire getWire(Operation source) {
        Wire w = wireSources.get(source);
        if (w == null) {
            w = new Wire(getDataWire(source));
            w.setSize(source.getOutputBitsize());
            wireSources.put(source, w);
        }
        return w;
    }

    int combinedDepth;
    int highestDepth;

    private DEPTH getDepth(Operation op) {
        int depth = -1;
        for (Operation use : op.getUse()) {
            if (depth == -1) {
                depth = Graph.getDistance(op, use);
            }
            assert Graph.getDistance(op, use) == depth : "different depths -> possible scheduling error " +
                    op;
        }
        depth = Math.max(depth, 0); // for operations which have no use
        combinedDepth += depth;
        if(!(op instanceof LoopInit))
            highestDepth = Math.max(highestDepth, depth);
        return new DEPTH(depth);
    }

    /**
     * Returns the string representation of result wire for the given Operation.
     * @param source the operation for which the result wire is desired
     * @return the string representation of result wire for the given Operation
     */
    public static String getDataWire(Operation source) {
        return String.format("dw_op%d", source.getNumber());
    }

    private Wire getControlWireAnd(Set<Predicate> predicates) {
        ArrayList<Wire> wires = new ArrayList<Wire>();
        for (Predicate p : predicates) {
            wires.add(getControlWire(p));
        }
        return new WireAnd(wires.toArray(new Wire[0]));
    }

    private Wire getControlWireOR(Set<Predicate> predicates) {
        ArrayList<Wire> wires = new ArrayList<Wire>();
        for (Predicate p : predicates) {
            wires.add(getControlWire(p));
        }
        return new WireOR(wires.toArray(new Wire[0]));
    }

    private Wire getControlWire(Predicate pred) {
        assert pred.getData().getOutputBitsize() == 1;
        switch (pred.getPredicationType()) {
            case INIT:
            case TRUE:
                return getWire(pred.getData());
            case FALSE:
                return new WireNot(getWire(pred.getData()));
            default:
                throw new RuntimeException("don't know the predicate type");
        }
    }

    private Wire getPredicationWire(Predication pred) {
        ArrayList<Wire> w = new ArrayList<Wire>();
        for (Iterator<Predicate> iter = pred.getPredicates().iterator(); iter.hasNext();) {
            Predicate p = iter.next();
            w.add(getControlWire(p));
        }
        return new WireAnd(w.toArray(new Wire[0]));
    }

    private static void writeRecursive(Graph graph, BufferedWriter writer) throws IOException {
        for (Graph g : graph.getInnerLoops()) {
            writeRecursive(g, writer);
        }
        ModlibWriter w = new ModlibWriter(graph, writer);
        w.write("`timescale 1ns / 1ns\n");
        w.write(graph);
    }

    private void write(Graph graph) throws IOException {
        combinedDepth = 0;
        highestDepth = 0;
        mulpipes = new HashSet<MulPipeCreator.Options>();
        // generate all modules and wires
        for (Operation op : graph.getOperations()) {
            if (!op.isHardwareOperation()) {
                continue;
            }
            op.visit(this);
        }
        write("// new loop\n");
        write(String.format("// latest schedule is %d\n",
                graph.getLatestSchedule()));
        write(String.format("// combined depth: %d\n", combinedDepth));
        write(String.format("// highest depth: %d\n", highestDepth));
        write("module graph" + graph.getId() + "\n");
        write("(\n");
        // Control Wires
        write("input wire RESULT_ACCEPT,\n");
        write("input wire CANCEL,\n");
        write("input wire CANCEL_STATE_RESET,\n");
        write("input wire START,\n");
        write("input wire START_CTRL,\n");
        write("input wire " + CLK.withSize() + ",\n");
        write("input wire " + RESET.withSize() + ",\n");
        write("input wire " + INIT.withSize() + ",\n");
        write("output wire " + END.withSize() + ",\n");
        write("input wire " + CE.withSize());
        // data input of the graph
        for (ParentInput pin : graph.getInput()) {
            write(",\n");
            write("input wire " + getWire(pin.getSource()).withSize());
            wireSources.remove(pin.getSource());
        }
        // data output of the graph
        for (ParentOutput pout : graph.getOutput()) {
            write(",\n");
            write("output wire " + getWire(pout).withSize());
            wireSources.remove(pout);
        }
        write("\n);\n");
        for (Wire w : wireSources.values()) {
            writeWire(w);
            write("\n");
        }
        addCommonControl();
        for (Module m : modules) {
            write(Modlib.module(m));
            write("\n");
        }
        write("endmodule\n");
        if(mul_pipe_create && mul_pipe) {
            System.out.println("number of different mul_pipe:" + mulpipes.size());
            System.out.println(mulpipes);
            MulPipeCreator.c(mulpipes);
        }
    }

    /**
     * Writes the Verilog output of given graph and all its subgraphs to the 
     * given writer.
     * @param g the graph to write
     * @param writer the writer with which the graph is printed
     */
    public static void write(Graph g, BufferedWriter writer) {
        try {
            writeRecursive(g, writer);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ModlibWriter.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void visit(Operation op) {
        System.err.println(op.getClass() + " not supported");
    }

    @Override
    public void visit(BinaryOperation op) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Mux op) {
        Set<Operation> preds = op.getOperands();
        ArrayList<Wire> Aw = new ArrayList<Wire>();
        ArrayList<Wire> Bw = new ArrayList<Wire>();
        for (Iterator<Operation> iter = preds.iterator(); iter.hasNext();) {
            Predication pred = (Predication) iter.next();
            Aw.add(getWire(pred.getData()));
            Bw.add(getPredicationWire(pred));
        }
        Module m = new Module("mux", op.getNumber());
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new WA(op.getOutputBitsize()));
        m.addParameter(new SIGN(op.isSigned()));
        m.addParameter(getDepth(op));
        m.addParameter(new NIN(Aw.size()));
        m.addIO(getWireIO(op, "R"));
        m.addIO(new WireIO(new WireConcat(Aw.toArray(new Wire[0])), "A"));
        m.addIO(new WireIO(new WireConcat(Bw.toArray(new Wire[0])), "B"));
        m.addIO(new WireIO(new WireOR(Bw.toArray(new Wire[0])), "START"));
        modules.add(m);
    }

    @Override
    public void visit(ConstantOperation op) {
        Module m = new Module("const_op", op.getNumber());
        
        m.addParameter(new WR(op.getOutputBitsize()));
//        m.addParameter(new VALUE(op.getValue().replace("0x", op.getOutputBitsize() +
//                "'h").replace("U",
//                "")));
        m.addParameter(new VALUE(String.format("%d'h%s", op.getOutputBitsize(),
                op.toHex())));
        // the data path works without this
        // but it is inserted so that the output changes with the end signal
        DEPTH depth = getDepth(op);
        if(false && depth.getV() > 0) {
            Nop n = new Nop();
            n.setType(op.getType());
            Module nop = new Module("nop", n.getNumber());
            m.addIO(getWireIO(n, "R"));
            nop.addIO(getWireIO(n, "A"));
            nop.addIO(getWireIO(op, "R"));
            nop.addParameter(new WR(op.getOutputBitsize()));
            nop.addParameter(new WA(op.getOutputBitsize()));
            nop.addParameter(depth);
            modules.add(nop);
        // end
        } else {
        m.addIO(getWireIO(op, "R"));
        }
        modules.add(m);
    }

    @Override
    public void visit(Add op) {
        Type type = op.getType();
        if (type instanceof datapath.graph.type.Integer) {
            binaryOp("add", op);
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            binaryOp("add", op);
        } else if (type instanceof datapath.graph.type.Float) {
            binaryOp("addfloat", op);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private void writeWire(Wire w) {
        write("wire " + w.withSize() + ";");
    }

    private void write(String s) {
        try {
            writer.write(s);
        } catch (IOException ex) {
            Logger.getLogger(ModlibWriter.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    private void addCommonControl() {
        for (Module m : modules) {
            m.addParameter(new QDEPTH(0));
            m.addIO(new WireIO(CLK, "CLK"));
            m.addIO(new WireIO(CE, "CE"));
            m.addIO(new WireIO(new Wire("1'b1"), "START"));
            m.addIO(new WireIO(new Wire("1'b1"), "RESULT_ACCEPT"));
            m.addIO(new WireIO(new Wire("1'b0"), "CANCEL"));
            m.addIO(new WireIO(new Wire("1'b1"), "START_CTRL"));
            m.addIO(new WireIO(new Wire("1'b1"), "CANCEL_STATE_RESET"));
            m.addIO(new WireIO(new Wire("1'b1"), "CANCEL_STATE_CTRL_RESET"));
            m.addIO(new WireIO(RESET, "RESET"));
        }
    }

    @Override
    public void visit(Less op) {
        binaryOp("cmplt", op);
    }

    private void binaryOp(String name, BinaryOperation op) {
        Module m = new Module(name, op.getNumber());
        m.addParameter(new WA(op.getLhs().getOutputBitsize()));
        m.addParameter(new WB(op.getRhs().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new SIGN(op.isSigned()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op.getLhs(), "A"));
        m.addIO(getWireIO(op.getRhs(), "B"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    private void unaryOp(String name, UnaryOperation op) {
        Module m = new Module(name, op.getNumber());
        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new SIGN(op.isSigned()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(MemWrite op) {
        Module m = new Module("memwrite", op.getNumber());
        m.addParameter(new ADDR_WIDTH(op.getAddress().getOutputBitsize()));
        m.addParameter(new DATA_WIDTH(op.getData().getOutputBitsize()));

        m.addIO(getWireIO(op.getAddress(), "A"));
        m.addIO(getWireIO(op.getData(), "B"));

        Wire start = getControlWireAnd(op.getPredicates());
        m.addIO(new WireIO(start, "START"));

        modules.add(m);
    }

    @Override
    public void visit(FromOuterLoop op) {
        Module m = new Module("nop", "FromOuterLoop", op.getNumber());

        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new WA(op.getSource().getOutputBitsize()));
        m.addParameter(new DEPTH(0));

        m.addIO(getWireIO(op, "R"));
        m.addIO(getWireIO(op.getSource(), "A"));

        modules.add(m);
    }

    @Override
    public void visit(ToInnerLoop op) {
        Module m = new Module("nop", op.getNumber());

        m.addParameter(new WR(op.getOutputBitsize()));

        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(Loop op) {
        Module m = new Module("graph" + op.getGraph().getId(),
                op.getGraph().getId());

        for (ParentInput pin : op.getGraph().getInput()) {
            m.addIO(getWireIO(pin.getSource(), getDataWire(pin.getSource())));
        }
        for (ParentOutput pout : op.getGraph().getOutput()) {
            m.addIO(getWireIO(pout, getDataWire(pout)));
        }

        modules.add(m);
    }

    @Override
    public void visit(HWInput op) {
        Module m = new Module("inreg", op.getNumber());
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addIO(getWireIO(op, "R"));

        //modules.add(m);
    }

    @Override
    public void visit(VariableShift op) {
        String type;
        switch (op.getMode()) {
            case Left:
                type = "vsl";
                break;
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
        Module m = new Module(type, op.getNumber());
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new WA(op.getLhs().getOutputBitsize()));
        m.addParameter(new WB(op.getRhs().getOutputBitsize()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op.getLhs(), "A"));
        m.addIO(getWireIO(op.getRhs(), "B"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(ConstantShift op) {
        Module m;
        ShiftMode mode = op.getMode();
        if (mode == ShiftMode.ZeroShiftLeft) {
          op.setShiftAmount(0);
          op.setMode(ShiftMode.Left);
        }
        if (mode == ShiftMode.ZeroShiftRight) {
          op.setShiftAmount(0);
          op.setMode(ShiftMode.Right);
        }

        mode = op.getMode();
        
        switch (mode) {
            case Right:
                if(op.isSigned()){
                    mode = ShiftMode.SignedRight;
                } else
                    mode = ShiftMode.UnsignedRight;
        }
        switch (mode) {
            case Left:
                m = new Module("lsl", op.getNumber());
                m.addParameter(new WB(op.getShiftAmount()));
                break;
            case SignedRight:
                assert op.getShiftAmount() >= 0;
                m = new Module("vsr", op.getNumber());
                m.addParameter(new WB(8));
                m.addParameter(new SIGN(true));
                m.addIO(new WireIO(new Wire("8'd" + op.getShiftAmount()), "B"));
                break;
            case UnsignedRight:
                m = new Module("lsr", op.getNumber());
                m.addParameter(new WB(op.getShiftAmount()));
                break;
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }

        m.addParameter(new WA(op.getData().getOutputBitsize()));
        // WB is set above
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(Nop op) {
        Module m = new Module("nop", op.getNumber());

        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new DEPTH(Graph.getDistance(op,
                op.getUse().iterator().next())));

        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(ToOuterLoop op) {
        Module m = new Module("nop", "ToOuterLoop", op.getNumber());

        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new DEPTH(1));

        m.addIO(getWireIO(op, "R"));
        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(new WireIO(getControlWireOR(op.getPredicates()), "START"));

        modules.add(m);
    }

    @Override
    public void visit(LoopEnd op) {
        Module m = new Module("nop", "LoopEnd", op.getNumber());

        m.addParameter(new WA(1)); // control wire has only one bit
        m.addParameter(new WR(1));
        m.addParameter(new DEPTH(1));

        m.addIO(new WireIO(getControlWireAnd(op.getPredicates()), "A"));
        m.addIO(new WireIO(END, "R"));

        modules.add(m);
    }

    @Override
    public void visit(LoopInit op) {
        Module m = new Module("nop", "LoopInit", op.getNumber());

        m.addParameter(new WA(1));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op, "R"));
        m.addIO(new WireIO(INIT, "A"));

        modules.add(m);
    }

    @Override
    public void visit(HWOutput op) {
        Module m = new Module("nop", "HWOutput", op.getNumber());

        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new DEPTH(1));

        m.addIO(getWireIO(op, "R"));
        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(new WireIO(getControlWireOR(op.getPredicates()), "START"));

        modules.add(m);
    }

    @Override
    public void visit(TopLevelInput op) {
        Module m = new Module("nop", "TopLevelInput", op.getNumber());

        m.addParameter(new WA(op.getSource().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op, "R"));
        m.addIO(getWireIO(op.getSource(), "A"));

        modules.add(m);
    }

    @Override
    public void visit(Negation op) {
        Type type = op.getType();
        Module m;
        if (type instanceof datapath.graph.type.Integer) {
            m = new Module("sub", op.getNumber());
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            m = new Module("sub", op.getNumber());
        } else if (type instanceof datapath.graph.type.Float) {
            m = new Module("subfloat", op.getNumber());
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new WB(op.getData().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new SIGN(op.isSigned()));
        assert op.getUse().size() > 0 : op + " has no use";
        m.addParameter(new DEPTH(Graph.getDistance(op,
                op.getUse().iterator().next())));

        m.addIO(getWireIO(op.getData(), "B"));
        m.addIO(new WireIO(new Wire(String.format("%d'd0", op.getData().getOutputBitsize())), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    HashSet<MulPipeCreator.Options> mulpipes;

    @Override
    public void visit(Multiplication op) {
        if(op.getLhs().getOutputBitsize() < op.getRhs().getOutputBitsize()) {
            Operation lhs = op.getLhs();
            Operation rhs = op.getRhs();
            op.removeLHS();
            op.removeRHS();
            op.setLHS(rhs);
            op.setRHS(lhs);
        }
        int lhs = op.getLhs().getOutputBitsize();
        int rhs = op.getRhs().getOutputBitsize();
        int bit = op.getOutputBitsize();
        Type type = op.getType();
        if (type instanceof datapath.graph.type.Integer) {
            binaryOp("mul", op);
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            if(mul_pipe && lhs <= 64 && rhs <= 64 && bit <= 128) {
                if(mul_pipe_create) {
                MulPipeCreator.Options ops = new MulPipeCreator.Options();
                ops.WA = lhs;
                ops.WB = rhs;
                ops.WR = bit;
                ops.signed = op.isSigned();
                ops.stages = op.getDelay();
                mulpipes.add(ops);
                }
                binaryOp("mul_pipe", op);
            } else {
                binaryOp("mul", op);
            }
        } else if (type instanceof datapath.graph.type.Float) {
            binaryOp("mulfloat", op);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void visit(Subtraction op) {
        Type type = op.getType();
        if (type instanceof datapath.graph.type.Integer) {
            binaryOp("sub", op);
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            binaryOp("sub", op);
        } else if (type instanceof datapath.graph.type.Float) {
            binaryOp("subfloat", op);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void visit(Divide op) {
        Type type = op.getType();
        if (type instanceof datapath.graph.type.Integer) {
            binaryOp("divint", op);
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            if(op.getOutputBitsize() == 64) {
                binaryOp("bigdiv", op);
            } else {
                binaryOp("divint", op);
            }
        } else if (type instanceof datapath.graph.type.Float) {
            binaryOp("divfloat", op);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void visit(Absolut op) {
        Type type = op.getType();
        Module m = null;
        if(type instanceof datapath.graph.type.Integer) {
            m = new Module("abs", op.getNumber());
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            m = new Module("abs", op.getNumber());
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new SIGN(op.getData().isSigned()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(Sin op) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Cos op) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ArcCos op) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(SquareRoot op) {
        Type type = op.getType();
        if (type instanceof datapath.graph.type.Integer) {
            unaryOp("sqrtint", op);
        } else if (type instanceof datapath.graph.type.FixedPoint) {
            unaryOp("sqrtint", op);
        } else if (type instanceof datapath.graph.type.Float) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    @Override
    public void visit(BitwidthTransmogrify op) {
        Module m = new Module("bitsel", op.getNumber());
        m.addParameter(new WA(op.getData().getOutputBitsize()));
        m.addParameter(new WR(op.getOutputBitsize()));
        m.addParameter(new SIGN(op.isSigned()));
        m.addParameter(getDepth(op));

        m.addIO(getWireIO(op.getData(), "A"));
        m.addIO(getWireIO(op, "R"));

        modules.add(m);
    }

    @Override
    public void visit(Predicate op) {
        // do nothing
    }

  @Override
  public void visit(TypeConversion op) {
    throw new UnsupportedOperationException("Not supported.");
  }

    @Override
    public void visit(ConstantMultiplication op) {
        binaryOp("mul", op);
    }
    
}
