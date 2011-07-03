package de.gaalop.tba.generatedTests;

import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class TBATest {
  @Test
  public void testCircleOnlyVars0() {
    CircleOnlyVars inst = new CircleOnlyVars();
    assertTrue(inst.setValue("x1_0",5.0f));
    assertTrue(inst.setValue("y1_0",2.0f));
    assertTrue(inst.setValue("x2_0",3.0f));
    assertTrue(inst.setValue("y2_0",9.0f));
    assertTrue(inst.setValue("x3_0",6.0f));
    assertTrue(inst.setValue("y3_0",4.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = new HashMap<String,Float>();
    outputs.put("m_1",inst.getValue("m_1"));
    outputs.put("m_2",inst.getValue("m_2"));
    outputs.put("r_0",inst.getValue("r_0"));
    // check outputs
// check containing r_0,m_1,m_2
assertTrue(outputs.containsKey("r_0"));
assertTrue(outputs.containsKey("m_1"));
assertTrue(outputs.containsKey("m_2"));
float r_0 = outputs.get("r_0");
float m_x = outputs.get("m_1");
float m_y = outputs.get("m_2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((5-m_x)*(5-m_x) + (2-m_y)*(2-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3-m_x)*(3-m_x) + (9-m_y)*(9-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6-m_x)*(6-m_x) + (4-m_y)*(4-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
}
