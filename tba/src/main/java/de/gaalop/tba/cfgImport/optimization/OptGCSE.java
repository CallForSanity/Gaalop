package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.LoggingListenerGroup;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.gcse.ComparisonResult;
import de.gaalop.tba.cfgImport.optimization.gcse.HashExpression;
import de.gaalop.tba.cfgImport.optimization.gcse.HashExpressions;
import de.gaalop.tba.cfgImport.optimization.gcse.Index;
import de.gaalop.tba.cfgImport.optimization.gcse.IndexCreator;
import de.gaalop.tba.cfgImport.optimization.gcse.OccurenceReplacer;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaInteractor;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Facade class for global common subexpression elimination (GCSE)
 * @author CSteinmetz15
 */
public class OptGCSE implements OptimizationStrategyWithModifyFlag {

    private final boolean optMaxima;
    private final String maximaCommand;
    private final boolean createVariablesInGCSE;
    private MaximaInteractor interactor;
    
    public OptGCSE(boolean optMaxima, String maximaCommand, boolean createVariablesInGCSE) {
        this.optMaxima = optMaxima;
        this.maximaCommand = maximaCommand;
        this.createVariablesInGCSE = createVariablesInGCSE;
    }
    
    /**
     * Finds global equality groups in an index. In a global equality group, all children are proved arithmetically equal.
     * @param index The index to search in
     * @return The list of global equality groups
     * @throws IOException 
     */
    private LinkedList<HashExpressions> findGlobalEqualityGroups(Index index) throws IOException {
        LinkedList<HashExpressions> globalEqualityGroups = new LinkedList<>();
        
        // Verify index elements with maxima
        for (String pattern: index.keySet()) {
            // All elements of index.get(pattern) have the same hash function (=pattern), lets check now for arithmetic equality groups inside of the list index.get(pattern)
            LinkedList<HashExpressions> equalityGroups = new LinkedList<>();
            
            for (HashExpression hashExpression: index.get(pattern)) {
                
                // Is already a group collected, which elements are arithmetically equal to current expression
                HashExpressions equalGroup = null;
                for (HashExpressions equalityGroup: equalityGroups) {
                    ComparisonResult comparisonResult = compareExpressions(hashExpression.expression, equalityGroup.getFirst().expression);

                    if (comparisonResult == ComparisonResult.EQUAL) {
                        equalGroup = equalityGroup;
                        break;
                    }

                    if (comparisonResult == ComparisonResult.NEGATED) {
                        equalGroup = equalityGroup;
                        hashExpression.isNegated = true;
                        break;
                    }
                }
                
                if (equalGroup == null) {
                    // No equalgroup found to current expression -> Create a new one!
                    equalGroup = new HashExpressions();
                    equalityGroups.add(equalGroup);
                } //else: equalgroup to current expression found! Perfomance gain!
                
                //Add this expression to the created/found equality group
                equalGroup.add(hashExpression);
            }

            // Traverse all found equality groups
            for (HashExpressions hashExpressions: equalityGroups) {
                // If there is more than one element in an equality group -> Perfomance gain -> Add to global list
                if (hashExpressions.size() > 1) {
                    globalEqualityGroups.add(hashExpressions);
                }
            }
        }
        return globalEqualityGroups;
    }
    
    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra, LoggingListenerGroup listeners) throws OptimizationException {
        Index index = IndexCreator.createIndex(graph);
 
        // remove those entries, whose containing list has only one element
        index.removeEntriesWithSizeOne();
        
        // If Maxima is enabled, then initialize interactive Maxima connection
        if (optMaxima) {
            interactor = new MaximaInteractor(maximaCommand);
            interactor.openConnection();
        }
        
        try {
            LinkedList<HashExpressions> globalEqualityGroups = findGlobalEqualityGroups(index);

            int counter = 1;
            
            // Create a temporary variable for each equality group straight before the first occurence
            for (HashExpressions hashExpressions: globalEqualityGroups) {
                
                HashExpression firstOccurence = hashExpressions.getFirst();
                
                // Create a temporary variable
                Variable gcseTempVar;
                if (firstOccurence.tempVariable != null) {
                    gcseTempVar = firstOccurence.tempVariable;
                } else {
                    gcseTempVar = (createVariablesInGCSE) ? new Variable("temp_gcse_"+counter) : new MultivectorComponent("temp_gcse_"+counter, 0);
                    counter++;
                }
                
                AssignmentNode node = new AssignmentNode(graph, gcseTempVar, firstOccurence.expression);
                firstOccurence.node.insertBefore(node); // Insert temporary variable straight before the first occurence
                
                // Replace all occurences with the temporary variable
                for (HashExpression occurence: hashExpressions) {
                    Expression replacement = (occurence.isNegated) ? new Negation(gcseTempVar.copy()) : gcseTempVar.copy();
                    OccurenceReplacer.replaceOccurences(occurence.expression, replacement, occurence.node);
                }
            }
        } catch (IOException ex) {
            throw new OptimizationException("IOException thrown: "+ex.getMessage(), graph);
        }
        
        // If Maxima is enabled, then finalize interactive Maxima connection
        if (optMaxima) {
            interactor.closeConnection();
        }

        return false;
    }

    /**
     * Compares two expressions arithmetically
     * @param expression1 The first expression to compare
     * @param expression2 The second expression to compare
     * @return The result of the comparison
     * @throws IOException 
     */
    private ComparisonResult compareExpressions(Expression expression1, Expression expression2) throws IOException {
        // Try first easy and fast case: The structures of the expressions are identical
        if (expression1.equals(expression2))
            // Great!
            return ComparisonResult.EQUAL;
        
        // The structure of the expressions are obviously not identical, check with Maxima on Equality if Maxima is allowed
        if (optMaxima) {
            //Check one time with - (for equality) or + (for negation)
            if (interactor.detectZeroExpression(new Subtraction(expression1, expression2))) {
                return ComparisonResult.EQUAL; // Expression1 is equal to expression2
            } else {
                if (interactor.detectZeroExpression(new Addition(expression1, expression2))) {
                    return ComparisonResult.NEGATED; // Expression1 is the negation of expression2
                } else {
                    return ComparisonResult.DIFFERENT; // Expression1 is for Maxima different to expression2
                }
            }
        }
        
        // An equality can not be proven -> Return that they are different
        return ComparisonResult.DIFFERENT;
    }
}
