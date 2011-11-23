package de.gaalop.productcomputer2;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class Algebra5d extends Algebra {

    @Override
    public void create() {
        base = new String[]{"e1","e2","e3","einf","e0"};
        base2 = new String[]{"e1","e2","e3","ep","em"};
        baseSquaresStr = new HashMap<String, Integer>();
        baseSquaresStr.put("e1", 1);
        baseSquaresStr.put("e2", 1);
        baseSquaresStr.put("e3", 1);
        baseSquaresStr.put("ep", 1);
        baseSquaresStr.put("em", -1);

        mapToPlusMinus = new HashMap<String, LinkedList<BladeStr>>();

        //e0(em,ep)
        LinkedList<BladeStr> listE0 = new LinkedList<BladeStr>();
        listE0.add(new BladeStr(0.5f, "em"));
        listE0.add(new BladeStr(-0.5f, "ep"));
        mapToPlusMinus.put("e0", listE0);
        //einf(em,ep)
        LinkedList<BladeStr> listEinf = new LinkedList<BladeStr>();
        listEinf.add(new BladeStr("em"));
        listEinf.add(new BladeStr("ep"));
        mapToPlusMinus.put("einf", listEinf);

        mapToZeroInf = new HashMap<String, LinkedList<BladeStr>>();

        //em(einf,e0)
        LinkedList<BladeStr> listEm = new LinkedList<BladeStr>();
        listEm.add(new BladeStr(0.5f, "einf"));
        listEm.add(new BladeStr("e0"));
        mapToZeroInf.put("em", listEm);
        //ep(einf,e0)
        LinkedList<BladeStr> listEp = new LinkedList<BladeStr>();
        listEp.add(new BladeStr(0.5f, "einf"));
        listEp.add(new BladeStr(-1, "e0"));
        mapToZeroInf.put("ep", listEp);

    }

}
