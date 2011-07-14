package de.gaalop.gapp.generatedTests;

import de.gaalop.CodeParserException;
import de.gaalop.OptimizationException;
import de.gaalop.gapp.executer.Executer;
import org.junit.Test;
import de.gaalop.gapp.*;
import static org.junit.Assert.*;
import org.junit.Ignore;

@Ignore
public class GAPPTest extends Base {
@Test
public void testCircle() {
        try {
            Circle c = new Circle();
            Executer executer = executeProgramm(c, "Circle");
            c.testOutput(executer);
        } catch (OptimizationException ex) {
            assertTrue("Optimization Error", false);
        } catch (CodeParserException ex) {
             assertTrue("Code Parse Error", false);
        }
}

}
