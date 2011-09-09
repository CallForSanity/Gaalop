package de.gaalop.tba;

import java.util.Arrays;
import java.util.Vector;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Implements a test for parsing multivectors correctly
 * @author Christian Steinmetz
 */
public class TestParseMultivectors {
    
    private static Algebra algebra;

    private static final double DELTA = 10E-7;

    @BeforeClass
    public static void initialize() {
        algebra = UseAlgebra.get5dConformalGA().getAlgebra();
    }

    private void testDummy(Multivector parsed, Multivector shouldBe) {
        double[] valueArrParsed = parsed.getValueArr();
        double[] valueArrShouldBe = shouldBe.getValueArr();

        assertEquals(valueArrShouldBe.length,valueArrParsed.length);
        for (int i=0;i<valueArrShouldBe.length;i++)
            assertEquals(valueArrShouldBe[i], valueArrParsed[i], DELTA);
    }

    @Test
    public void testParseMultivectorBlade() {
        Multivector parsed = Multivector.parse("e1", algebra);
        Multivector shouldBe = new Multivector(algebra);
        Vector<String> bases = new Vector<String>();
        bases.add("e1");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) 1));

        testDummy(parsed, shouldBe);
    }

    @Test
    public void testParseMultivectorAddition() {
        Multivector parsed = Multivector.parse("e1+e2", algebra);
        Multivector shouldBe = new Multivector(algebra);
        Vector<String> bases = new Vector<String>();
        bases.add("e1");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) 1));

        bases = new Vector<String>();
        bases.add("e2");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) 1));

        testDummy(parsed, shouldBe);
    }


    @Test
    public void testParseMultivectorSubtraction() {
        Multivector parsed = Multivector.parse("e1-e2", algebra);
        Multivector shouldBe = new Multivector(algebra);
        Vector<String> bases = new Vector<String>();
        bases.add("e1");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) 1));

        bases = new Vector<String>();
        bases.add("e2");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        testDummy(parsed, shouldBe);
    }

    @Test
    public void testParseMultivectorFirstSubtraction() {
        Multivector parsed = Multivector.parse("-e1+e2", algebra);
        Multivector shouldBe = new Multivector(algebra);
        Vector<String> bases = new Vector<String>();
        bases.add("e1");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        bases = new Vector<String>();
        bases.add("e2");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) 1));

        testDummy(parsed, shouldBe);
    }

    @Test
    public void testParseMultivectorDoubleSubtraction() {
        Multivector parsed = Multivector.parse("-e1-e2", algebra);
        Multivector shouldBe = new Multivector(algebra);

        Vector<String> bases = new Vector<String>();
        bases.add("e1");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        bases = new Vector<String>();
        bases.add("e2");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        testDummy(parsed, shouldBe);
    }

    @Test
    public void testParseMultivectorTripleSubtraction() {
        Multivector parsed = Multivector.parse("-e1-e2-e3", algebra);
        Multivector shouldBe = new Multivector(algebra);

        Vector<String> bases = new Vector<String>();
        bases.add("e1");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        bases = new Vector<String>();
        bases.add("e2");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        bases = new Vector<String>();
        bases.add("e3");
        shouldBe.addBlade(new Blade(algebra, bases,(byte) -1));

        testDummy(parsed, shouldBe);
    }

}
