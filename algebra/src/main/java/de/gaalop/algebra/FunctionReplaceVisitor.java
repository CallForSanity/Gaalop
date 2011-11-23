package de.gaalop.algebra;

import de.gaalop.visitors.ReplaceVisitor;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import java.util.LinkedList;
import java.util.List;

/**
 * Replaces all macros with functions, if they are defined
 * @author Christian Steinmetz
 */
public class FunctionReplaceVisitor extends EmptyControlFlowVisitor {

    private FunctionWrapper wrapper;

    public FunctionReplaceVisitor(FunctionWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public FunctionWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(FunctionWrapper wrapper) {
        this.wrapper = wrapper;
    }
    private ReplaceVisitor dfgVisitor = new ReplaceVisitor() {

        @Override
        public void visit(MacroCall node) {
            LinkedList<Expression> args = new LinkedList<Expression>();
            for (Expression arg: node.getArguments()) {
                arg.accept(dfgVisitor);
                if (result != null) {
                    args.add(result);
                    result = null;
                } else
                    args.add(arg);
            }

            node.setArgs(args);

            if (isMathFunction(node.getName(), node.getArguments())) {
                return;
            }

            result = wrapper.getExpression(node.getName(), node.getArguments());
            if (result == null) {
                System.err.println("Function " + node.getName() + " is unknown!");
            }
        }

        /**
         * Determines, if a name is a Math Function
         * @param name The name
         * @param args The arguments
         * @return If the name is a Math Function
         */
        private boolean isMathFunction(String name, List<Expression> args) {
            for (MathFunction mathFunction : MathFunction.values()) {
                if (mathFunction.toString().toLowerCase().equals(name)) {
                    if (args.size() == 1) {
                        result = new MathFunctionCall(args.get(0), mathFunction);
                        return true;
                    } else {
                        throw new IllegalArgumentException("Calling math function " + mathFunction + " with more than one"
                                + " argument: " + args);
                    }
                }
            }
            return false;
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
