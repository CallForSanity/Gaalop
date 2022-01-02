package de.gaalop.testbenchTbaGapp.productComputer;


import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.Algebra;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.Multivector;

import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class ProductTes {

    protected UseAlgebra useAlgebraClu;

    protected Algebra algebra;

    private void compare(String message, Multivector product1, Multivector product2) {
        assertEquals(message, product2.getValueArr(algebra), product1.getValueArr(algebra));
    }

    protected void dummy(IMultTable table1, IMultTable table2, int gr1, int gr2) {
        int bladeCount = algebra.getBladeCount();
        for (int i=0;i<bladeCount;i++)
            for (int j=0;j<bladeCount;j++)
                if (algebra.getBlade(i).getBases().size() == gr1 && algebra.getBlade(j).getBases().size() == gr2) 
                    compare(algebra.getBlade(i)+"["+i+"],"+algebra.getBlade(j)+"["+j+"]",table1.getProduct(i,j), table2.getProduct(i,j));
    }

}


