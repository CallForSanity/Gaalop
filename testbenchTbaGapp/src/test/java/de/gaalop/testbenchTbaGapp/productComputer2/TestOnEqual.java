/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.testbenchTbaGapp.productComputer2;

import de.gaalop.dfg.Expression;
import de.gaalop.algebra.TCBlade;
import de.gaalop.algebra.BladeArrayRoutines;
import java.util.Arrays;
import de.gaalop.algebra.AlStrategy;
import de.gaalop.tba.Algebra;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.MultTableAbsDirectComputer;
import de.gaalop.tba.MultTableAbsDirectComputer2;
import de.gaalop.tba.Multivector;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 *
 * @author christian
 */
public class TestOnEqual {

    private void compareMultivector(int i1, char c, int i2, Multivector m1, Multivector m2) {
        HashMap<Integer, Integer> container = new HashMap<Integer, Integer>();
        for (BladeRef b: m1.getBlades()) 
            container.put(b.getIndex(),(int) b.getPrefactor());

        boolean ok = true;
        for (BladeRef b: m2.getBlades()) {
            if (container.containsKey(b.getIndex()) && container.get(b.getIndex()) == b.getPrefactor()) 
                container.remove(b.getIndex());
            else
                ok = false;
        }

        if (!ok || !container.isEmpty()) {
            assertTrue(i1+""+c+""+i2,false);
        }

    }

    @Test
    public void testEqual() {
        try {
            int dimension = 5;
            AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
            String baseDirPath = "algebra/" + dimension + "d/";

            alFile.loadFromFile(AlStrategy.class.getResourceAsStream(baseDirPath+"definition.csv"));
            alFile.setUseAsRessource(true);


            TCBlade[] blades = BladeArrayRoutines.createBlades(Arrays.copyOfRange(alFile.base,1,alFile.base.length));
            alFile.blades = new Expression[blades.length];
            for (int i = 0; i < blades.length; i++) {
                alFile.blades[i] = blades[i].toExpression();
            }

            Algebra algebra = new Algebra(alFile);
            int bladeCount = algebra.getBladeCount();

            //Load first product computer
            MultTableAbsDirectComputer comp1In = new MultTableAbsDirectComputer(alFile, new de.gaalop.productComputer.InnerProductCalculator());
            MultTableAbsDirectComputer comp1Out = new MultTableAbsDirectComputer(alFile, new de.gaalop.productComputer.OuterProductCalculator());
            MultTableAbsDirectComputer comp1Geo = new MultTableAbsDirectComputer(alFile, new de.gaalop.productComputer.GeoProductCalculator());
            //Load second product computer
            MultTableAbsDirectComputer2 comp2In = new MultTableAbsDirectComputer2(alFile, new de.gaalop.productComputer2.InnerProductCalculator());
            MultTableAbsDirectComputer2 comp2Out = new MultTableAbsDirectComputer2(alFile, new de.gaalop.productComputer2.OuterProductCalculator());
            MultTableAbsDirectComputer2 comp2Geo = new MultTableAbsDirectComputer2(alFile, new de.gaalop.productComputer2.GeoProductCalculator());

            //compare entries
            for (int i1 = 0; i1 < bladeCount; i1++) {
                for (int i2 = 0; i2 < bladeCount; i2++) {
                    compareMultivector(i1, '.', i2, comp1In.getProduct(i1, i2), comp2In.getProduct(i1, i2));
                    compareMultivector(i1, '^', i2, comp1Out.getProduct(i1, i2), comp2Out.getProduct(i1, i2));
                    compareMultivector(i1, '*', i2, comp1Geo.getProduct(i1, i2), comp2Geo.getProduct(i1, i2));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TestOnEqual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
