package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import java.util.LinkedList;
import java.util.ListIterator;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author christian
 */
public class MaximaDifferentiater {
    
    public LinkedList<AssignmentNode> differentiate(LinkedList<AssignmentNode> toDerive, String maximaCommand, String variable) throws OptimizationException, RecognitionException {
        MaximaConnection connection = new ProcessBuilderMaximaConnection(maximaCommand);
        
        MaximaInput input = new MaximaInput();
        input.add("display2d:false;"); // very important!
        input.add("ratprint:false;"); // very important!
        input.add("keepfloat:true;");
        fillMaximaInput(toDerive, input, variable);
        input.add("quit();"); // very important!

        MaximaOutput output = connection.optimizeWithMaxima(input);

        //connect in and output
        LinkedList<String> connected = new LinkedList<String>();
        MaximaRoutines.groupMaximaInAndOutputs(connected, output);

        connected.removeFirst(); // remove display2d
        connected.removeFirst(); // remove ratsimp
        connected.removeFirst(); // remove keepfloat
        
        LinkedList<AssignmentNode> result = new LinkedList<AssignmentNode>();
        ListIterator<AssignmentNode> listIterator = toDerive.listIterator();
        for (String io : connected) {
            AssignmentNode node = listIterator.next();
            Expression exp = MaximaRoutines.getExpressionFromMaximaOutput(io);
            result.add(new AssignmentNode(node.getGraph(), new MultivectorComponent(node.getVariable().getName(), 0), exp));
        }

        return result; 
    }

    private void fillMaximaInput(LinkedList<AssignmentNode> toDerive, MaximaInput input, String variable) {

        for (AssignmentNode node : toDerive) {
            DFGToMaximaCode dfg = new DFGToMaximaCode();
            node.getValue().accept(dfg);
            input.add("ratsimp(diff("+dfg.getResultString() + ","+variable+"\\$0,1));");
        }

    }
    
}
