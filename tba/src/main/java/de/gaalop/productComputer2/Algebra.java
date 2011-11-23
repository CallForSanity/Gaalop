package de.gaalop.productComputer2;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public abstract class Algebra {
    
    public abstract void create();

    public String[] base;
    public String[] base2;
    public HashMap<String,Integer> baseSquaresStr;
    public HashMap<String, LinkedList<BladeStr>> mapToPlusMinus;
    public HashMap<String, LinkedList<BladeStr>> mapToZeroInf;

}
