/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian
 */
public class DFGDifferentiaterTest {
    
    public DFGDifferentiaterTest() {
    }

    /**
     * Test of differentiate method, of class DFGDifferentiater.
     */
    @Test
    public void testDifferentiate() {
        Expression e = new Exponentiation(new Addition(new MultivectorComponent("x", 0), new MultivectorComponent("y", 0)), new FloatConstant(2));
        Expression d = DFGDifferentiater.differentiate(e, new MultivectorComponent("x", 0));
        System.out.println(d.toString());
    }

}
