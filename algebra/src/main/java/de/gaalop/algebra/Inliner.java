package de.gaalop.algebra;

import de.gaalop.cfg.*;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.DFGTraversalVisitor;
import de.gaalop.visitors.ReplaceVisitor;
import java.util.ArrayList;
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
    
    private ColorNode currentColorNode = null;

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

    @Override
    public void visit(ColorNode node) {
        curNode = node;
        //make all non-variable arguments to variables
        Expression c = node.getR();
        if (!((c instanceof Variable) || (c instanceof FloatConstant))) {
            Variable newVariable = createNewVariable();
            AssignmentNode assignmentNode = new AssignmentNode(graph, newVariable, c);
            curNode.insertBefore(assignmentNode); 
            StoreResultNode storeNode = new StoreResultNode(graph, newVariable);
            curNode.insertBefore(storeNode);
            node.setR(newVariable);
        } 
        
        c = node.getG();
        if (!((c instanceof Variable) || (c instanceof FloatConstant))) {
            Variable newVariable = createNewVariable();
            AssignmentNode assignmentNode = new AssignmentNode(graph, newVariable, c);
            curNode.insertBefore(assignmentNode); 
            StoreResultNode storeNode = new StoreResultNode(graph, newVariable);
            curNode.insertBefore(storeNode);
            node.setG(newVariable);
        } 

        c = node.getB();
        if (!((c instanceof Variable) || (c instanceof FloatConstant))) {
            Variable newVariable = createNewVariable();
            AssignmentNode assignmentNode = new AssignmentNode(graph, newVariable, c);
            curNode.insertBefore(assignmentNode); 
            StoreResultNode storeNode = new StoreResultNode(graph, newVariable);
            curNode.insertBefore(storeNode);
            node.setB(newVariable);
        } 
        
        c = node.getAlpha();
        if (!((c instanceof Variable) || (c instanceof FloatConstant))) {
            Variable newVariable = createNewVariable();
            AssignmentNode assignmentNode = new AssignmentNode(graph, newVariable, c);
            curNode.insertBefore(assignmentNode); 
            StoreResultNode storeNode = new StoreResultNode(graph, newVariable);
            curNode.insertBefore(storeNode);
            node.setAlpha(newVariable);
        } 
        
        currentColorNode = node;
        super.visit(node);
    }

    private Inliner() {
    }

    private SequentialNode curNode;

    private boolean error = false;

    @Override
    public void visit(AssignmentNode node) {
        curNode = node;
        
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
        curNode = node;
        replacer.result = null;
        delete = false;
        node.getExpression().accept(replacer);
        boolean del = delete;
        Expression result = replacer.result;
        if (result != null)
            node.setExpression(result);

        super.visit(node);
        
        if (del)
            graph.removeNode(node);
    }

    private boolean delete;
    
//TODO chs (optional) macros are case sensitive, math functions not!

    private ReplaceVisitor replacer = new ReplaceVisitor() {

        @Override
        public void visit(MacroCall node) {

            String macroCallName = node.getName();
            
            //check if this macro call is a builtin function
            if ("coefficient".equals(macroCallName.toLowerCase())) {
                result = new Relation(node.getArguments().get(0), node.getArguments().get(1), Relation.Type.COEFFICIENT);
                return;
            }

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
                result = null;
                delete = true;
                graph.unknownMacros.add(new UnknownMacroCall(node, currentColorNode));
                //make all non-variable arguments to variables
                ArrayList<Expression> newArgs = new ArrayList<Expression>(node.getArguments().size());
                for (Expression arg: node.getArguments()) {
                    if (!((arg instanceof Variable) || (arg instanceof FloatConstant))) {
                        Variable newVariable = createNewVariable();
                        AssignmentNode assignmentNode = new AssignmentNode(graph, newVariable, arg);
                        curNode.insertBefore(assignmentNode); 
                        StoreResultNode storeNode = new StoreResultNode(graph, newVariable);
                        curNode.insertBefore(storeNode);
                        newArgs.add(newVariable);
                    } else 
                        newArgs.add(arg);
                }
                node.setArgs(newArgs);
                return;
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
                SequentialNode copySNode = sNode.copy(graph);
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
