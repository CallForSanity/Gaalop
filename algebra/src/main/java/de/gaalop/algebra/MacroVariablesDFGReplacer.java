package de.gaalop.algebra;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.ReplaceVisitor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class MacroVariablesDFGReplacer extends ReplaceVisitor {

    private HashMap<String, Expression> replaceMap;

    public MacroVariablesDFGReplacer(HashMap<String, Expression> replaceMap) {
        this.replaceMap = replaceMap;
    }


    @Override
    public void visit(Variable node) {
        if (replaceMap.containsKey(node.getName())) 
            result = replaceMap.get(node.getName()).copy();
        super.visit(node);
    }

    @Override
    public void visit(FunctionArgument node) {
        result = replaceMap.get("_P("+node.getIndex()+")");
    }

    @Override
    public void visit(MacroCall node) {
        ArrayList<Expression> newArgs = new ArrayList<Expression>(node.getArguments().size());

        for (Expression arg: node.getArguments()) {
            arg.accept(this);
            if (result == null)
                newArgs.add(arg);
            else {
                newArgs.add(result);
                result = null;
            }
        }

        node.setArgs(newArgs);
    }

}
