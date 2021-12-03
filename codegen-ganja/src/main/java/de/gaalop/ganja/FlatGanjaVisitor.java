package de.gaalop.ganja;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import java.util.*;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++
 * code.
 */
public class FlatGanjaVisitor extends GanjaVisitor {

    public FlatGanjaVisitor(AlgebraProperties algebraProperties) {
        super(algebraProperties);
    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = node.getVariable().getName();
        if (!mvs.contains(varName)) {
            appendI("var " + varName + " = new Element();\n");
            mvs.add(varName);
        }

        appendI("");
        node.getVariable().accept(new GanjaExpressionVisitor(code.current));
        code.appendO(" = ");
        node.getValue().accept(new GanjaExpressionVisitor(code.current));
        code.appendO(";");

        if (node.getVariable() instanceof MultivectorComponent) {
            code.appendO(" // ");
            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            code.appendO(graph.getBladeString(component));

            if (!mvComponents.containsKey(component.getName())) {
                mvComponents.put(component.getName(), new LinkedList<Integer>());
            }

            mvComponents.get(component.getName()).add(component.getBladeIndex());
        }

        code.appendO('\n');

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        computeInitializeOutputNullVars();
        computeRenderListWithoutColors();
        addBlocks();
    }
}
