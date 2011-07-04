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
  @Test
  public void testMultipleAssignments0() {
    MultipleAssignments inst = new MultipleAssignments();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = new HashMap<String,Float>();
    outputs.put("b_0",inst.getValue("b_0"));
    outputs.put("b_1",inst.getValue("b_1"));
    outputs.put("b_2",inst.getValue("b_2"));
    outputs.put("b_3",inst.getValue("b_3"));
    outputs.put("b_4",inst.getValue("b_4"));
    outputs.put("b_5",inst.getValue("b_5"));
    outputs.put("b_6",inst.getValue("b_6"));
    outputs.put("b_7",inst.getValue("b_7"));
    outputs.put("b_8",inst.getValue("b_8"));
    outputs.put("b_9",inst.getValue("b_9"));
    outputs.put("b_10",inst.getValue("b_10"));
    outputs.put("b_11",inst.getValue("b_11"));
    outputs.put("b_12",inst.getValue("b_12"));
    outputs.put("b_13",inst.getValue("b_13"));
    outputs.put("b_14",inst.getValue("b_14"));
    outputs.put("b_15",inst.getValue("b_15"));
    outputs.put("b_16",inst.getValue("b_16"));
    outputs.put("b_17",inst.getValue("b_17"));
    outputs.put("b_18",inst.getValue("b_18"));
    outputs.put("b_19",inst.getValue("b_19"));
    outputs.put("b_20",inst.getValue("b_20"));
    outputs.put("b_21",inst.getValue("b_21"));
    outputs.put("b_22",inst.getValue("b_22"));
    outputs.put("b_23",inst.getValue("b_23"));
    outputs.put("b_24",inst.getValue("b_24"));
    outputs.put("b_25",inst.getValue("b_25"));
    outputs.put("b_26",inst.getValue("b_26"));
    outputs.put("b_27",inst.getValue("b_27"));
    outputs.put("b_28",inst.getValue("b_28"));
    outputs.put("b_29",inst.getValue("b_29"));
    outputs.put("b_30",inst.getValue("b_30"));
    outputs.put("b_31",inst.getValue("b_31"));
    outputs.put("d_0",inst.getValue("d_0"));
    outputs.put("d_1",inst.getValue("d_1"));
    outputs.put("d_2",inst.getValue("d_2"));
    outputs.put("d_3",inst.getValue("d_3"));
    outputs.put("d_4",inst.getValue("d_4"));
    outputs.put("d_5",inst.getValue("d_5"));
    outputs.put("d_6",inst.getValue("d_6"));
    outputs.put("d_7",inst.getValue("d_7"));
    outputs.put("d_8",inst.getValue("d_8"));
    outputs.put("d_9",inst.getValue("d_9"));
    outputs.put("d_10",inst.getValue("d_10"));
    outputs.put("d_11",inst.getValue("d_11"));
    outputs.put("d_12",inst.getValue("d_12"));
    outputs.put("d_13",inst.getValue("d_13"));
    outputs.put("d_14",inst.getValue("d_14"));
    outputs.put("d_15",inst.getValue("d_15"));
    outputs.put("d_16",inst.getValue("d_16"));
    outputs.put("d_17",inst.getValue("d_17"));
    outputs.put("d_18",inst.getValue("d_18"));
    outputs.put("d_19",inst.getValue("d_19"));
    outputs.put("d_20",inst.getValue("d_20"));
    outputs.put("d_21",inst.getValue("d_21"));
    outputs.put("d_22",inst.getValue("d_22"));
    outputs.put("d_23",inst.getValue("d_23"));
    outputs.put("d_24",inst.getValue("d_24"));
    outputs.put("d_25",inst.getValue("d_25"));
    outputs.put("d_26",inst.getValue("d_26"));
    outputs.put("d_27",inst.getValue("d_27"));
    outputs.put("d_28",inst.getValue("d_28"));
    outputs.put("d_29",inst.getValue("d_29"));
    outputs.put("d_30",inst.getValue("d_30"));
    outputs.put("d_31",inst.getValue("d_31"));
    // check outputs
assertEquals(outputs.get("b_0"),outputs.get("d_0"),0.001);
assertEquals(outputs.get("b_1"),outputs.get("d_1"),0.001);
assertEquals(outputs.get("b_2"),outputs.get("d_2"),0.001);
assertEquals(outputs.get("b_3"),outputs.get("d_3"),0.001);
assertEquals(outputs.get("b_4"),outputs.get("d_4"),0.001);
assertEquals(outputs.get("b_5"),outputs.get("d_5"),0.001);
assertEquals(outputs.get("b_6"),outputs.get("d_6"),0.001);
assertEquals(outputs.get("b_7"),outputs.get("d_7"),0.001);
assertEquals(outputs.get("b_8"),outputs.get("d_8"),0.001);
assertEquals(outputs.get("b_9"),outputs.get("d_9"),0.001);
assertEquals(outputs.get("b_10"),outputs.get("d_10"),0.001);
assertEquals(outputs.get("b_11"),outputs.get("d_11"),0.001);
assertEquals(outputs.get("b_12"),outputs.get("d_12"),0.001);
assertEquals(outputs.get("b_13"),outputs.get("d_13"),0.001);
assertEquals(outputs.get("b_14"),outputs.get("d_14"),0.001);
assertEquals(outputs.get("b_15"),outputs.get("d_15"),0.001);
assertEquals(outputs.get("b_16"),outputs.get("d_16"),0.001);
assertEquals(outputs.get("b_17"),outputs.get("d_17"),0.001);
assertEquals(outputs.get("b_18"),outputs.get("d_18"),0.001);
assertEquals(outputs.get("b_19"),outputs.get("d_19"),0.001);
assertEquals(outputs.get("b_20"),outputs.get("d_20"),0.001);
assertEquals(outputs.get("b_21"),outputs.get("d_21"),0.001);
assertEquals(outputs.get("b_22"),outputs.get("d_22"),0.001);
assertEquals(outputs.get("b_23"),outputs.get("d_23"),0.001);
assertEquals(outputs.get("b_24"),outputs.get("d_24"),0.001);
assertEquals(outputs.get("b_25"),outputs.get("d_25"),0.001);
assertEquals(outputs.get("b_26"),outputs.get("d_26"),0.001);
assertEquals(outputs.get("b_27"),outputs.get("d_27"),0.001);
assertEquals(outputs.get("b_28"),outputs.get("d_28"),0.001);
assertEquals(outputs.get("b_29"),outputs.get("d_29"),0.001);
assertEquals(outputs.get("b_30"),outputs.get("d_30"),0.001);
assertEquals(outputs.get("b_31"),outputs.get("d_31"),0.001);

  }
}
