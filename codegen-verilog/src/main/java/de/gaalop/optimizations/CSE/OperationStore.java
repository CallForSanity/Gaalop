package de.gaalop.optimizations.CSE;


import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.Variable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author pj
 */
public class OperationStore {

   // HashSet<OperationToken> allops = new HashSet();
    HashMap<Integer, OperationToken> allOpsSet = new HashMap();
    HashMap<Integer, OperationToken> ReplaceAbleOpsSet = new HashMap();
     private int savedoperations;

    public HashMap<Integer, OperationToken> getReplaceAbleOpsSet() {
        return ReplaceAbleOpsSet;
    }
    public boolean add(Expression extoadd, AssignmentNode a) {

        int exToAddToHash = extoadd.toString().hashCode();
        OperationToken optok = null;
        assert (extoadd instanceof BinaryOperation) || (extoadd instanceof UnaryOperation);

        if (!allOpsSet.containsKey(exToAddToHash)) {

        // System.out.println("OperationStore: Key not present: " + extoadd.toString());
            if (extoadd instanceof BinaryOperation) {
                // System.out.println("OperationStore: Adding to set: " + extoadd.toString());
                optok = new OperationToken((BinaryOperation) extoadd, a);
                allOpsSet.put(exToAddToHash, optok);
                return true;
            }

            if (extoadd instanceof UnaryOperation) {
                // System.out.println("OperationStore: Adding to set: " + extoadd.toString());
                optok = new OperationToken((UnaryOperation) extoadd, a);
                allOpsSet.put(exToAddToHash, optok);
                return true;
            }

            System.err.println("Something ist not working with CSE (Should not see me) assertions on ?");
            return false;

        } else if (allOpsSet.get(exToAddToHash).getOccurences() == 1) {
        //     System.err.println("  OperationStore: Second Appearance of " + extoadd.toString());
            OperationToken op = allOpsSet.get(exToAddToHash);
            op.incOccurences();
            ReplaceAbleOpsSet.put(exToAddToHash, op);

           

            return false;
        } else {
         //    System.err.println("  OperationStore: More Appearances of " + extoadd.toString());
            ReplaceAbleOpsSet.get(exToAddToHash).incOccurences();
            return false;
        }

    }

    public HashMap<Integer, OperationToken> getAllOpsSet() {
        return allOpsSet;
    }




public boolean containsOperation (Expression e){

return allOpsSet.containsKey(e.toString().hashCode());

}


public boolean OperationShouldBeReplaced (Expression e){

return ReplaceAbleOpsSet.containsKey(e.toString().hashCode());

}



public String getReplacementID (Expression e){
    
OperationToken optok = ReplaceAbleOpsSet.get(e.toString().hashCode());

if (!optok.isUsedToReplace())
    { 
        optok.getAssignment().insertBefore(new AssignmentNode(optok.getAssignment().getGraph(), new Variable(optok.getIdentifier()), optok.getExpression()));
        optok.setUsedToReplace(true);
    }


 return ReplaceAbleOpsSet.get(e.toString().hashCode()).getIdentifier();

}






    public void printSummary() {

        System.out.println("***************************Summary**************************");
        HashMap summary = ReplaceAbleOpsSet;
        int oldmaximum = 0;
        int newmaximum = 0;
        OperationToken winner = null;
        savedoperations = 0;
        for (Iterator it = summary.values().iterator(); it.hasNext();) {
            OperationToken optok = (OperationToken) it.next();
            if (optok.occurences > 1) {
                savedoperations = savedoperations + (optok.occurences - 1);
                System.out.println("Replaced " + optok.occurences + " occurences of:    ( " + optok.tokenexp.toString() + " )       with: " + optok.getIdentifier());
                newmaximum = Math.max(optok.occurences, oldmaximum);
                winner = newmaximum > oldmaximum ? optok : winner;
                oldmaximum = newmaximum;
            }

        }
        if (winner != null) {
            System.out.println("\n \nAnd the winner is: " + winner.tokenexp.toString() + " with: " + winner.occurences + " occurences replaced with: " + winner.getIdentifier());
            System.out.println("\nSaved operations in Total : " + savedoperations);
            System.out.println("\n \n");

        } else {
            System.out.println("\n no Expressions to eliminate found");
        }
        System.out.println("***************************End**************************");
    }

    public int getSavedoperations() {
        return savedoperations;
    }
}








   






