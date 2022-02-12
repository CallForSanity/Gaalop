package de.gaalop.tba.cfgImport.optimization.gcse;

import de.gaalop.StringList;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents hashexpressions as index using a pattern map
 * @author CSteinmetz15
 */
public class Index extends HashMap<String, HashExpressions> {
    
    /**
     * Indexes an expression
     * @param expr The expression to index
     * @param pattern The precomputed pattern list of the expression
     * @param curNode The current node of the expression
     * @param tempVariable The temporary variable of the expression
     */
    public void indexExpression(Expression expr, StringList pattern, SequentialNode curNode, Variable tempVariable) {
        if (pattern.size() > 1) { // Terminals should not be indexed, because there is no performance gain in substitution
            Collections.sort(pattern);
            String patternStr = pattern.join(",");
            HashExpression hashExpression = new HashExpression(curNode, expr, patternStr, tempVariable);

            HashExpressions hashExpressions;
            if (containsKey(patternStr)) {
                hashExpressions = get(patternStr);
            } else {
                hashExpressions = new HashExpressions();
                put(patternStr, hashExpressions);
            }

            hashExpressions.add(hashExpression);
        }
    }

    /**
     * Removes the entries with a list size of one
     */
    public void removeEntriesWithSizeOne() {
        StringList toRemove = new StringList();
        for (String pattern: keySet()) 
            if (get(pattern).size() == 1) 
                toRemove.add(pattern);
        
        for (String r: toRemove)
            remove(r);
    }
    
}
