package de.gaalop.algebra;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.ReplaceVisitor;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Function {

    private String name;
    private LinkedList<String> args;
    private Expression definition;

    public Function(String name, LinkedList<String> args, Expression definition) {
        this.name = name;
        this.args = args;
        this.definition = definition;
    }

    public LinkedList<String> getArgs() {
        return args;
    }

    public Expression getDefinition() {
        return definition;
    }

    public String getName() {
        return name;
    }

    public void setArgs(LinkedList<String> args) {
        this.args = args;
    }

    public void setDefinition(Expression definition) {
        this.definition = definition;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Determines, if this is the syntactically right function for a given pattern
     * @param name The name of the pattern function
     * @param argCount The number of the pattern arguments
     * @return Is this the syntactically right function
     */
    public boolean isRightFunction(String name, int argCount) {
        return (args.size() == argCount) && (this.name.equals(name));
    }

    @Override
    public String toString() {
        return name+"("+args.toString()+"):="+definition.toString();
    }

    /**
     * Returns the copied expression with actual arguments
     * @param actualArgs The actual arguments
     * @return The copied expression
     */
    public Expression getCopiedExpression(final Expression[] actualArgs) {
        Expression copy = definition.copy();
        ReplaceVisitor visitor = new ReplaceVisitor() {
            @Override
            public void visit(Variable node) {
                String nodeName = node.getName();
                if (args.contains(nodeName)) {
                    int i=0;
                    for (String arg: args) {
                        if (arg.equals(nodeName)) {
                            result = actualArgs[i];
                            return;
                        }
                        i++;
                    }
                }
            }
        };
        copy.accept(visitor);
        if (visitor.result != null)
            return visitor.result;
        else
            return copy;
    }

}
