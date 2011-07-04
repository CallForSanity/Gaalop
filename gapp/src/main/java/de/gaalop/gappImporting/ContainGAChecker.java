package de.gaalop.gappImporting;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Reverse;
import de.gaalop.tba.cfgImport.optimization.DFGTraversalVisitor;

/**
 *
 * @author christian
 */
public class ContainGAChecker extends DFGTraversalVisitor {

    private boolean containGA = false;

    public boolean isContainGA() {
        return containGA;
    }

    @Override
    public void visit(InnerProduct node) {
        containGA = true;
    }

    @Override
    public void visit(Multiplication node) {
        containGA = true;
    }

    
    @Override
    public void visit(OuterProduct node) {
        containGA = true;
    }

    @Override
    public void visit(BaseVector node) {
        containGA = true;
    }

    @Override
    public void visit(Reverse node) {
        containGA = true;
    }

}
