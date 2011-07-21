package de.gaalop.tba.generatedTests;

import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class TBATest {
  @Test
  public void testGPSNoVars0() {
    GPSNoVars inst = new GPSNoVars();
    inst.calculate();
    // collect outputs
    HashMap<String,Float> outputs = inst.getValues();
    // check outputs
// check containing all outputs
assertTrue(outputs.containsKey("rc1N_1"));
assertTrue(outputs.containsKey("rc1N_2"));
assertTrue(outputs.containsKey("rc1N_3"));
assertTrue(outputs.containsKey("rc2N_1"));
assertTrue(outputs.containsKey("rc2N_2"));
assertTrue(outputs.containsKey("rc2N_3"));
assertTrue(outputs.containsKey("z11_0"));
assertTrue(outputs.containsKey("z12_0"));
assertTrue(outputs.containsKey("z21_0"));
assertTrue(outputs.containsKey("z22_0"));
assertTrue(outputs.containsKey("z31_0"));
assertTrue(outputs.containsKey("z32_0"));
float rc1Nx = outputs.get("rc1N_1");
float rc1Ny = outputs.get("rc1N_2");
float rc1Nz = outputs.get("rc1N_3");
float rc2Nx = outputs.get("rc2N_1");
float rc2Ny = outputs.get("rc2N_2");
float rc2Nz = outputs.get("rc2N_3");
float z11 = outputs.get("z11_0");
float z12 = outputs.get("z12_0");
float z21 = outputs.get("z21_0");
float z22 = outputs.get("z22_0");
float z31 = outputs.get("z31_0");
float z32 = outputs.get("z32_0");
assertEquals(0,z11,0.001);
assertEquals(0,z12,0.001);
assertEquals(0,z21,0.001);
assertEquals(0,z22,0.001);
assertEquals(0,z31,0.001);
assertEquals(0,z32,0.001);
// check number of outputs
assertEquals(12, outputs.size());

  }
}
