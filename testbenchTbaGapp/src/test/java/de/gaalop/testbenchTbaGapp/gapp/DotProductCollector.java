package de.gaalop.testbenchTbaGapp.gapp;

import de.gaalop.gapp.executer.Executer;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class DotProductCollector implements GAPPTestable {

    @Override
    public String getSource() {
        return 
                "s1 = *SphereN3(x1,y1,0,L1);\n"+
                "s2 = *SphereN3(x2,y2,0,L2);\n"+
                "s3 = *SphereN3(x3,y3,0,L3);\n"+
                "?Pp = *(s1^s2^s3);\n";
    }

    @Override
    public HashMap<String, Double> getInputs() {
        HashMap<String, Double> hashMap = new HashMap<String, Double>();
        hashMap.put("x1", 0.0d);
        hashMap.put("y1", 0.0d);
        hashMap.put("L1", 0.0d);
        hashMap.put("x2", 0.0d);
        hashMap.put("y2", 0.0d);
        hashMap.put("L2", 0.0d);
        hashMap.put("x3", 0.0d);
        hashMap.put("y3", 0.0d);
        hashMap.put("L3", 0.0d);
        return hashMap;
    }

    @Override
    public void testOutput(Executer executer) {
        
    }

}
