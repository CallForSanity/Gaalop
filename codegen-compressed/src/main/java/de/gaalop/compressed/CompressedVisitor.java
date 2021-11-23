package de.gaalop.compressed;

import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.visitors.DFGTraversalVisitor;

import java.util.*;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++ code.
 */
public class CompressedVisitor extends de.gaalop.cpp.CppVisitor {

    protected Map<String,Integer> mvSizes;
    protected Map<String,Map<Integer,Integer>> mvBladeMap = new HashMap<String,Map<Integer,Integer>>();
    protected boolean gpcMetaInfo = true;

    public CompressedVisitor(Map<String,Integer> mvSizes,boolean standalone, boolean useDouble) {
        super(standalone, useDouble);
        this.mvSizes = mvSizes;
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();

        if (standalone) {
            code.append("void calculate(");

            StringList list = new StringList();
            // Input Parameters
            for (String var : graph.getInputs()) {
                list.add(variableType+" "+var);
            }

            for (String var : graph.getLocals()) {
                String add = variableType+" "+var;
                if(mvSizes.get(var) > 1)
                    add += "[" + mvSizes.get(var).toString() + "]";
                list.add(add);
            }

            code.append(list.join());

            code.append(") {\n");
            indentation++;
        } else {
            for (String var : graph.getLocals()) {
                // GPC definition
                if (gpcMetaInfo) {
                    appendIndentation();
                    code.append("//#pragma gpc multivector ");
                    code.append(var);
                    code.append('\n');
                }
                
                // standard definition
                appendIndentation();
                code.append(variableType).append(" ");
                code.append(var);
                if(mvSizes.get(var) > 1)
                    code.append("[" + mvSizes.get(var).toString() + "]");
                code.append(";\n");
            }
        }

        if (!graph.getScalars().isEmpty()) {
            appendIndentation();
            code.append(variableType).append(" ");
            code.append(graph.getScalars().join());
            code.append(";\n");
        }

        if (!graph.getLocalVariables().isEmpty()) {
            code.append("\n");
        }

        node.getSuccessor().accept(this);
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
        node.getVariable().accept(new MultivectorComponentWriteVisitor());
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(MultivectorComponent component) {
        // this method is for reading multivector components
        
        // get blade pos in array
        final String name = component.getName();
        final int pos = mvBladeMap.get(name).get(component.getBladeIndex());
        
        // standard definition
        code.append(name);
        if(mvSizes.get(name) > 1)
            code.append("[" + pos + "]");
    }

    // this visitor is for writing to multivector components
    protected class MultivectorComponentWriteVisitor implements de.gaalop.dfg.ExpressionVisitor {
        
        @Override
        public void visit(MultivectorComponent component) {
            // this method is for writing to multivector components
            
            // get blade pos in array
            final String name = component.getName();
            Map<Integer,Integer> bladeMap = mvBladeMap.get(component.getName());
            if(bladeMap == null)
                bladeMap = new HashMap<Integer, Integer>();
            final int pos = bladeMap.size();
            
            // determine component name
            String componentName = name;
            if(mvSizes.get(name) > 1)
                componentName += "[" + pos + "]";

            // GPC definition
            if (gpcMetaInfo) {
                code.append("//#pragma gpc multivector_component ");
                code.append(component.getName());
                code.append(' ');
                String bladeStr = graph.getAlgebraDefinitionFile().getBladeString(component.getBladeIndex());
                bladeStr = bladeStr.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
                code.append(bladeStr);
                code.append(' ');
                code.append(componentName);
                code.append('\n');
            }

            // standard definition
            code.append(componentName);
            
            // save to blade map
            bladeMap.put(component.getBladeIndex(),pos);
            mvBladeMap.put(component.getName(), bladeMap);
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
