package wordlengthoptimization;

import datapath.graph.Graph;
import datapath.graph.operations.BitwidthTransmogrify;
import datapath.graph.operations.ConstantShift;
import datapath.graph.operations.Operation;
import datapath.graph.operations.ShiftMode;
import datapath.graph.operations.TypeConversion;
import datapath.graph.operations.UnaryOperation;
import datapath.graph.type.FixedPoint;
import datapath.graph.type.Type;
import java.util.HashSet;

/**
 *
 * @author jh
 */
public class RemoveTypeConversion {

    /**
     * Replaces all type conversions from the given graph with the appropriate
     * operations.
     * @param graph
     */
    public void removeTypeConversions(Graph graph){
        for(Operation op : graph.getOperations().toArray(new Operation[0])){
            if(op instanceof TypeConversion){
                removeUselessTypeConversion((TypeConversion) op, graph);
            }
        }
        for(Operation op : graph.getOperations().toArray(new Operation[0])){
            if(op instanceof TypeConversion){
                insertSignChange((TypeConversion)op, graph);
            }
        }
        for(Operation op : graph.getOperations().toArray(new Operation[0])){
            if(op instanceof TypeConversion){
                TypeConversion tc = (TypeConversion)op;
                FixedPoint fpi = (FixedPoint)tc.getInputType();
                FixedPoint fpo = (FixedPoint)tc.getOutputType();
                if(fpi.getBitsize() < fpo.getBitsize()) {
                    insertBitExtension((TypeConversion)op, graph);
                    insertFractionShift((TypeConversion)op, graph);
                } else {
                    insertFractionShift((TypeConversion)op, graph);
                    insertBitExtension((TypeConversion)op, graph);
                }



            }
        }
        for(Operation op : graph.getOperations().toArray(new Operation[0])){
            if(op instanceof TypeConversion){
                
            }
        }
        for(Operation op : graph.getOperations().toArray(new Operation[0])){
            if(op instanceof TypeConversion){
                removeUselessTypeConversion((TypeConversion) op, graph);
            }
        }
        assert !containsTypeConversion(graph);
    }

    private void insertTrans(UnaryOperation bt, TypeConversion op, Graph graph){
        bt.setData(op.getData());
        op.replace(op.getData(), bt);
        graph.addOperation(bt);
    }

    private void insertSignChange(TypeConversion op, Graph graph) {
        if(op.getInputType().isSigned() && !op.getOutputType().isSigned()) {
            // signed -> unsigned
            BitwidthTransmogrify bt = new BitwidthTransmogrify();
            assert op.getInputType() instanceof FixedPoint;
            FixedPoint fp = (FixedPoint)op.getInputType().clone();
            fp.setBitsize(fp.getBitsize()-1);
            fp.setSigned(false);
            bt.setType(fp);
            insertTrans(bt, op, graph);
        } else if (!op.getInputType().isSigned() && op.getOutputType().isSigned()) {
            // unsigned -> signed
            BitwidthTransmogrify bt = new BitwidthTransmogrify();
            assert op.getInputType() instanceof FixedPoint;
            FixedPoint fp = (FixedPoint)op.getInputType().clone();
            fp.setBitsize(fp.getBitsize()+1);
            bt.setType(fp);
            assert bt.getType().isSigned() == false;
            insertTrans(bt, op, graph);
            BitwidthTransmogrify bt2 = new BitwidthTransmogrify();
            bt2.setType(bt.getType().clone());
            bt2.getType().setSigned(true);
            insertTrans(bt2, op, graph);
        }
    }

    private void removeUselessTypeConversion(TypeConversion op, Graph graph) {
        Type inputType = op.getInputType();
        Type outputType = op.getOutputType();
        Operation data = op.getData();
        if(inputType.equals(outputType)) {
            // just remove the type conversion
            op.removeData();
            for(Operation use : op.getUse().toArray(new Operation[0])){
                use.replace(op, data);
            }
            graph.remove(op);
        }
    }

    private void insertBitExtension(TypeConversion op, Graph graph) {
        if(op.getInputType().getBitsize() != op.getOutputType().getBitsize()) {
            BitwidthTransmogrify bt = new BitwidthTransmogrify();
            bt.setType(op.getInputType().clone());
            bt.getType().setBitsize(op.getOutputType().getBitsize());
            insertTrans(bt, op, graph);
        }
    }

    private void insertFractionShift(TypeConversion op, Graph graph) {
        assert op.getInputType() instanceof FixedPoint;
        assert op.getOutputType() instanceof FixedPoint;
        FixedPoint inputType = (FixedPoint)op.getInputType();
        FixedPoint outputType = (FixedPoint)op.getOutputType();
        if(inputType.getFractionlength() != outputType.getFractionlength()) {
            // insert shift
            int shiftAmount = Math.abs(inputType.getFractionlength()-outputType.getFractionlength());
            ConstantShift shift = null;
            if(inputType.getFractionlength() > outputType.getFractionlength()){
                // insert right shift
                shift = new ConstantShift(shiftAmount, ShiftMode.Right);
            } else {
                // insert left shift
                shift = new ConstantShift(shiftAmount, ShiftMode.Left);
            }
            shift.setType(outputType.clone());
            shift.getType().setBitsize(inputType.getBitsize());
            insertTrans(shift, op, graph);
        }
    }

    private boolean containsTypeConversion(Graph graph) {
        for(Operation op : graph.getOperations().toArray(new Operation[0])){
            if(op instanceof TypeConversion){
                return true;
            }
        }
        return false;
    }
}
