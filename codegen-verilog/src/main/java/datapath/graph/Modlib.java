package datapath.graph;

import datapath.graph.modlib.IO;
import datapath.graph.modlib.Module;
import datapath.graph.modlib.Parameter;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Jens
 */
public class Modlib {

    @Deprecated
    public static String nary_op(String op, int id, int WR, int SIGN,
            String VALUE, int DEPTH, int QDEPTH, int STATIC_CT,
            int START_CTRL_IN, String R,
            String RESET, String CLK, String CE, String START, String START_RFD,
            String START_CTRL, String START_CTRL_RFD,
            String CANCEL, String CANCEL_ACCEPT, String CANCEL_STATE,
            String CANCEL_STATE_RESET,
            String CANCEL_STATE_CTRL, String CANCEL_STATE_CTRL_RESET,
            String RESULT_READY,
            String RESULT_ACCEPT) {
        return String.format(
                "%s #(.WR(%d), .SIGN(%d), .VALUE(%s), .DEPTH(%d), .QDEPTH(%d))" +
                " %s%d (.R(%s), .RESET(%s), .CLK(%s), .CE(%s), .START(%s), .START_RFD(%s)," +
                " .START_CTRL(%s), .START_CTRL_RFD(%s), .CANCEL(%s), .CANCEL_ACCEPT(%s)," +
                " .CANCEL_STATE(%s), .CANCEL_STATE_RESET(%s), .CANCEL_STATE_CTRL(%s)," +
                " .CANCEL_STATE_CTRL_RESET(%s), .RESULT_READY(%s), .RESULT_ACCEPT(%s));",
                op, WR, SIGN, VALUE, DEPTH, QDEPTH, op, id, R,
                RESET, CLK, CE, START, START_RFD, START_CTRL, START_CTRL_RFD,
                CANCEL, CANCEL_ACCEPT, CANCEL_STATE, CANCEL_STATE_RESET,
                CANCEL_STATE_CTRL, CANCEL_STATE_CTRL_RESET, RESULT_READY,
                RESULT_ACCEPT);

    }

    @Deprecated
    public static String binary_op(String op, int id, int WA, int WB, int WR,
            int DEPTH, int QDEPTH, int STATIC_CT, int START_CTRL_IN,
            int SIGN, String A, String B, String R, String RESET, String CLK,
            String CE, String START, String START_RFD, String START_CTRL,
            String START_CTRL_RFD, String CANCEL, String CANCEL_ACCEPT,
            String CANCEL_STATE, String CANCEL_STATE_RESET,
            String CANCEL_STATE_CTRL, String CANCEL_STATE_CTRL_RESET,
            String RESULT_READY, String RESULT_ACCEPT) {
        return String.format(
                "%s #(.WA(%d), .WB(%d), .WR(%d), .SIGN(%d), .DEPTH(%d), .QDEPTH(%d))" +
                " %s%d (.A(%s), .B(%s), .R(%s), .RESET(%s), .CLK(%s), .CE(%s), .START(%s), .START_RFD(%s)," +
                " .START_CTRL(%s), .START_CTRL_RFD(%s), .CANCEL(%s), .CANCEL_ACCEPT(%s)," +
                " .CANCEL_STATE(%s), .CANCEL_STATE_RESET(%s), .CANCEL_STATE_CTRL(%s)," +
                " .CANCEL_STATE_CTRL_RESET(%s), .RESULT_READY(%s), .RESULT_ACCEPT(%s));",
                op, WA, WB, WR, SIGN, DEPTH, QDEPTH, op, id, A, B, R,
                RESET, CLK, CE, START, START_RFD, START_CTRL, START_CTRL_RFD,
                CANCEL, CANCEL_ACCEPT, CANCEL_STATE, CANCEL_STATE_RESET,
                CANCEL_STATE_CTRL, CANCEL_STATE_CTRL_RESET, RESULT_READY,
                RESULT_ACCEPT);
    }

    @Deprecated
    public static String ary_op(String op, int id, int WA, int NIN, int WR,
            int ONEHOT, int DEPTH, int QDEPTH, int STATIC_CT, int START_CTRL_IN,
            int SIGN, String A, String B,
            String R,
            String RESET,
            String CLK,
            String CE,
            String START,
            String START_RFD,
            String START_CTRL,
            String START_CTRL_RFD,
            String CANCEL,
            String CANCEL_ACCEPT,
            String CANCEL_STATE,
            String CANCEL_STATE_RESET,
            String CANCEL_STATE_CTRL,
            String CANCEL_STATE_CTRL_RESET,
            String RESULT_READY,
            String RESULT_ACCEPT) {
        return String.format(
                "%s #(.WA(%d), .NIN(%d), .WR(%d), .ONEHOT(%d), .DEPTH(%d), .QDEPTH(%d)," +
                " .STATIC_CT(%d), .START_CTRL_IN(%d), .SIGN(%d))" +
                " %s%d (.A(%s), .B(%S), .R(%s), .RESET(%s), .CLK(%s), .CE(%s), .START(%s), .START_RFD(%s)," +
                " .START_CTRL(%s), .START_CTRL_RFD(%s), .CANCEL(%s), .CANCEL_ACCEPT(%s)," +
                " .CANCEL_STATE(%s), .CANCEL_STATE_RESET(%s), .CANCEL_STATE_CTRL(%s)," +
                " .CANCEL_STATE_CTRL_RESET(%s), .RESULT_READY(%s), .RESULT_ACCEPT(%s));",
                op, WA, NIN, WR, ONEHOT, DEPTH, QDEPTH, STATIC_CT, START_CTRL_IN,
                SIGN,
                op, id, A, B, R, RESET, CLK, CE, START, START_RFD, START_CTRL,
                START_CTRL_RFD, CANCEL, CANCEL_ACCEPT, CANCEL_STATE,
                CANCEL_STATE_RESET, CANCEL_STATE_CTRL, CANCEL_STATE_CTRL_RESET,
                RESULT_READY, RESULT_ACCEPT);
    }

    public static String module(Module module) {
        StringBuffer buf = new StringBuffer();
        buf.append(module.getType());
        if (module.getParams().size() > 0) {
            buf.append(" #(");
            for (Iterator<Parameter> iter = module.getParams().iterator(); iter.hasNext();) {
                Parameter p = iter.next();
                buf.append(" .").append(p.getName()).append('(').append(
                        p.getValue()).append(')');
                if (iter.hasNext()) {
                    buf.append(',');
                }
            }
            buf.append(")");
        }
        buf.append(' ');
        buf.append("op").append(module.getId()).append('_').append(
                module.getName() != null ? module.getName() : module.getType());
        buf.append(" (");
        for (Iterator<IO> iter = module.getIos().iterator(); iter.hasNext();) {
            IO io = iter.next();
            buf.append(" .").append(io.getName()).append('(').append(
                    io.getValue()).append(')');
            if (iter.hasNext()) {
                buf.append(',');
            }
        }
        buf.append(");");
        return buf.toString();
    }

    @Deprecated
    public static String module(String name, int id, Set<Parameter> parameters,
            Set<IO> ios) {
        Module m = new Module(name, id);
        for (Parameter p : parameters) {
            m.addParameter(p);
        }
        for (IO io : ios) {
            m.addIO(io);
        }
        return module(m);
    }
}
