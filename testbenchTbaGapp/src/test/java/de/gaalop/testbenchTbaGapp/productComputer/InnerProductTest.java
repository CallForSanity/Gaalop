package de.gaalop.testbenchTbaGapp.productComputer;

import de.gaalop.tba.IMultTable;
import de.gaalop.tba.UseAlgebra;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Provides methods for testing the inner products
 * @author Christian Steinmetz
 */
public class InnerProductTest extends ProductTes {

    public static ProductTes test;
    public static IMultTable inner;

    @BeforeClass
    public static void setUp() {
        test = new InnerProductTest();
        test.useAlgebraClu = UseAlgebra.getCGATable();
        test.algebra = test.useAlgebraClu.getAlgebra();
        inner = UseAlgebra.getCGALive().getTableInner();
    }

    @Test
    public void innerGrade00() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),0,0);
    }

    @Test
    public void innerGrade01() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),0,1);
    }

    @Test
    public void innerGrade02() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),0,2);
    }

    @Test
    public void innerGrade03() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),0,3);
    }

    @Test
    public void innerGrade04() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),0,4);
    }

    @Test
    public void innerGrade05() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),0,5);
    }

    @Test
    public void innerGrade10() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),1,0);
    }

    @Test
    public void innerGrade11() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),1,1);
    }

    @Test
    public void innerGrade12() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),1,2);
    }

    @Test
    public void innerGrade13() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),1,3);
    }

    @Test
    public void innerGrade14() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),1,4);
    }

    @Test
    public void innerGrade15() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),1,5);
    }

    @Test
    public void innerGrade20() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),2,0);
    }

    @Test
    public void innerGrade21() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),2,1);
    }

    @Test
    public void innerGrade22() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),2,2);
    }

    @Test
    public void innerGrade23() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),2,3);
    }

    @Test
    public void innerGrade24() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),2,4);
    }

    @Test
    public void innerGrade25() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),2,5);
    }

    @Test
    public void innerGrade30() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),3,0);
    }

    @Test
    public void innerGrade31() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),3,1);
    }

    @Test
    public void innerGrade32() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),3,2);
    }

    @Test
    public void innerGrade33() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),3,3);
    }

    @Test
    public void innerGrade34() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),3,4);
    }

    @Test
    public void innerGrade35() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),3,5);
    }

    @Test
    public void innerGrade40() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),4,0);
    }

    @Test
    public void innerGrade41() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),4,1);
    }

    @Test
    public void innerGrade42() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),4,2);
    }

    @Test
    public void innerGrade43() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),4,3);
    }

    @Test
    public void innerGrade44() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),4,4);
    }

    @Test
    public void innerGrade45() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),4,5);
    }

    @Test
    public void innerGrade50() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),5,0);
    }

    @Test
    public void innerGrade51() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),5,1);
    }

    @Test
    public void innerGrade52() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),5,2);
    }

    @Test
    public void innerGrade53() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),5,3);
    }

    @Test
    public void innerGrade54() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),5,4);
    }

    @Test
    public void innerGrade55() {
        test.dummy(inner,test.useAlgebraClu.getTableInner(),5,5);
    }

}