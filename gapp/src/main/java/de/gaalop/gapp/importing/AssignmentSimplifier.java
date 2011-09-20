package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectType;
import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import de.gaalop.gapp.instructionSet.CalculationType;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class AssignmentSimplifier {

    private LinkedList<Assignment> assignments;

    public void simplify(LinkedList<Assignment> assignments) {
        this.assignments = assignments;
        LinkedList<Assignment> toRemove = new LinkedList<Assignment>();

        for (Assignment assignment : assignments) {
            if (assignment.getSummands().size() == 1) {
                SignedSummand summand = assignment.getSummands().keySet().iterator().next();
                Scalarproduct scalarprod = assignment.getSummands().get(summand);

                if (scalarprod.getObjects().size() == 1) {
                    ParallelObject obj = scalarprod.getObjects().getFirst();
                    boolean replace = false;
                    switch (ParallelObjectType.getType(obj)) {
                        case constant:
                        case mvComponent:
                            replace = true;
                    }

                    if (replace) {
                        if (!summand.isPositiveSigned()) 
                            obj.setNegatedInSum(!obj.isNegatedInSum());
                        
                        replaceInAssignments(assignment.getName(), assignment.getIndex(), obj);
                        toRemove.add(assignment);
                    }
                }
            }
        }

        for (Assignment assignment : toRemove) 
            assignments.remove(assignment);
    }

    private void replaceInAssignments(String name, int index, ParallelObject obj) {
        for (Assignment a : assignments) 
            for (SignedSummand summand : a.getSummands().keySet()) 
                replaceInScalarproduct(a.getSummands().get(summand), name, index, obj);
    }

    private void replaceInScalarproduct(Scalarproduct scalarproduct, String name, int index, ParallelObject obj) {
        LinkedList<ParallelObject> toAdd = new LinkedList<ParallelObject>();
        LinkedList<ParallelObject> toRemove = new LinkedList<ParallelObject>();

        for (ParallelObject objS: scalarproduct.getObjects()) {
            if (equalsParallelObject(objS, name, index)) {
                toRemove.add(objS);
                toAdd.add(obj);
            } else {
                replaceInParallelObject(objS, name, index, obj);
            }
        }
        

        for (ParallelObject objRemove: toRemove) 
            scalarproduct.getObjects().remove(objRemove);

        for (ParallelObject objAdd: toAdd)
            scalarproduct.getObjects().add(objAdd);
    }

    private void replaceInParallelObject(ParallelObject inObject, String name, int index, ParallelObject toReplace) {
        switch (ParallelObjectType.getType(inObject)) {
            case constant:

                break;
            case extCalculation:
                ExtCalculation calc = (ExtCalculation) inObject;
                if (equalsParallelObject(calc.getOperand1(), name, index)) {
                    calc.setOperand1(toReplace);
                    
                } else {
                    replaceInParallelObject(calc.getOperand1(), name, index, toReplace);
                    
                }
                if (calc.getOperand2() != null) {
                    if (equalsParallelObject(calc.getOperand2(), name, index)) {
                        calc.setOperand2(toReplace);
                        
                    } else {
                        replaceInParallelObject(calc.getOperand1(), name, index, toReplace);
                        
                    }
                }

                break;
            case mvComponent:

                break;

        }
    }

    private boolean equalsParallelObject(ParallelObject object, String name, int index) {
        if (ParallelObjectType.getType(object) == ParallelObjectType.mvComponent) {
            MvComponent mvC = (MvComponent) object;
            if (mvC.getMultivectorComponent().getName().equals(name) && mvC.getMultivectorComponent().getBladeIndex() == index) {
                return true;
            }
        }
        return false;
    }
}
