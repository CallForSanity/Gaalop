/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class ToOuterLoop extends ParentOutput {

    private FromInnerLoop target;

    @Override
    public String getDisplayLabel() {
        return "Outer Loop OUTPUT";
    }

    public void setTarget(FromInnerLoop target) {
        this.target = target;
    }

    @Override
    public int getOutputBitsize() {
        return getData().getOutputBitsize();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }





}
