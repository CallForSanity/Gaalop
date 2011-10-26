package de.gaalop.java;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This visitor traverses the control and data flow graphs and generates Java code.
 */
public class JavaVisitor implements ControlFlowVisitor, ExpressionVisitor {

    protected Log log = LogFactory.getLog(JavaVisitor.class);
    protected StringBuilder codePre = new StringBuilder();
    protected StringBuilder codeCalc = new StringBuilder();
    protected StringBuilder codePost = new StringBuilder();
    protected ControlFlowGraph graph;
    protected int indentation = 0;
    protected Set<String> assigned = new HashSet<String>();
    protected Set<String> declared = new HashSet<String>();
    protected Set<String> toDeclare = new HashSet<String>();
    private final int JAVALIMIT = 65500; // let space for indentation!
    private byte curSection = 0;
    private int storedIndentation;
    private boolean implementFactorial = false;

    /**
     * Appends a character to the result string
     * @param character The character to append
     */
    private void append(char character) {
        append(character + "");
    }

    /**
     * Appends a string to the current code section
     * The code section is definied by the curSection member
     * @param string The string to append to the code section
     */
    private void append(String string) {
        switch (curSection) {
            case 0:
                codePre.append(string);
                break;
            case 1:
                codeCalc.append(string);
                break;
            case 2:
                codePost.append(string);
                break;
        }
    }

    /**
     * Returns the code, created by this code generator
     * @return The generated code
     */
    public String getCode() {
        StringBuilder result = new StringBuilder();
        result.append(codePre);

        if (codeCalc.length() > JAVALIMIT) {
            result.append(splitUpCalcMethods());
        } else {
            result.append(codeCalc);
        }

        result.append(codePost);
        return result.toString();
    }

    /**
     * Appends the indentation to the result string
     */
    protected void appendIndentation() {
        for (int i = 0; i < indentation; ++i) {
            append('\t');
        }
    }

    /**
     * Returns all outputs components of the graph
     * @return The output components
     */
    private LinkedList<String> getOutputs() {
        LinkedList<String> outputs = new LinkedList<String>();

        HashSet<String> known = new HashSet<String>();

        for (String outputVarStr : graph.getPragmaOutputVariables()) {
            declared.add(outputVarStr);
            outputs.add(outputVarStr);
            known.add(outputVarStr.split("\\$")[0]);
        }

        //get StoreResultNodes in graph
        FindStoreOutputNodes storeOutputNodesVisitor = new FindStoreOutputNodes();
        graph.accept(storeOutputNodesVisitor);
        for (StoreResultNode s : storeOutputNodesVisitor.getNodes()) {
            String name = s.getValue().getName();

            // Remind that only variables can be outputted, no MultivectorComponents!
            if (!known.contains(name)) {
                known.add(name);
                int bladeCount = (int) Math.pow(2, graph.getSignature().getDimension());
                for (int blade = 0; blade < bladeCount; blade++) {
                    String bladeName = name + "$" + blade;
                    outputs.add(bladeName);
                    declared.add(bladeName);
                }
            }

        }
        return outputs;
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();

        //process all members
        curSection = 0;
        implementFactorial = false;

        append("import java.util.HashMap;\n\n");

        append("public class " + graph.getSource().getName() + " implements GAProgram {\n");
        indentation++;


        appendIndentation();
        append("// input variables\n");
        for (Variable inputVar : graph.getInputVariables()) {

            String variableName = getVarName(inputVar);
            declared.add(variableName);
            appendIndentation();
            append("private float " + variableName + ";\n");
        }
        append("\n");

        LinkedList<String> outputs = getOutputs();

        appendIndentation();
        append("// output variables\n");
        for (String curOutput : outputs) {
            appendIndentation();
            append("private float " + curOutput + ";\n");
        }
        append("\n");

        // getValue for output variables
        appendIndentation();
        append("@Override\n");
        appendIndentation();
        append("public float getValue(String varName) {\n");
        indentation++;

        for (String curOutput : outputs) {
            appendIndentation();
            append("if (varName.equals(\"" + curOutput + "\")) return " + curOutput + ";\n");
        }

        appendIndentation();
        append("return 0.0f;\n");

        indentation--;
        appendIndentation();
        append("}\n"); // close procedure getValue
        append("\n");

        //getValues for output variables
        appendIndentation();
        append("@Override\n");
        appendIndentation();
        append("public HashMap<String,Float> getValues() {\n");
        indentation++;

        appendIndentation();
        append("HashMap<String,Float> result = new HashMap<String,Float>();\n");

        for (String curOutput : outputs) {
            appendIndentation();
            append("result.put(\"" + curOutput + "\"," + curOutput + ");\n");
        }

        appendIndentation();
        append("return result;\n");

        indentation--;
        appendIndentation();
        append("}\n"); // close procedure getValues


        // setValue for input variables
        appendIndentation();
        append("@Override\n");
        appendIndentation();
        append("public boolean setValue(String varName, float value) {\n");
        indentation++;

        for (Variable inputVar : graph.getInputVariables()) {
            appendIndentation();
            append("if (varName.equals(\"" + getVarName(inputVar) + "\")) { " + getVarName(inputVar) + " = value; return true; }\n");
        }
        appendIndentation();
        append("return false;\n");

        indentation--;
        appendIndentation();
        append("}\n"); // close procedure setValue

        appendIndentation();
        append("\n");
        appendIndentation();
        append("@Override\n");
        appendIndentation();
        append("public void calculate() {\n");
        indentation++;
        storedIndentation = indentation;
        curSection = 1;
        node.getSuccessor().accept(this);
    }

