package de.gaalop.testbenchTbaGapp.productComputer;

import de.gaalop.tba.IMultTable;
import de.gaalop.tba.UseAlgebra;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Provides methods for testing the geo products
 * @author Christian Steinmetz
 */
public class GeoProductTest extends ProductTes {

    public static ProductTes test;
    public static IMultTable geo;

    @BeforeClass
    public static void setUp() {
        test = new InnerProductTest();
        test.useAlgebraClu = UseAlgebra.getCGATable();
        test.algebra = test.useAlgebraClu.getAlgebra();
        geo = UseAlgebra.getCGALive().getTableGeo();
    }


    @Test
    public void geoGrade00() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),0,0);
    }

    @Test
    public void geoGrade01() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),0,1);
    }

    @Test
    public void geoGrade02() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),0,2);
    }

    @Test
    public void geoGrade03() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),0,3);
    }

    @Test
    public void geoGrade04() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),0,4);
    }

    @Test
    public void geoGrade05() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),0,5);
    }

    @Test
    public void geoGrade10() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),1,0);
    }

    @Test
    public void geoGrade11() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),1,1);
    }

    @Test
    public void geoGrade12() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),1,2);
    }

    @Test
    public void geoGrade13() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),1,3);
    }

    @Test
    public void geoGrade14() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),1,4);
    }

    @Test
    public void geoGrade15() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),1,5);
    }

    @Test
    public void geoGrade20() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),2,0);
    }

    @Test
    public void geoGrade21() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),2,1);
    }

    @Test
    public void geoGrade22() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),2,2);
    }

    @Test
    public void geoGrade23() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),2,3);
    }

    @Test
    public void geoGrade24() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),2,4);
    }

    @Test
    public void geoGrade25() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),2,5);
    }

    @Test
    public void geoGrade30() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),3,0);
    }

    @Test
    public void geoGrade31() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),3,1);
    }

    @Test
    public void geoGrade32() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),3,2);
    }

    @Test
    public void geoGrade33() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),3,3);
    }

    @Test
    public void geoGrade34() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),3,4);
    }

    @Test
    public void geoGrade35() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),3,5);
    }

    @Test
    public void geoGrade40() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),4,0);
    }

    @Test
    public void geoGrade41() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),4,1);
    }

    @Test
    public void geoGrade42() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),4,2);
    }

    @Test
    public void geoGrade43() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),4,3);
    }

    @Test
    public void geoGrade44() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),4,4);
    }

    @Test
    public void geoGrade45() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),4,5);
    }

    @Test
    public void geoGrade50() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),5,0);
    }

    @Test
    public void geoGrade51() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),5,1);
    }

    @Test
    public void geoGrade52() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),5,2);
    }

    @Test
    public void geoGrade53() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),5,3);
    }

    @Test
    public void geoGrade54() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),5,4);
    }

    @Test
    public void geoGrade55() {
        test.dummy(geo,test.useAlgebraClu.getTableGeo(),5,5);
    }

}