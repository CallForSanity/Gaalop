package de.gaalop.algebra;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.DFGTraversalVisitor;
import de.gaalop.visitors.ReplaceVisitor;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Inlines the macros in a ControlFlowGraph
 * @author Christian Steinmetz
 */
public class Inliner extends EmptyControlFlowVisitor {

    private int count = 0;
    private HashMap<StringIntContainer, Macro> macros;
    private ControlFlowGraph graph;

    private boolean containsMacroCall(Expression e) {
        final LinkedList<MacroCall> calls = new LinkedList<MacroCall>();
        e.accept(new DFGTraversalVisitor() {
            @Override
            public void visit(MacroCall node) {
                calls.add(node);
            }
        });
        return !calls.isEmpty();
    }

    private Inliner(HashMap<StringIntContainer, Macro> macros, ControlFlowGraph graph) {   //private constructor for making the usage of the static methods mandatory
        this.macros = macros;
        this.graph = graph;
    }

    public static void inline(ControlFlowGraph graph, HashMap<StringIntContainer, Macro> macros) {
        Inliner inliner = new Inliner(macros, graph);
        while (!inliner.error && MacroCallCounter.countMacroCallsInGraph(graph)>0)
            graph.accept(inliner);
    }

    private AssignmentNode curNode;

    private boolean error = false;

    @Override
    public void visit(AssignmentNode node) {
        curNode = node;
        error = false;
        while (containsMacroCall(node.getValue()) && !error) {
            replacer.result = null;
            node.getValue().accept(replacer);
            if (replacer.result != null) 
                node.setValue(replacer.result);
        }
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        replacer.result = null;
        node.getExpression().accept(replacer);
        if (replacer.result != null)
            node.setExpression(replacer.result);

        super.visit(node);
    }

//TODO chs (optional) macros are case sensitive, math functions not!

    private ReplaceVisitor replacer = new ReplaceVisitor() {

        @Override
        public void visit(MacroCall node) {

            String macroCallName = node.getName();

            //check if this macro call is a MathFunction
            for (MathFunction f: MathFunction.values()) {
                if (f.name().toLowerCase().equals(macroCallName.toLowerCase())) { 
                    // it is a MathFunction
                    result = new MathFunctionCall(node.getArguments().get(0), f);
                    return;
                }
            }

            StringIntContainer container = new StringIntContainer(macroCallName, node.getArguments().size());
            if (!macros.containsKey(container)) {
                System.err.println("Macro "+macroCallName+" is not defined!");
                error = true; //escape endless loop!
            }
            Macro macro = macros.get(container);

            HashMap<String, Expression> replaceMap = new HashMap<String, Expression>();
            //fill replaceMap with macro call arguments
            int index = 1;
            for (Expression e: node.getArguments()) {
                replaceMap.put("_P("+index+")", e);
                index++;
            }

            //add replacements
            for (SequentialNode sNode: macro.getBody()) {
                if (sNode instanceof AssignmentNode) {
                    AssignmentNode assignmentNode = (AssignmentNode) sNode;
                    String name = assignmentNode.getVariable().getName();

                    if (!replaceMap.containsKey(name)) 
                        replaceMap.put(name, createNewVariable());
                }
            }

            for (SequentialNode sNode: macro.getBody()) {
                SequentialNode copySNode = sNode.copy();
                //replace variables in copySNode
                MacroVariablesCFGReplacer r = new MacroVariablesCFGReplacer(replaceMap);
                copySNode.accept(r);
                //
                curNode.insertBefore(copySNode);
            }
            
            Expression copiedReturnVal = macro.getReturnValue().copy();
            //replace variables in copiedReturnVal
            MacroVariablesDFGReplacer d = new MacroVariablesDFGReplacer(replaceMap);
            copiedReturnVal.accept(d);
            //
            result = copiedReturnVal;
        }



    };

    private Variable createNewVariable() {
        VariableCollector collector = new VariableCollector();
        graph.accept(collector);

        count++;
        while (collector.getVariables().contains("macroUniqueName"+count))
            count++;

        return new Variable("macroUniqueName"+count);
    }

}