    /**
     * Sorts a set of variables by name to make the order deterministic.
     *
     * @param inputVariables
     * @return
     */
    protected List<Variable> sortVariables(Set<Variable> inputVariables) {
        List<Variable> variables = new ArrayList<Variable>(inputVariables);
        Comparator<Variable> comparator = new Comparator<Variable>() {

            @Override
            public int compare(Variable o1, Variable o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        };

        Collections.sort(variables, comparator);
        return variables;
    }

    private String getVarName(Variable var) {
        if (!(var instanceof MultivectorComponent)) {
            return var.getName() + "$0";
        } else {
            return var.getName() + "$" + ((MultivectorComponent) var).getBladeIndex();
        }

    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = getVarName(node.getVariable());

        appendIndentation();
        if (!(declared.contains(varName) || toDeclare.contains(varName))) {
            toDeclare.add(varName);
        }

        node.getVariable().accept(this);
        append(" = ");
        node.getValue().accept(this);

        if (node.getVariable() instanceof MultivectorComponent) {
            append(';');
            append(" // ");

            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            Expression[] bladeList = node.getGraph().getBladeList();

            BladePrinter bladeVisitor = new BladePrinter(node.getGraph().getSignature());
            bladeList[component.getBladeIndex()].accept(bladeVisitor);
            append(bladeVisitor.getCode());
        }
        append(";\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        appendIndentation();
        node.getExpression().accept(this);
        append(";\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        assigned.add(node.getValue().getName());
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(IfThenElseNode node) {
        Expression condition = node.getCondition();

        appendIndentation();
        append("if (");
        condition.accept(this);
        append(") {\n");

        indentation++;
        node.getPositive().accept(this);
        indentation--;

        appendIndentation();
        append("}");

        if (node.getNegative() instanceof BlockEndNode) {
            append("\n");
        } else {
            append(" else ");

            boolean isElseIf = false;
            if (node.getNegative() instanceof IfThenElseNode) {
                IfThenElseNode ifthenelse = (IfThenElseNode) node.getNegative();
                isElseIf = ifthenelse.isElseIf();
            }
            if (!isElseIf) {
                append("{\n");
                indentation++;
            }

            node.getNegative().accept(this);

            if (!isElseIf) {
                indentation--;
                appendIndentation();
                append("}\n");
            }
        }

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(LoopNode node) {
        appendIndentation();
        append("while(true) {\n");

        indentation++;
        node.getBody().accept(this);
        indentation--;

        appendIndentation();
        append("}\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(BreakNode breakNode) {
        appendIndentation();
        append("break;\n");
    }

    @Override
    public void visit(BlockEndNode node) {
        // nothing to do
    }

    @Override
    public void visit(EndNode node) {
        indentation--;
        appendIndentation();
        append("}\n\n"); // close procedure calculate
        curSection = 2;

        if (implementFactorial) {
            appendIndentation();
            append("private float fact(int n) {\n");
            indentation++;
            appendIndentation();
            append("float result = 1;\n");
            appendIndentation();
            append("for (int i=2;i<=n;i++)\n");
            indentation++;
            appendIndentation();
            append("result *= i;\n");
            indentation--;
            appendIndentation();
            append("return result;\n");
            indentation--;
            appendIndentation();
            append("}\n");
        }

        // print all members that are used for calculating
        for (String decl : toDeclare) {
            appendIndentation();
            append("private float " + decl + ";\n");
        }

        append("\n");
        indentation--;

        appendIndentation();
        append("}\n"); // close class
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    protected void addBinaryInfix(BinaryOperation op, String operator) {
        append("(");
        addChild(op, op.getLeft());
        append(operator);
        addChild(op, op.getRight());
        append(")");
    }

    protected void addChild(Expression parent, Expression child) {
        if (OperatorPriority.hasLowerPriority(parent, child)) {
            append('(');
            child.accept(this);
            append(')');
        } else {
            child.accept(this);
        }
    }

    @Override
    public void visit(Subtraction subtraction) {
        addBinaryInfix(subtraction, " - ");
    }

    @Override
    public void visit(Addition addition) {
        addBinaryInfix(addition, " + ");
    }

    @Override
    public void visit(Division division) {
        addBinaryInfix(division, " / ");
    }

    @Override
    public void visit(InnerProduct innerProduct) {
        throw new UnsupportedOperationException("The Java backend does not support the inner product.");
    }

    @Override
    public void visit(Multiplication multiplication) {
        addBinaryInfix(multiplication, " * ");
    }

    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        String funcName = "(float) Math." + mathFunctionCall.getFunction().toString().toLowerCase();
        if (mathFunctionCall.getFunction() == MathFunction.FACT) {
            funcName = "fact";
            implementFactorial = true;
        }
        append(funcName);
        append('(');
        mathFunctionCall.getOperand().accept(this);
        append(')');
    }

    @Override
    public void visit(Variable variable) {
        append(getVarName(variable));
    }

    @Override
    public void visit(MultivectorComponent component) {
        append(getVarName(component));
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            append("Math.pow(");
            exponentiation.getLeft().accept(this);
            append(',');
            exponentiation.getRight().accept(this);
            append(')');
        }
    }

    /**
     * Returns if the exponentiation has an exponent which is equal to 2
     * @param exponentiation The exponentiation
     * @return <value>true</value> if the exponent is equal to 2, <value>false</value> otherwise
     */
    protected boolean isSquare(Exponentiation exponentiation) {
        final FloatConstant two = new FloatConstant(2.0f);
        return two.equals(exponentiation.getRight());
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        append(Float.toString(floatConstant.getValue()));
        append('f');
    }

    @Override
    public void visit(OuterProduct outerProduct) {
        throw new UnsupportedOperationException("The Java backend does not support the outer product.");
    }

    @Override
    public void visit(BaseVector baseVector) {
        throw new UnsupportedOperationException("The Java backend does not support base vectors.");
    }

    @Override
    public void visit(Negation negation) {
        append("(-");
        addChild(negation, negation.getOperand());
        append(")");
    }

    @Override
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("The Java backend does not support the reverse operation.");
    }

    @Override
    public void visit(LogicalOr node) {
        addBinaryInfix(node, " || ");
    }

    @Override
    public void visit(LogicalAnd node) {
        addBinaryInfix(node, " && ");
    }

    @Override
    public void visit(LogicalNegation node) {
        append('!');
        addChild(node, node.getOperand());
    }

    @Override
    public void visit(Equality node) {
        addBinaryInfix(node, " == ");
    }

    @Override
    public void visit(Inequality node) {
        addBinaryInfix(node, " != ");
    }

    @Override
    public void visit(Relation relation) {
        addBinaryInfix(relation, relation.getTypeString());
    }

    @Override
    public void visit(Macro node) {
        throw new IllegalStateException("Macros should have been inlined and removed from the graph.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macros should have been inlined and no macro calls should be in the graph.");
    }

    //Calc method code can be exceeded about 65535 chars,
    //catch this hard limit by splitting up the calc method
    /**
     * Splits the calculation method in parts, so that each one don't exceeds the java code hard limit of
     * 65535 chars
     * @return The splitted methods
     */
    private String splitUpCalcMethods() {
        StringBuilder result = new StringBuilder();

        int backupIndentation = indentation;
        indentation = storedIndentation;

        int length = codeCalc.length();
        int curPosition = 0;
        int curProcCounter = 1;

        LOOP:
        while (length - curPosition > JAVALIMIT) {
            // Divide in two parts:

            String part = codeCalc.substring(curPosition, curPosition + JAVALIMIT);
            int lastSemicolon = part.lastIndexOf('\n');

            if (lastSemicolon > 0) {
                curPosition += lastSemicolon + 1;
                result.append(part.substring(0, lastSemicolon + 1));
                for (int i = 0; i < indentation; ++i) {
                    result.append('\t');
                }
                result.append("calculate" + curProcCounter + "();\n");

                //end procedure and begin new procedure
                indentation--;
                for (int i = 0; i < indentation; ++i) {
                    result.append('\t');
                }
                result.append("}\n\n");

                for (int i = 0; i < indentation; ++i) {
                    result.append('\t');
                }
                result.append("public void calculate" + curProcCounter + "() {\n");
                indentation++;


                curProcCounter++;
            } else {
                System.err.println("Expression is more than " + JAVALIMIT + " chars long. The outputted file is not compilable with code limit of java! Try to split the calculate method manually!");
                break LOOP; // catch endless loop
            }


        }

        result.append(codeCalc.substring(curPosition));


        indentation = backupIndentation;
        return result.toString();
    }
}
