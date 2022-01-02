package datapath.graph;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jh
 */
public class MulPipeCreator {

    public static boolean virtex5 = true;

    public static void main(String[] args) {


        Options o = new Options();
        o.WA = 15;
        o.WB = 32;
        o.WR = 46;
        o.stages = 4;
        o.signed = true;

        HashSet<Options> set = new HashSet<Options>();
        set.add(o);

        c(set);
    }

    public static void c(Set<Options> options) {
        File dir = null;
        if(virtex5) {
            dir = new File("/scratch/mul_pipe_virtex5/");
        } else {
            dir = new File("/scratch/mul_pipe_virtex2/");
        }
        dir.mkdirs();
        try {
            BufferedWriter topModule = new BufferedWriter(new FileWriter(new File(
                    dir,
                    "mul_pipe.v")));
            topModule.write(createModule(options));
            topModule.flush();
            topModule.close();
        } catch (IOException ex) {
            Logger.getLogger(MulPipeCreator.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        // create not existing modules with coregen
        for (Options ops : options) {
            File f = new File(dir, String.format("mul_pipe_%s_%d_%d_%d.v",
                    ops.signed ? "s" : "u", ops.WA, ops.WB, ops.WR));
            if (f.exists()) {
                System.out.println("skipping creation of " + ops +
                        " because it already exists");
                continue;
            }
            try {
                String ls_str;
                File opts = File.createTempFile("foo", "bar");
                opts.deleteOnExit();
                BufferedWriter w = new BufferedWriter(new FileWriter(opts));
                if(virtex5) {
                    w.write(createVirtex5(ops));
                } else
                    w.write(create(ops));
                w.flush();
                Process ls_proc = Runtime.getRuntime().exec("coregen -b " +
                        opts.getAbsolutePath(), null, dir);
                // get its output (your input) stream
                DataInputStream ls_in = new DataInputStream(
                        ls_proc.getInputStream());
                try {
                    while ((ls_str = ls_in.readLine()) != null) {
                        System.out.println(ls_str);
                    }
                } catch (IOException e) {
                    System.exit(0);
                }
            } catch (IOException e1) {
                System.err.println(e1);
                System.exit(1);
            }
        }
    }

    private static Set<Options> allOpts(int WA, int WB, int WR) {
        HashSet<Options> opts = new HashSet();
        return opts;
    }

    private static String createModule(Set<Options> options) {
        StringBuffer generate = new StringBuffer();


        generate.append("\n");
        generate.append("`timescale 1ns / 1ns\n");
        generate.append("\n");
        generate.append("`ifdef QUOTIENT\n");
        generate.append("  `undef QUOTIENT\n");
        generate.append("`endif\n");
        generate.append("`ifdef REMAINDER\n");
        generate.append("  `undef REMAINDER\n");
        generate.append("`endif\n");
        generate.append("\n");
        generate.append("/*\n");
        generate.append("`ifdef TARGET_ML507\n");
        generate.append("  `define QUOTIENT quotient\n");
        generate.append("  `define REMAINDER fractional\n");
        generate.append("`else*/\n");
        generate.append("  `define QUOTIENT quot\n");
        generate.append("  `define REMAINDER remd\n");
        generate.append("//`endif\n");
        generate.append("\n");
        generate.append("module mul_pipe\n");
        generate.append("\n");
        generate.append("          #(parameter WA        = 32, \n");
        generate.append("                      WB        = 32, \n");
        generate.append("                      WR        = 32,\n");
        generate.append("                      DEPTH     = 0,\n");
        generate.append("                      QDEPTH    = 16,\n");
        generate.append("                      STATIC_CT = 0,\n");
        generate.append("                      START_CTRL_IN = 0,\n");
        generate.append("                      SIGN      = 0)\n");
        generate.append("\n");
        generate.append("          (input  wire    [(WA-1):0] A, \n");
        generate.append("           input  wire    [(WB-1):0] B, \n");
        generate.append("           output wire    [(WR-1):0] R,\n");
        generate.append("           input  wire               RESET,\n");
        generate.append("           input  wire               CLK,\n");
        generate.append("           input  wire               CE,\n");
        generate.append("           input  wire               START,\n");
        generate.append("           output wire               START_RFD,\n");
        generate.append("           input  wire               START_CTRL,\n");
        generate.append("           output wire               START_CTRL_RFD,\n");
        generate.append("           input  wire               CANCEL,\n");
        generate.append("           output reg                CANCEL_ACCEPT,\n");
        generate.append("           output wire               CANCEL_STATE,\n");
        generate.append(
                "           input  wire               CANCEL_STATE_RESET,\n");
        generate.append(
                "           output wire               CANCEL_STATE_CTRL,\n");
        generate.append(
                "           input  wire               CANCEL_STATE_CTRL_RESET,\n");
        generate.append("           output wire               RESULT_READY,\n");
        generate.append("           input  wire               RESULT_ACCEPT);\n");
        generate.append("           \n");
        generate.append("  // define LATENCY of this module (operator only) \n");
        generate.append(
                "  localparam LATENCY          = (WA <= 16) ? 2 : (WA <= 32) ? 4 : 6; // 32b mod: 34 clocks + 2 clocks if signed\n");
        generate.append(
                "  // CANCEL BITS depending on LATENCY and shift register DEPTH     \n");
        generate.append(
                "  localparam CANCEL_BITS      = min_1(ceil_log2(LATENCY+DEPTH-1));\n");
        generate.append("  // QDEPTH_INT has to be at least 16\n");
        generate.append("  localparam QDEPTH_INT       = min_16(QDEPTH);\n");
        generate.append("  // set QBITS accordingly\n");
        generate.append(
                "  localparam QBITS            = (QDEPTH == 0) ? 0 : ceil_log2(QDEPTH_INT-1);\n");
        generate.append(
                "  // COMPUTATION_BITS depending  on LATENCY shift register DEPTH and queue QDEPTH\n");
        generate.append(
                "  localparam COMPUTATION_BITS = min_1(ceil_log2(LATENCY+DEPTH+QDEPTH_INT-1));\n");
        generate.append("  \n");
        generate.append("                   \n");
        generate.append("  wire rfd;\n");
        generate.append("  wire ce_comp;\n");
        generate.append("  \n");
        generate.append("  wire write;\n");
        generate.append("  wire read;\n");
        generate.append("  wire full;\n");
        generate.append("  wire empty;\n");
        generate.append("  wire result_ready_sr;\n");
        generate.append("  wire increase;\n");
        generate.append("  wire decrease;\n");
        generate.append("  wire increase_comp;\n");
        generate.append("  wire decrease_comp;\n");
        generate.append("  \n");
        generate.append("  wire [(WR-1)              :0]    r_queue;\n");
        generate.append("  wire [(WR-1)              :0]    r_sr;\n");
        generate.append("  reg  [(CANCEL_BITS-1)     :0]    cancel_count; \n");
        generate.append(
                "  reg  [(COMPUTATION_BITS-1):0]    computation_count;\n");
        generate.append(
                "  reg                              cancel_state_keep;\n");
        generate.append(
                "  reg                              cancel_state_ctrl_keep;\n");
        generate.append(
                "  reg                              cancel_to_process; \n");
        generate.append("  \n");
        generate.append("  wire [(WR-1):0]       quot;\n");
        generate.append("  reg  [(LATENCY-1):0]  RESULT_READY_int;\n");
        generate.append("\n");
        generate.append("`include \"handshaking_assigns_blackbox_include.v\"\n");
        generate.append("  \n");
        generate.append("  // synthesis translate_off\n");
        generate.append("  // synthesis translate_on\n");
        generate.append("  \n");
        generate.append("  generate\n");
        for (Options op : options) {
            int WA = op.WA;
            int WB = op.WB;
            int WR = op.WR;
            int sign = op.signed ? 1 : 0;
            String signString = op.signed ? "s" : "u";
            generate.append(String.format(
                    "if ((WA == %d) && (WB == %d) && (WR == %d) && (SIGN == %d)) begin\n",
                    WA, WB, WR, sign));
            generate.append("assign rfd = ce_comp;\n");
            generate.append(String.format(
                    "mul_pipe_%s_%d_%d_%d mul_pipe_%s_%d_%d_%d (\n", signString,
                    WA, WB, WR, signString, WA, WB, WR));
            generate.append(".clk(CLK),\n");
            generate.append(".a(A),\n");
            generate.append(".b(B),\n");
            generate.append(".ce(ce_comp),\n");
            generate.append(".sclr(RESET),\n");
            generate.append(".p(quot));\n");
            generate.append("end\n");
        }

        generate.append("  endgenerate\n");
        generate.append("\n");
        generate.append("\n");
        generate.append("  // shift in RESULT_READY signals based on START\n");
        generate.append(
                "  // thus these are aligned with the computation result\n");
        generate.append("  always @(posedge CLK, posedge RESET) begin\n");
        generate.append("    if (RESET) begin\n");
        generate.append("      RESULT_READY_int <= 0;\n");
        generate.append("    end else begin\n");
        generate.append("      if (ce_comp)\n");
        generate.append(
                "        RESULT_READY_int <= (RESULT_READY_int << 1) | (START & START_RFD);\n");
        generate.append("      else\n");
        generate.append(
                "        RESULT_READY_int <= RESULT_READY_int;            \n");
        generate.append("    end\n");
        generate.append("  end\n");
        generate.append("\n");
        generate.append("  \n");
        generate.append("  // count ongoing computations;\n");
        generate.append("  // increase if START & START_RFD,\n");
        generate.append(
                "  // decrease if datum is read from the queue, or (in case of empty queue) if new result comes out of\n");
        generate.append(
                "  // the pipeline and is cancelled by stored CT or incoming CT\n");
        generate.append("  always @(posedge CLK, posedge RESET) begin\n");
        generate.append("    if (RESET) begin\n");
        generate.append(
                "      computation_count <= {(COMPUTATION_BITS){1'b0}};\n");
        generate.append("    end else begin\n");
        generate.append("      case ({decrease_comp, increase_comp}) \n");
        generate.append(
                "           2'b00: computation_count <= computation_count;\n");
        generate.append(
                "           2'b01: if (computation_count != {(COMPUTATION_BITS){1'b1}})\n");
        generate.append(
                "                     computation_count <= computation_count + 1'b1;\n");
        generate.append(
                "           2'b10: if (computation_count != {(COMPUTATION_BITS){1'b0}})\n");
        generate.append(
                "                     computation_count <= computation_count - 1'b1;              \n");
        generate.append(
                "           2'b11: computation_count <= computation_count; \n");
        generate.append("      endcase\n");
        generate.append("    end\n");
        generate.append("  end  \n");
        generate.append("\n");
        generate.append("  \n");
        generate.append(
                "  // on CANCEL request the signal CANCEL_ACCEPT is assertet on acceptance\n");
        generate.append(
                "  // acceptance is either the increase of an internal counter or the forwarding\n");
        generate.append("  always @(posedge CLK, posedge RESET) begin\n");
        generate.append("    if (RESET) begin\n");
        generate.append("      cancel_count <=  {(CANCEL_BITS){1'b0}};\n");
        generate.append("    end else if (empty) begin\n");
        generate.append("      case ({decrease, increase}) \n");
        generate.append("           2'b00: begin\n");
        generate.append("              cancel_count <= cancel_count;\n");
        generate.append("           end\n");
        generate.append("           2'b01: begin\n");
        generate.append(
                "              if (cancel_count != {(CANCEL_BITS){1'b1}}) begin  \n");
        generate.append(
                "                 cancel_count <= cancel_count + 1'b1;\n");
        generate.append("              end\n");
        generate.append("           end         \n");
        generate.append("           2'b10: begin\n");
        generate.append(
                "              if (cancel_count != {(CANCEL_BITS){1'b0}}) begin\n");
        generate.append(
                "                 cancel_count <= cancel_count - 1'b1;\n");
        generate.append("              end\n");
        generate.append("           end         \n");
        generate.append("           2'b11: begin\n");
        generate.append("                cancel_count <= cancel_count;\n");
        generate.append("           end\n");
        generate.append("      endcase\n");
        generate.append("    end\n");
        generate.append("  end \n");
        generate.append("     \n");
        generate.append("     \n");
        generate.append("  // shift register instantiation    \n");
        generate.append("  shiftreg #(.WA(WR), .WR(WR), .DEPTH(DEPTH)) sr (\n");
        generate.append("    .A(quot), \n");
        generate.append("    .R(r_sr), \n");
        generate.append("    .RESET(RESET), \n");
        generate.append("    .CLK(CLK), \n");
        generate.append(
                "    .CE(~full | read | (~RESULT_READY & ((START_CTRL_IN == 0) | ~result_ready_sr | ~ac_empty))),\n");
        generate.append("    .START(RESULT_READY_int[LATENCY-1]), \n");
        generate.append("    .RESULT_READY(result_ready_sr));\n");
        generate.append("       \n");
        generate.append("  // queue instantiation    \n");
        generate.append(
                "  sr_queue #(.WR(WR), .QDEPTH(QDEPTH_INT), .QBITS(QBITS)) queue_out ( \n");
        generate.append("    .CLK(CLK),         \n");
        generate.append("    .RESET(RESET), \n");
        generate.append("    .DIN(r_sr), \n");
        generate.append("    .READ(read), \n");
        generate.append("    .WRITE(write), \n");
        generate.append("    .DOUT(r_queue),\n");
        generate.append("    .EMPTY(empty), \n");
        generate.append("    .FULL(full));       \n");
        generate.append("  \n");
        generate.append("\n");
        generate.append("  //ceil of the log base 2\n");
        generate.append("  function integer ceil_log2;\n");
        generate.append("    input [31:0] value;\n");
        generate.append(
                "    for (ceil_log2=0; value>0; ceil_log2=ceil_log2+1)\n");
        generate.append("      value = value>>1;\n");
        generate.append("  endfunction\n");
        generate.append("  \n");
        generate.append("  // value is at least 16\n");
        generate.append("  function integer min_16;\n");
        generate.append("    input [31:0] value;\n");
        generate.append("    if ((value < 16) & (value > 0))\n");
        generate.append("      min_16 = 16;\n");
        generate.append("    else\n");
        generate.append("      min_16 = value;\n");
        generate.append("  endfunction\n");
        generate.append("  \n");
        generate.append("  // value cannot be less than 1\n");
        generate.append("  function integer min_1;\n");
        generate.append("    input [31:0] value;\n");
        generate.append("    if (value == 0)\n");
        generate.append("      min_1 = 1;\n");
        generate.append("    else\n");
        generate.append("      min_1 = value;\n");
        generate.append("  endfunction\n");
        generate.append("\n");
        generate.append("endmodule\n");

        return generate.toString();
    }

    private static String createVirtex5(Options opts) {
        StringBuffer generate = new StringBuffer();
        boolean signed = opts.signed;
        int WR = opts.WR;
        int WA = opts.WA;
        int WB = opts.WB;

        String sign = signed ? "Signed" : "Unsigned";
        generate.append("SET addpads = False\n");
        generate.append("SET asysymbol = True\n");
        generate.append("SET busformat = BusFormatAngleBracketNotRipped\n");
        generate.append("SET createndf = False\n");
        generate.append("SET designentry = Verilog\n");
        generate.append("SET device = xc5vfx70t\n");
        generate.append("SET devicefamily = virtex5\n");
        generate.append("SET flowvendor = Other\n");
        generate.append("SET formalverification = False\n");
        generate.append("SET foundationsym = False\n");
        generate.append("SET implementationfiletype = Ngc\n");
        generate.append("SET package = ff1136\n");
        generate.append("SET removerpms = False\n");
        generate.append("SET simulationfiles = Behavioral\n");
        generate.append("SET speedgrade = -2\n");
        generate.append("SET verilogsim = True\n");
        generate.append("SET vhdlsim = False\n");
        generate.append("# END Project Options\n");
        generate.append("# BEGIN Select\n");
        generate.append("SELECT Multiplier family Xilinx,_Inc. 11.2\n");
        generate.append("# END Select\n");
        generate.append("# BEGIN Parameters\n");
        generate.append("CSET ccmimp=Distributed_Memory\n");
        generate.append("CSET clockenable=true\n");
        generate.append(String.format(
                "CSET component_name=mul_pipe_%s_%d_%d_%d\n", signed ? "s" : "u",
                WA, WB, WR));
        generate.append("CSET constvalue=129\n");
        generate.append("CSET internaluser=0\n");
        generate.append("CSET multiplier_construction=Use_Mults\n");
        generate.append("CSET multtype=Parallel_Multiplier\n");
        generate.append("CSET optgoal=Speed\n");
        generate.append(String.format("CSET outputwidthhigh=%d\n",WR-1));
        generate.append("CSET outputwidthlow=0\n");
        generate.append(String.format("CSET pipestages=%d\n",opts.delay()));
        generate.append(String.format("CSET portatype=%s\n",sign));
        generate.append(String.format("CSET portawidth=%d\n",WA));
        generate.append(String.format("CSET portbtype=%s\n",sign));
        generate.append(String.format("CSET portbwidth=%d\n",WB));
        generate.append("CSET roundpoint=0\n");
        generate.append("CSET sclrcepriority=SCLR_Overrides_CE\n");
        generate.append("CSET syncclear=true\n");
        generate.append("CSET use_custom_output_width=true\n");
        generate.append("CSET userounding=false\n");
        generate.append("CSET zerodetect=false\n");
        generate.append("GENERATE\n");
        return generate.toString();
    }

    private static String create(Options opts) {
        StringBuffer generate = new StringBuffer();
        boolean signed = opts.signed;
        int WR = opts.WR;
        int WA = opts.WA;
        int WB = opts.WB;

        String sign = signed ? "Signed" : "Unsigned";


        generate.append("SET addpads = False\n");
        generate.append("SET asysymbol = False\n");
        generate.append("SET busformat = BusFormatAngleBracketNotRipped\n");
        generate.append("SET createndf = False\n");
        generate.append("SET designentry = Verilog\n");
        generate.append("SET device = xc5vfx70t\n");
        generate.append("SET devicefamily = virtex5\n");
//        generate.append("SET device = xc2vp30\n");
//        generate.append("SET devicefamily = virtex2p\n");
        generate.append("SET flowvendor = Other\n");
        generate.append("SET formalverification = False\n");
        generate.append("SET foundationsym = False\n");
        generate.append("SET implementationfiletype = Ngc\n");
//        generate.append("SET package = ff896\n");
        generate.append("SET package = ff1136\n");
        generate.append("SET removerpms = False\n");
        generate.append("SET simulationfiles = Structural\n");
//        generate.append("SET speedgrade = -6\n");
          generate.append("SET speedgrade = -2\n");
        generate.append("SET verilogsim = True\n");
        generate.append("SET vhdlsim = False\n");
//generate.append("# END Project Options\n");
//generate.append("# BEGIN Select\n");
        generate.append("SELECT Multiplier family Xilinx,_Inc. 10.1\n");
//generate.append("# END Select\n");
//generate.append("# BEGIN Parameters\n");
        generate.append("CSET ccmimp=Distributed_Memory\n");
        generate.append("CSET clockenable=true\n");
//generate.append("CSET component_name=mul_pipe_s_16_32\n");
        generate.append(String.format(
                "CSET component_name=mul_pipe_%s_%d_%d_%d\n", signed ? "s" : "u",
                WA, WB, WR));
        generate.append("CSET constvalue=129\n");
        generate.append("CSET internaluser=0\n");
        generate.append("CSET multiplier_construction=Use_Mults\n");
        generate.append("CSET multtype=Parallel_Multiplier\n");
        generate.append("CSET optgoal=Speed\n");
//generate.append("CSET outputwidthhigh=31\n");
        generate.append(String.format("CSET outputwidthhigh=%d\n", WR - 1));
        generate.append("CSET outputwidthlow=0\n");
        //generate.append("CSET pipestages=2\n");
        generate.append(String.format("CSET pipestages=%d\n", opts.delay()));

        //generate.append("CSET portatype=Signed\n");
        generate.append(String.format("CSET portatype=%s\n", sign));
        //generate.append("CSET portawidth=16\n");
        generate.append(String.format("CSET portawidth=%d\n", WA));
        //generate.append("CSET portbtype=Signed\n");
        generate.append(String.format("CSET portbtype=%s\n", sign));
//generate.append("CSET portbwidth=16\n");
        generate.append(String.format("CSET portbwidth=%d\n", WB));
        generate.append("CSET roundpoint=0\n");
        generate.append("CSET sclrcepriority=SCLR_Overrides_CE\n");
        generate.append("CSET syncclear=true\n");
        generate.append("CSET use_custom_output_width=true\n");
        generate.append("CSET userounding=false\n");
        generate.append("CSET zerodetect=false\n");
        generate.append("GENERATE\n");
        return generate.toString();
    }

    public static class Options {

        int WA;
        int WB;
        int WR;
        int stages;
        boolean signed;

        public int delay() {
            return stages;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Options other = (Options) obj;
            if (this.WA != other.WA) {
                return false;
            }
            if (this.WB != other.WB) {
                return false;
            }
            if (this.WR != other.WR) {
                return false;
            }
            if (this.signed != other.signed) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + this.WA;
            hash = 29 * hash + this.WB;
            hash = 29 * hash + this.WR;
            hash = 29 * hash + (this.signed ? 1 : 0);
            return hash;
        }

        @Override
        public String toString() {
            return String.format("mul_pipe_%s_%d_%d_%d", signed ? "s" : "u", WA,
                    WB, WR);
        }
    }
}
