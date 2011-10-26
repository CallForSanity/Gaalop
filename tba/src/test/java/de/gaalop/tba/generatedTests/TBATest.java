package de.gaalop.tba.generatedTests;

import java.util.HashMap;
import de.gaalop.tba.gps.Point3D;

import de.gaalop.tba.linePointDistance.Vec3D;

import org.junit.Test;

import static org.junit.Assert.*;

public class TBATest {
  @Test
  public void testTrafoTst0() {
    TrafoTst inst = new TrafoTst();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
float p$1 = outputs.get("p$1");
float p$2 = outputs.get("p$2");
float p$5 = outputs.get("p$5");
float pt2$1 = outputs.get("pt2$1");
float pt2$2 = outputs.get("pt2$2");
float pt2$5 = outputs.get("pt2$5");
float pt3$1 = outputs.get("pt3$1");
float pt3$2 = outputs.get("pt3$2");
float pt3$5 = outputs.get("pt3$5");
assertEquals(p$1,pt2$1,0.001);
assertEquals(p$2,pt2$2,0.001);
assertEquals(p$5,pt2$5,0.001);
assertEquals(p$1,pt3$1,0.001);
assertEquals(p$2,pt3$2,0.001);
assertEquals(p$5,pt3$5,0.001);

  }
  @Test
  public void testTrigonometricFunctions0() {
    TrigonometricFunctions inst = new TrigonometricFunctions();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
assertTrue(outputs.containsKey("r$0"));
float r$0 = outputs.get("r$0");
assertEquals(1,r$0,0.01);
  }
  @Test
  public void testCircleNoVars0() {
    CircleNoVars inst = new CircleNoVars();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((5-m$x)*(5-m$x) + (2-m$y)*(2-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3-m$x)*(3-m$x) + (9-m$y)*(9-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6-m$x)*(6-m$x) + (4-m$y)*(4-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar0() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-25.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-25.0-m$x)*(-25.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar1() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-24.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-24.0-m$x)*(-24.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar2() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-23.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-23.0-m$x)*(-23.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar3() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-22.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-22.0-m$x)*(-22.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar4() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-21.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-21.0-m$x)*(-21.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar5() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-20.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-20.0-m$x)*(-20.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar6() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-19.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-19.0-m$x)*(-19.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar7() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-18.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-18.0-m$x)*(-18.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar8() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-17.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-17.0-m$x)*(-17.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar9() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-16.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-16.0-m$x)*(-16.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar10() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-15.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-15.0-m$x)*(-15.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar11() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-14.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-14.0-m$x)*(-14.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar12() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-13.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-13.0-m$x)*(-13.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar13() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-12.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-12.0-m$x)*(-12.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar14() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-11.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-11.0-m$x)*(-11.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar15() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-10.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-10.0-m$x)*(-10.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar16() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-9.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-9.0-m$x)*(-9.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar17() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-8.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-8.0-m$x)*(-8.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar18() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-7.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-7.0-m$x)*(-7.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar19() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-6.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-6.0-m$x)*(-6.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar20() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-5.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-5.0-m$x)*(-5.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar21() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-4.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-4.0-m$x)*(-4.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar22() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-3.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-3.0-m$x)*(-3.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar23() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-2.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-2.0-m$x)*(-2.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar24() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",-1.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((-1.0-m$x)*(-1.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar25() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",0.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((0.0-m$x)*(0.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar26() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",1.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((1.0-m$x)*(1.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar27() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",2.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((2.0-m$x)*(2.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar28() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",3.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar29() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",4.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((4.0-m$x)*(4.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar30() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",5.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((5.0-m$x)*(5.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar31() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",6.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar32() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",7.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((7.0-m$x)*(7.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar33() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",8.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((8.0-m$x)*(8.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar34() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",9.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((9.0-m$x)*(9.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar35() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",10.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((10.0-m$x)*(10.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar36() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",11.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((11.0-m$x)*(11.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar37() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",12.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((12.0-m$x)*(12.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar38() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",13.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((13.0-m$x)*(13.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar39() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",14.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((14.0-m$x)*(14.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar40() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",15.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((15.0-m$x)*(15.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar41() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",16.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((16.0-m$x)*(16.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar42() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",17.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((17.0-m$x)*(17.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar43() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",18.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((18.0-m$x)*(18.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar44() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",19.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((19.0-m$x)*(19.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar45() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",20.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((20.0-m$x)*(20.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar46() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",21.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((21.0-m$x)*(21.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar47() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",22.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((22.0-m$x)*(22.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar48() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",23.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((23.0-m$x)*(23.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar49() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",24.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((24.0-m$x)*(24.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOneVar50() {
    CircleOneVar inst = new CircleOneVar();
    assertTrue(inst.setValue("x1$0",25.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((25.0-m$x)*(25.0-m$x) + (2.0-m$y)*(2.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3.0-m$x)*(3.0-m$x) + (9.0-m$y)*(9.0-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6.0-m$x)*(6.0-m$x) + (4.0-m$y)*(4.0-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testCircleOnlyVars0() {
    CircleOnlyVars inst = new CircleOnlyVars();
    assertTrue(inst.setValue("x1$0",5.0f));
    assertTrue(inst.setValue("y1$0",2.0f));
    assertTrue(inst.setValue("x2$0",3.0f));
    assertTrue(inst.setValue("y2$0",9.0f));
    assertTrue(inst.setValue("x3$0",6.0f));
    assertTrue(inst.setValue("y3$0",4.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing r$0,m$1,m$2
assertTrue(outputs.containsKey("r$0"));
assertTrue(outputs.containsKey("m$1"));
assertTrue(outputs.containsKey("m$2"));
float r$0 = outputs.get("r$0");
float m$x = outputs.get("m$1");
float m$y = outputs.get("m$2");
// check radius (should be equal to distance from m to p1,p2,p3)
assertEquals((float) Math.sqrt((5-m$x)*(5-m$x) + (2-m$y)*(2-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((3-m$x)*(3-m$x) + (9-m$y)*(9-m$y)),r$0,0.001);
assertEquals((float) Math.sqrt((6-m$x)*(6-m$x) + (4-m$y)*(4-m$y)),r$0,0.001);
// check number of outputs
assertEquals(3, outputs.size());

  }
  @Test
  public void testOutputCount0() {
    OutputCount inst = new OutputCount();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
assertEquals(32,outputs.size());
  }
  @Test
  public void testUnused0() {
    Unused inst = new Unused();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check number of outputs
assertEquals(32, outputs.size());

  }
  @Test
  public void testGPSNoVars0() {
    GPSNoVars inst = new GPSNoVars();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing all outputs
assertTrue(outputs.containsKey("rc1N$1"));
assertTrue(outputs.containsKey("rc1N$2"));
assertTrue(outputs.containsKey("rc1N$3"));
assertTrue(outputs.containsKey("rc2N$1"));
assertTrue(outputs.containsKey("rc2N$2"));
assertTrue(outputs.containsKey("rc2N$3"));
assertTrue(outputs.containsKey("z11$0"));
assertTrue(outputs.containsKey("z12$0"));
assertTrue(outputs.containsKey("z21$0"));
assertTrue(outputs.containsKey("z22$0"));
assertTrue(outputs.containsKey("z31$0"));
assertTrue(outputs.containsKey("z32$0"));
float rc1Nx = outputs.get("rc1N$1");
float rc1Ny = outputs.get("rc1N$2");
float rc1Nz = outputs.get("rc1N$3");
float rc2Nx = outputs.get("rc2N$1");
float rc2Ny = outputs.get("rc2N$2");
float rc2Nz = outputs.get("rc2N$3");
float z11 = outputs.get("z11$0");
float z12 = outputs.get("z12$0");
float z21 = outputs.get("z21$0");
float z22 = outputs.get("z22$0");
float z31 = outputs.get("z31$0");
float z32 = outputs.get("z32$0");
assertEquals(0,z11,0.001);
assertEquals(0,z12,0.001);
assertEquals(0,z21,0.001);
assertEquals(0,z22,0.001);
assertEquals(0,z31,0.001);
assertEquals(0,z32,0.001);
// check number of outputs
assertEquals(12, outputs.size());

  }
  @Test
  public void testGPSOnlyVars0() {
    GPSOnlyVars inst = new GPSOnlyVars();
    assertTrue(inst.setValue("sat1x$0",1.0f));
    assertTrue(inst.setValue("sat1y$0",1.0f));
    assertTrue(inst.setValue("sat1z$0",1.0f));
    assertTrue(inst.setValue("sat2x$0",0.0f));
    assertTrue(inst.setValue("sat2y$0",0.0f));
    assertTrue(inst.setValue("sat2z$0",1.0f));
    assertTrue(inst.setValue("sat3x$0",0.0f));
    assertTrue(inst.setValue("sat3y$0",1.0f));
    assertTrue(inst.setValue("sat3z$0",0.0f));
    assertTrue(inst.setValue("d1$0",0.6f));
    assertTrue(inst.setValue("d2$0",0.7f));
    assertTrue(inst.setValue("d3$0",0.9f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing all outputs
assertTrue(outputs.containsKey("rc1N$1"));
assertTrue(outputs.containsKey("rc1N$2"));
assertTrue(outputs.containsKey("rc1N$3"));
assertTrue(outputs.containsKey("rc2N$1"));
assertTrue(outputs.containsKey("rc2N$2"));
assertTrue(outputs.containsKey("rc2N$3"));
assertTrue(outputs.containsKey("z11$0"));
assertTrue(outputs.containsKey("z12$0"));
assertTrue(outputs.containsKey("z21$0"));
assertTrue(outputs.containsKey("z22$0"));
assertTrue(outputs.containsKey("z31$0"));
assertTrue(outputs.containsKey("z32$0"));
float rc1Nx = outputs.get("rc1N$1");
float rc1Ny = outputs.get("rc1N$2");
float rc1Nz = outputs.get("rc1N$3");
float rc2Nx = outputs.get("rc2N$1");
float rc2Ny = outputs.get("rc2N$2");
float rc2Nz = outputs.get("rc2N$3");
float z11 = outputs.get("z11$0");
float z12 = outputs.get("z12$0");
float z21 = outputs.get("z21$0");
float z22 = outputs.get("z22$0");
float z31 = outputs.get("z31$0");
float z32 = outputs.get("z32$0");
assertEquals(0,z11,0.001);
assertEquals(0,z12,0.001);
assertEquals(0,z21,0.001);
assertEquals(0,z22,0.001);
assertEquals(0,z31,0.001);
assertEquals(0,z32,0.001);
// check number of outputs
assertEquals(12, outputs.size());

  }
  @Test
  public void testLinePointDistance0() {
    LinePointDistance inst = new LinePointDistance();
    assertTrue(inst.setValue("p1x$0",3.0f));
    assertTrue(inst.setValue("p1y$0",4.0f));
    assertTrue(inst.setValue("p1z$0",5.0f));
    assertTrue(inst.setValue("p2x$0",7.0f));
    assertTrue(inst.setValue("p2y$0",8.0f));
    assertTrue(inst.setValue("p2z$0",10.0f));
    assertTrue(inst.setValue("pTstx$0",3.0f));
    assertTrue(inst.setValue("pTsty$0",8.0f));
    assertTrue(inst.setValue("pTstz$0",10.0f));
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
assertTrue(outputs.containsKey("abstand$0"));
assertTrue(outputs.containsKey("nor$1"));
assertTrue(outputs.containsKey("nor$2"));
assertTrue(outputs.containsKey("nor$3"));
float abstand = outputs.get("abstand$0");
Vec3D nor = new Vec3D(outputs.get("nor$1"), outputs.get("nor$2"), outputs.get("nor$3"));
nor.normalize();
nor.scalarMultiplication(abstand);
Point3D pBase = nor.applyToPoint(new Point3D(3.0f,8.0f,10.0f));
//pBase must lie on plane and on line
//test if on line, assume that dx,dy,dz not zero
double tx = (pBase.x-3.0)/(4.0);
double ty = (pBase.y-4.0)/(4.0);
double tz = (pBase.z-5.0)/(5.0);
// since the normal is unique except of a sign:
if (Math.abs(tx-ty)>0.001 || Math.abs(tz-ty)>0.001) {
nor = new Vec3D(outputs.get("nor$1"), outputs.get("nor$2"), outputs.get("nor$3"));
nor.normalize();
nor.scalarMultiplication(-abstand);
pBase = nor.applyToPoint(new Point3D(3.0f,8.0f,10.0f));
//pBase must lie on plane and on line
//test if on line, assume that dx,dy,dz not zero
tx = (pBase.x-3.0)/(4.0);
ty = (pBase.y-4.0)/(4.0);
tz = (pBase.z-5.0)/(5.0);
}
assertEquals(tx,ty,0.001);
assertEquals(ty,tz,0.001);
//test if on plane
Vec3D r = new Vec3D(4.0f,4.0f,5.0f);
r.normalize();
Vec3D xmpTest = new Vec3D(pBase.x-3.0f,pBase.y-8.0f,pBase.z-10.0f);
float dp = xmpTest.dotProduct(r);
assertEquals(0,dp,0.001);

  }
}
