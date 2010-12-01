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
public class Equal extends BinaryOperation {
    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

}
