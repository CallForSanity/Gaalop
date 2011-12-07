package de.gaalop.algebra;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class MacroVariablesCFGReplacer implements ControlFlowVisitor {

    private HashMap<String, Expression> replaceMap;
    private MacroVariablesDFGReplacer dfgReplacer;


    public MacroVariablesCFGReplacer(HashMap<String, Expression> replaceMap) {
        this.replaceMap = replaceMap;
        dfgReplacer = new MacroVariablesDFGReplacer(replaceMap);
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(dfgReplacer);
        if (dfgReplacer.result != null) {
            node.setValue(dfgReplacer.result);
            dfgReplacer.result = null;
        }
        if (replaceMap.containsKey(node.getVariable().getName()))
            node.setVariable((Variable) replaceMap.get(node.getVariable().getName()).copy());

    }

    @Override
    public void visit(ExpressionStatement node) {
        node.getExpression().accept(dfgReplacer);
        if (dfgReplacer.result != null) {
            node.setExpression(dfgReplacer.result);
            dfgReplacer.result = null;
        }

    }

    @Override
    public void visit(StoreResultNode node) {
        if (replaceMap.containsKey(node.getValue().getName())) {
            Expression replacement = replaceMap.get(node.getValue().getName());
            if (replacement instanceof Variable) {
                node.setValue((Variable) replacement.copy());
            } else {
                
            }



        }
        
    }

    @Override
    public void visit(StartNode node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(IfThenElseNode node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(BlockEndNode node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(LoopNode node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(BreakNode node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(Macro node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(EndNode node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(ColorNode node) {
        throw new IllegalStateException();
    }
    


}
