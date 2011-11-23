package de.gaalop.algebra;

import java.util.LinkedList;
import de.gaalop.dfg.Expression;
import java.util.List;

/**
 * This class wraps names and arguments which represents built-in macros to real expressions
 * @author Christian Steinmetz
 */
public class FunctionWrapper {

    private LinkedList<Function> functions;

    public FunctionWrapper(LinkedList<Function> functions) {
        this.functions = functions;
    }

    /**
     * Wraps name and arguments to a real expression
     * @param name The name
     * @param args The arguments
     * @return The real expression
     */
    public Expression getExpression(String name, List<Expression> args) {
        int argCount = args.size();
        for (Function f: functions) 
            if (f.isRightFunction(name, argCount)) 
                return f.getCopiedExpression(args.toArray(new Expression[0]));
        return null;
    }

}
