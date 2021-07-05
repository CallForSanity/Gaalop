package de.gaalop.testbenchTbaGapp.productComputer;

import de.gaalop.tba.IMultTable;
import de.gaalop.tba.UseAlgebra;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Provides methods for testing the outer products
 * @author Christian Steinmetz
 */
public class OuterProductTest extends ProductTes {

    public static ProductTes test;
    public static IMultTable outer;

    @BeforeClass
    public static void setUp() {
        test = new OuterProductTest();
        test.useAlgebraClu = UseAlgebra.getCGATable();
        test.algebra = test.useAlgebraClu.getAlgebra();
        outer = UseAlgebra.getCGALive().getTableOuter();
    }



    @Test
    public void outerGrade00() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),0,0);
    }

    @Test
    public void outerGrade01() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),0,1);
    }

    @Test
    public void outerGrade02() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),0,2);
    }

    @Test
    public void outerGrade03() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),0,3);
    }

    @Test
    public void outerGrade04() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),0,4);
    }

    @Test
    public void outerGrade05() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),0,5);
    }

    @Test
    public void outerGrade10() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),1,0);
    }

    @Test
    public void outerGrade11() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),1,1);
    }

    @Test
    public void outerGrade12() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),1,2);
    }

    @Test
    public void outerGrade13() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),1,3);
    }

    @Test
    public void outerGrade14() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),1,4);
    }

    @Test
    public void outerGrade15() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),1,5);
    }

    @Test
    public void outerGrade20() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),2,0);
    }

    @Test
    public void outerGrade21() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),2,1);
    }

    @Test
    public void outerGrade22() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),2,2);
    }

    @Test
    public void outerGrade23() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),2,3);
    }

    @Test
    public void outerGrade24() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),2,4);
    }

    @Test
    public void outerGrade25() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),2,5);
    }

    @Test
    public void outerGrade30() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),3,0);
    }

    @Test
    public void outerGrade31() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),3,1);
    }

    @Test
    public void outerGrade32() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),3,2);
    }

    @Test
    public void outerGrade33() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),3,3);
    }

    @Test
    public void outerGrade34() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),3,4);
    }

    @Test
    public void outerGrade35() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),3,5);
    }

    @Test
    public void outerGrade40() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),4,0);
    }

    @Test
    public void outerGrade41() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),4,1);
    }

    @Test
    public void outerGrade42() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),4,2);
    }

    @Test
    public void outerGrade43() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),4,3);
    }

    @Test
    public void outerGrade44() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),4,4);
    }

    @Test
    public void outerGrade45() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),4,5);
    }

    @Test
    public void outerGrade50() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),5,0);
    }

    @Test
    public void outerGrade51() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),5,1);
    }

    @Test
    public void outerGrade52() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),5,2);
    }

    @Test
    public void outerGrade53() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),5,3);
    }

    @Test
    public void outerGrade54() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),5,4);
    }

    @Test
    public void outerGrade55() {
        test.dummy(outer,test.useAlgebraClu.getTableOuter(),5,5);
    }

}