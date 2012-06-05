package de.gaalop.algebra;

import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Variable;

/**
 * Replaces Variables in a Control Flow Graph which are base vectors
 * @author Christian Steinmetz
 */
public class BaseVectorReplaceVisitor extends EmptyControlFlowVisitor {

    private BaseVectorDefiner definer;

    public BaseVectorReplaceVisitor(BaseVectorDefiner definer) {
        this.definer = definer;
    }

    public BaseVectorDefiner getDefiner() {
        return definer;
    }

    public void setDefiner(BaseVectorDefiner definer) {
        this.definer = definer;
    }

    private ReplaceVisitor dfgVisitor = new ReplaceVisitor() {

        @Override
        public void visit(Variable node) {
            if (definer.isBaseVector(node.getName()))
                result = new BaseVector(node.getName().substring(1));
            
            super.visit(node);
        }

    };

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(dfgVisitor);
        if (dfgVisitor.result != null) {
            node.setValue(dfgVisitor.result);
            dfgVisitor.result = null;
        }
        super.visit(node);
    }

}
