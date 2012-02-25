package de.gaalop.testbenchTbaGapp.gapp.generatedTests;

import de.gaalop.CodeParserException;
import de.gaalop.OptimizationException;
import de.gaalop.gapp.executer.Executer;
import org.junit.Test;
import de.gaalop.testbenchTbaGapp.gapp.*;
import static org.junit.Assert.*;

public class GAPPTest extends Base {
@Test
public void testCircle() {
        try {
            Circle c = new Circle();
            Executer executer = executeProgram(c, "Circle");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

@Test
public void testGPS() {
        try {
            GPS c = new GPS();
            Executer executer = executeProgram(c, "GPS");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

@Test
public void testPaper() {
        try {
            Paper c = new Paper();
            Executer executer = executeProgram(c, "Paper");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

@Test
public void testPaper2() {
        try {
            Paper2 c = new Paper2();
            Executer executer = executeProgram(c, "Paper2");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

@Test
public void testPaper3() {
        try {
            Paper3 c = new Paper3();
            Executer executer = executeProgram(c, "Paper3");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

@Test
public void testMoreVector() {
        try {
            MoreVector c = new MoreVector();
            Executer executer = executeProgram(c, "MoreVector");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

@Test
public void testPaper3d() {
        try {
            Paper3d c = new Paper3d();
            Executer executer = executeProgram(c, "Paper3d");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

}
