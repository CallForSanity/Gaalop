package de.gaalop.optimizations.CSE;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Expression;


/**
 *
 * @author pj
 */
public class OperationToken {
    public int occurences =1;
    public Expression tokenexp;
    public AssignmentNode correspondingAssignment ;
    String identifier="noneSet";
    boolean usedToReplace=false;

    public boolean isUsedToReplace() {
        return usedToReplace;
    }

    public void setUsedToReplace(boolean usedToReplace) {
        this.usedToReplace = usedToReplace;
    }

    public Expression getExpression() {
        return tokenexp;
    }



    public OperationToken(BinaryOperation b,AssignmentNode a) {
    tokenexp = b;
    identifier = b.getClass().getSimpleName().substring(0,3) + "_CSE_";
    correspondingAssignment =a;
    }

    public OperationToken(UnaryOperation u,AssignmentNode a) {
    tokenexp=  u;
    correspondingAssignment =a;
    identifier = u.getClass().getSimpleName().substring(0,3) + "_CSE_";
    }



    public int getOccurences() {
        return occurences;
    }

    public void setOccurences(int occurences) {
        this.occurences = occurences;
    }
      public void incOccurences() {
      if (occurences == 1) {
          identifier = identifier  + IDgiver.getINSTANCE().getUnusedID();
      }

        this.occurences = occurences +1;
    }

    public AssignmentNode getAssignment() {
        return correspondingAssignment;
    }

    public String getIdentifier() {
        return identifier;
    }

}
