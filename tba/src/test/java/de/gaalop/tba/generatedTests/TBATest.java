package de.gaalop.tba.generatedTests;

import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class TBATest {
  @Test
  public void testCircleNoVars0() {
    CircleNoVars inst = new CircleNoVars();
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
  @Test
  public void testCircleOneVar0() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-25.0f));
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
assertEquals((float) Math.sqrt((-25.0-m_x)*(-25.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar1() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-24.0f));
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
assertEquals((float) Math.sqrt((-24.0-m_x)*(-24.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar2() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-23.0f));
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
assertEquals((float) Math.sqrt((-23.0-m_x)*(-23.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar3() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-22.0f));
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
assertEquals((float) Math.sqrt((-22.0-m_x)*(-22.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar4() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-21.0f));
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
assertEquals((float) Math.sqrt((-21.0-m_x)*(-21.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar5() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-20.0f));
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
assertEquals((float) Math.sqrt((-20.0-m_x)*(-20.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar6() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-19.0f));
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
assertEquals((float) Math.sqrt((-19.0-m_x)*(-19.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar7() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-18.0f));
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
assertEquals((float) Math.sqrt((-18.0-m_x)*(-18.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar8() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-17.0f));
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
assertEquals((float) Math.sqrt((-17.0-m_x)*(-17.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar9() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-16.0f));
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
assertEquals((float) Math.sqrt((-16.0-m_x)*(-16.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar10() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-15.0f));
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
assertEquals((float) Math.sqrt((-15.0-m_x)*(-15.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar11() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-14.0f));
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
assertEquals((float) Math.sqrt((-14.0-m_x)*(-14.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar12() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-13.0f));
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
assertEquals((float) Math.sqrt((-13.0-m_x)*(-13.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar13() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-12.0f));
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
assertEquals((float) Math.sqrt((-12.0-m_x)*(-12.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar14() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-11.0f));
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
assertEquals((float) Math.sqrt((-11.0-m_x)*(-11.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar15() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-10.0f));
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
assertEquals((float) Math.sqrt((-10.0-m_x)*(-10.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar16() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-9.0f));
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
assertEquals((float) Math.sqrt((-9.0-m_x)*(-9.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar17() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-8.0f));
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
assertEquals((float) Math.sqrt((-8.0-m_x)*(-8.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar18() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-7.0f));
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
assertEquals((float) Math.sqrt((-7.0-m_x)*(-7.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar19() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-6.0f));
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
assertEquals((float) Math.sqrt((-6.0-m_x)*(-6.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar20() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-5.0f));
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
assertEquals((float) Math.sqrt((-5.0-m_x)*(-5.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar21() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-4.0f));
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
assertEquals((float) Math.sqrt((-4.0-m_x)*(-4.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar22() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-3.0f));
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
assertEquals((float) Math.sqrt((-3.0-m_x)*(-3.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar23() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-2.0f));
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
assertEquals((float) Math.sqrt((-2.0-m_x)*(-2.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar24() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",-1.0f));
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
assertEquals((float) Math.sqrt((-1.0-m_x)*(-1.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar25() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",0.0f));
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
assertEquals((float) Math.sqrt((0.0-m_x)*(0.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar26() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",1.0f));
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
assertEquals((float) Math.sqrt((1.0-m_x)*(1.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar27() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",2.0f));
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
assertEquals((float) Math.sqrt((2.0-m_x)*(2.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar28() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",3.0f));
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
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar29() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",4.0f));
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
assertEquals((float) Math.sqrt((4.0-m_x)*(4.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar30() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",5.0f));
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
assertEquals((float) Math.sqrt((5.0-m_x)*(5.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar31() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",6.0f));
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
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar32() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",7.0f));
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
assertEquals((float) Math.sqrt((7.0-m_x)*(7.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar33() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",8.0f));
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
assertEquals((float) Math.sqrt((8.0-m_x)*(8.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar34() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",9.0f));
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
assertEquals((float) Math.sqrt((9.0-m_x)*(9.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar35() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",10.0f));
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
assertEquals((float) Math.sqrt((10.0-m_x)*(10.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar36() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",11.0f));
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
assertEquals((float) Math.sqrt((11.0-m_x)*(11.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar37() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",12.0f));
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
assertEquals((float) Math.sqrt((12.0-m_x)*(12.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar38() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",13.0f));
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
assertEquals((float) Math.sqrt((13.0-m_x)*(13.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar39() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",14.0f));
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
assertEquals((float) Math.sqrt((14.0-m_x)*(14.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar40() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",15.0f));
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
assertEquals((float) Math.sqrt((15.0-m_x)*(15.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar41() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",16.0f));
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
assertEquals((float) Math.sqrt((16.0-m_x)*(16.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar42() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",17.0f));
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
assertEquals((float) Math.sqrt((17.0-m_x)*(17.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar43() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",18.0f));
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
assertEquals((float) Math.sqrt((18.0-m_x)*(18.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar44() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",19.0f));
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
assertEquals((float) Math.sqrt((19.0-m_x)*(19.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar45() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",20.0f));
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
assertEquals((float) Math.sqrt((20.0-m_x)*(20.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar46() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",21.0f));
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
assertEquals((float) Math.sqrt((21.0-m_x)*(21.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar47() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",22.0f));
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
assertEquals((float) Math.sqrt((22.0-m_x)*(22.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar48() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",23.0f));
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
assertEquals((float) Math.sqrt((23.0-m_x)*(23.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar49() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",24.0f));
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
assertEquals((float) Math.sqrt((24.0-m_x)*(24.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar50() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1_0",25.0f));
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
assertEquals((float) Math.sqrt((25.0-m_x)*(25.0-m_x) + (2.0-m_y)*(2.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((3.0-m_x)*(3.0-m_x) + (9.0-m_y)*(9.0-m_y)),r_0,0.001);
assertEquals((float) Math.sqrt((6.0-m_x)*(6.0-m_x) + (4.0-m_y)*(4.0-m_y)),r_0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
}
