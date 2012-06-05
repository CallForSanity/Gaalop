/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.testbenchTbaGapp.tba.maxima;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.*;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaDifferentiater;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;
import java.util.LinkedList;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

/**
 *
 * @author christian
 */
public class MaximaDiff {
    
    
    public static void main(String[] args) throws OptimizationException, RecognitionException {
        
        MaximaDifferentiater d = new MaximaDifferentiater();
        LinkedList<AssignmentNode> toDerive = new LinkedList<AssignmentNode>();
        
        toDerive.add(new AssignmentNode(null, new Variable("x"), new Addition(new MultivectorComponent("t",0),new FloatConstant(1))));
        toDerive.add(new AssignmentNode(null, new Variable("x"), new Multiplication(new MultivectorComponent("t",0),new FloatConstant(2))));
        toDerive.add(new AssignmentNode(null, new Variable("x"), new Multiplication(new MultivectorComponent("t",0),new MultivectorComponent("t",0))));
        int i=0;
        for (AssignmentNode node: toDerive)
            System.out.println((i++) +": " + node.toString());
        
        
        LinkedList<AssignmentNode> differentiate = d.differentiate(toDerive, ProcessBuilderMaximaConnection.CMD_MAXIMA_WINDOWS);
        i=0;
        for (AssignmentNode node: differentiate)
            System.out.println((i++) +": "+ node.toString());
        
    }
    
}
