package de.gaalop.visualizer;

import de.gaalop.LoggingListenerGroup;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.tba.cfgImport.optimization.maxima.DFGToMaximaCode;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaConnection;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaInput;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaOutput;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaRoutines;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a differentiater that uses maxima
 * @author Christian Steinmetz
 */
public class MaximaDifferentiater implements Differentiater {
    
    private String maximaCommand;

    public MaximaDifferentiater(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }
 
    @Override
    public LinkedList<AssignmentNode> differentiate(LinkedList<AssignmentNode> toDerive, MultivectorComponent variable) {
        try {
            MaximaConnection connection = new ProcessBuilderMaximaConnection(maximaCommand);
            connection.setProgressListeners(new LoggingListenerGroup());
            
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
        } catch (OptimizationException ex) {
            Logger.getLogger(MaximaDifferentiater.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void fillMaximaInput(LinkedList<AssignmentNode> toDerive, MaximaInput input, MultivectorComponent variable) {

        for (AssignmentNode node : toDerive) {
            DFGToMaximaCode dfg = new DFGToMaximaCode();
            node.getValue().accept(dfg);
            input.add("ratsimp(diff("+dfg.getResultString() + ","+variable.getName()+"\\$"+variable.getBladeIndex()+",1));");
        }

    }
    
}
