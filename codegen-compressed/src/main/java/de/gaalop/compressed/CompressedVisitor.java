package de.gaalop.compressed;

import de.gaalop.Notifications;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.gaalet.*;
import de.gaalop.gaalet.output.*;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++ code.
 */
public class CompressedVisitor extends de.gaalop.gaalet.output.CppVisitor {

    protected boolean gpcMetaInfo = true;

    public CompressedVisitor(boolean standalone) {
        super(standalone);
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();

        FindStoreOutputNodes findOutput = new FindStoreOutputNodes();
        graph.accept(findOutput);
        for (StoreResultNode var : findOutput.getNodes()) {
            String outputName = var.getValue().getName() + "_out";

            outputNamesMap.put(var, outputName);
        }
        if (standalone) {
            code.append("void calculate(");

            // Input Parameters
            List<Variable> inputParameters = sortVariables(graph.getInputVariables());
            for (Variable var : inputParameters) {
                code.append("float "); // The assumption here is that they all are normal scalars
                printVarName(var.getName());
                code.append(", ");
            }

            for (StoreResultNode var : findOutput.getNodes()) {
                code.append("float **");
                printVarName(outputNamesMap.get(var));
                code.append(", ");
            }

            if (!graph.getInputVariables().isEmpty() || !findOutput.getNodes().isEmpty()) {
                code.setLength(code.length() - 2);
            }

            code.append(") {\n");
            indentation++;
        }

        handleLocalVariables();
        createVectorSet(findOutput);
        node.getSuccessor().accept(this);
    }

    /**	
     * Declare local variables
     *	 but first local variables in a set, so we reduce redundancy
     */
    @Override
    protected void handleLocalVariables() {
        for (Variable var : graph.getLocalVariables()) {
            // count number of components
            FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var.getName());
            graph.accept(fieldVisitor);
            final int size = fieldVisitor.getMultiVector().getGaalopBlades().size();
            if (size <= 0)
                continue;

            // GCD definition
            if (gpcMetaInfo) {
                appendIndentation();
                code.append("//#pragma gpc multivector ");
                code.append(var.getName());
                code.append('\n');
            }

            // standard definition
            appendIndentation();
            code.append(variableType + ' ' + var.getName());
            code.append("[" + size + "]");
            code.append(";\n");
        }

        if (!graph.getLocalVariables().isEmpty()) {
            code.append("\n");
        }
    }

    @Override
    public void visit(AssignmentNode node) {
        if (assigned.contains(node.getVariable().getName())) {
            log.warn("Reuse of variable " + node.getVariable().getName()
                    + ". Make sure to reset this variable or use another name.");
            code.append("\n");
            appendIndentation();
            code.append("// Warning: reuse of variable ");
            code.append(node.getVariable().getName());
            code.append(".\n");
            appendIndentation();
            code.append("// Make sure to reset this variable or use another name.\n");
            assigned.remove(node.getVariable().getName());
        }

        appendIndentation();
        node.getVariable().accept(new DetourVisitor());
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";\n");

        node.getSuccessor().accept(this);
    }

    protected class DetourVisitor implements de.gaalop.dfg.ExpressionVisitor {

        @Override
        public void visit(MultivectorComponent component) {
            // get blade pos in array
            final String name = component.getName().replace(suffix, "");
            int pos = -1;
            for (GaaletMultiVector vec : vectorSet) {
                if (name.equals(vec.getName())) {
                    pos = vec.getBladePosInArray(component.getBladeIndex());
                }
            }

            // GCD definition
            final String componentName = name + '[' + pos + ']';
            if (gpcMetaInfo) {
                code.append("//#pragma gpc multivector_component ");
                code.append(component.getName());                
                code.append(' ');
                code.append(component.getBladeName());
                code.append(' ');
                code.append(componentName);
                code.append('\n');
            }

            // standard definition
            code.append(componentName);
        }

        @Override
        public void visit(Subtraction node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Addition node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Division node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(InnerProduct node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Multiplication node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(MathFunctionCall node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Variable node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Exponentiation node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(FloatConstant node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(OuterProduct node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(BaseVector node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Negation node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Reverse node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(LogicalOr node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(LogicalAnd node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(LogicalNegation node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Equality node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Inequality node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(Relation relation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(FunctionArgument node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void visit(MacroCall node) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
