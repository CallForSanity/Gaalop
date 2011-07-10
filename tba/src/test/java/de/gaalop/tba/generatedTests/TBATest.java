package de.gaalop.tba.generatedTests;

import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class TBATest {
  @Test
  public void testCircleOneVar0() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",0.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r_0,m_1,m_2
assertTrue(outputs.containsKey("r_0"));
assertTrue(outputs.containsKey("m_1"));
assertTrue(outputs.containsKey("m_2"));
float r_0 = outputs.get("r_0");
float m_x = outputs.get("m_1");
float m_y = outputs.get("m_2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((0.0-m_x)*(0.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
}
