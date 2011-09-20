package de.gaalop.gapp.importing;

import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.variables.GAPPVariable;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPCreatorAllParallel extends GAPPBaseCreator {

    public GAPPCreatorAllParallel(GAPP gapp, int bladeCount) {
        super(gapp, bladeCount);
    }

    @Override
    public void createGAPPInstructionsFromAssignment(Assignment assignment) {
        GAPPVariable destination = new GAPPMultivectorComponent(assignment.getName(), assignment.getIndex());
        HashMap<SignedSummand, Scalarproduct> summands = assignment.getSummands();
        for (SignedSummand summand: summands.keySet()) {
            Scalarproduct scalarproduct = summands.get(summand);

            //TODO here
            //fill GAPP
        }
    }

}
