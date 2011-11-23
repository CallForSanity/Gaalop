package de.gaalop.productComputer2;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class Algebra9d extends Algebra {

@Override
    public void create() {
        base = new String[]{"e1","e2","e3","einfx","einfy","einfz","e0x","e0y","e0z"};
        base2 = new String[]{"e1","e2","e3","e4","e5","e6","e7","e8","e9"};
        baseSquaresStr = new HashMap<String, Integer>();
        baseSquaresStr.put("e1", 1);
        baseSquaresStr.put("e2", 1);
        baseSquaresStr.put("e3", 1);
        baseSquaresStr.put("e4", 1);
        baseSquaresStr.put("e5", 1);
        baseSquaresStr.put("e6", 1);
        baseSquaresStr.put("e7", -1);
        baseSquaresStr.put("e8", -1);
        baseSquaresStr.put("e9", -1);
        mapToPlusMinus = new HashMap<String, LinkedList<BladeStr>>();

        //e0x=0.5*e7-0.5*e4,
        //e0y=0.5*e8-0.5*e5,
        //e0z=0.5*e9-0.5*e6,
        LinkedList<BladeStr> listE0x = new LinkedList<BladeStr>();
        listE0x.add(new BladeStr(0.5f, "e7"));
        listE0x.add(new BladeStr(-0.5f, "e4"));
        mapToPlusMinus.put("e0x", listE0x);
        //einf(em,ep)
        LinkedList<BladeStr> listE0y = new LinkedList<BladeStr>();
        listE0y.add(new BladeStr(0.5f, "e8"));
        listE0y.add(new BladeStr(-0.5f, "e5"));
        mapToPlusMinus.put("e0y", listE0y);

        LinkedList<BladeStr> listE0z = new LinkedList<BladeStr>();
        listE0z.add(new BladeStr(0.5f, "e9"));
        listE0z.add(new BladeStr(-0.5f, "e6"));
        mapToPlusMinus.put("e0z", listE0z);

        //einfx=e4+e7,
        //einfy=e5+e8,
        //einfz=e6+e9
        LinkedList<BladeStr> listEinfx = new LinkedList<BladeStr>();
        listEinfx.add(new BladeStr("e4"));
        listEinfx.add(new BladeStr("e7"));
        mapToPlusMinus.put("einfx", listEinfx);

        LinkedList<BladeStr> listEinfy = new LinkedList<BladeStr>();
        listEinfy.add(new BladeStr("e5"));
        listEinfy.add(new BladeStr("e8"));
        mapToPlusMinus.put("einfy", listEinfy);

        LinkedList<BladeStr> listEinfz = new LinkedList<BladeStr>();
        listEinfz.add(new BladeStr("e6"));
        listEinfz.add(new BladeStr("e9"));
        mapToPlusMinus.put("einfz", listEinfz);

        mapToZeroInf = new HashMap<String, LinkedList<BladeStr>>();

        //e4=0.5*einfx-e0x,
        //e5=0.5*einfy-e0y,
        //e6=0.5*einfz-e0z,
        LinkedList<BladeStr> listE4 = new LinkedList<BladeStr>();
        listE4.add(new BladeStr(0.5f, "einfx"));
        listE4.add(new BladeStr(-1,"e0x"));
        mapToZeroInf.put("e4", listE4);

        LinkedList<BladeStr> listE5 = new LinkedList<BladeStr>();
        listE5.add(new BladeStr(0.5f, "einfy"));
        listE5.add(new BladeStr(-1,"e0y"));
        mapToZeroInf.put("e5", listE5);

        LinkedList<BladeStr> listE6 = new LinkedList<BladeStr>();
        listE6.add(new BladeStr(0.5f, "einfz"));
        listE6.add(new BladeStr(-1,"e0z"));
        mapToZeroInf.put("e6", listE6);

        //e7=0.5*einfx+e0x,
        //e8=0.5*einfy+e0y,
        //e9=0.5*einfz+e0z
        LinkedList<BladeStr> listE7 = new LinkedList<BladeStr>();
        listE7.add(new BladeStr(0.5f, "einfx"));
        listE7.add(new BladeStr("e0x"));
        mapToZeroInf.put("e7", listE7);
        
        LinkedList<BladeStr> listE8 = new LinkedList<BladeStr>();
        listE8.add(new BladeStr(0.5f, "einfy"));
        listE8.add(new BladeStr("e0y"));
        mapToZeroInf.put("e8", listE8);

        LinkedList<BladeStr> listE9 = new LinkedList<BladeStr>();
        listE9.add(new BladeStr(0.5f, "einfz"));
        listE9.add(new BladeStr("e0z"));
        mapToZeroInf.put("e9", listE9);
    }

}
