package de.gaalop.ganja;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++
 * code.
 */
public class GradedGanjaVisitor extends GanjaVisitor {

    private String curVar = "";
    private HashMap<Integer, LinkedList<NodeWithGradedIndex>> mapGraded = new HashMap<>();

    public GradedGanjaVisitor(AlgebraProperties algebraProperties) {
        super(algebraProperties);
    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = node.getVariable().getName();
        if (!mvs.contains(varName)) {
            printCollectedAssignments();
            mapGraded.clear();
            curVar = varName;
            mvs.add(varName);
        }

        if (node.getVariable() instanceof MultivectorComponent) {
            MultivectorComponent mvc = (MultivectorComponent) (node.getVariable());
            GradedIndex gradedIndex = getGradedIndexFromBladeIndex(mvc.getBladeIndex());

            if (!mapGraded.containsKey(gradedIndex.i)) {
                mapGraded.put(gradedIndex.i, new LinkedList<NodeWithGradedIndex>());
            }
            mapGraded.get(gradedIndex.i).add(new NodeWithGradedIndex(gradedIndex, node));
        }

        node.getSuccessor().accept(this);
    }
    
    @Override
    public void visit(EndNode node) {
        computeInitializeOutputNullVars();
        computeRenderListWithoutColors();
        
        printCollectedAssignments();
        curVar = "";
        
        addBlocks();
    }

    private class GradedIndex {

        public int i;
        public int j;

        public GradedIndex(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private static Long factorial(int n) {
        if (n <= 1) {
            return 1L;
        }
        Long result = 2L;
        for (int i = 3; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static long binomialCoefficient(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    private GradedIndex getGradedIndexFromBladeIndex(int bladeIndex) {
        int i = 0;
        int n = algebraProperties.signature.getDimension();
        int rest = bladeIndex;
        Long bladeCountInGrade = 1L; // binominalCoeff(n, 0) == 1
        while (rest >= bladeCountInGrade) {
            rest -= bladeCountInGrade;
            i++;
            bladeCountInGrade = binomialCoefficient(n, i);
        }
        return new GradedIndex(i, rest);
    }

    private class NodeWithGradedIndex {

        public GradedIndex gradedIndex;
        public AssignmentNode node;

        public NodeWithGradedIndex(GradedIndex gradedIndex, AssignmentNode node) {
            this.gradedIndex = gradedIndex;
            this.node = node;
        }
    }

    private void printCollectedAssignments() {
        if (mapGraded.isEmpty()) {
            return;
        }
        // curVar is the current variable name
        int n = algebraProperties.signature.getDimension();

        appendI("var " + curVar + " = new Element();\n");

        ArrayList<Integer> grades = new ArrayList<Integer>(mapGraded.keySet());
        grades.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(i1, i2);
            }
        });
        for (Integer grade : grades) {
            String[] positions = new String[(int) binomialCoefficient(n, grade)];
            Arrays.fill(positions, "0");
            for (NodeWithGradedIndex node : mapGraded.get(grade)) {
                StringBuilder expressionCode = new StringBuilder();
                node.node.getValue().accept(new GanjaExpressionVisitor(expressionCode));
                positions[node.gradedIndex.j] = expressionCode.toString();
            }

            appendI(curVar + "[" + grade + "] = [" + String.join(",", positions) + "];\n");
        }
    }
}
