/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.productComputer2;

/**
 *
 * @author christian
 */
public class Blade extends BitSet {

    public Blade(int bitCount) {
        super(bitCount);
    }

    public Blade(int bitCount, BitSet blade) {
        super(bitCount);
        or(blade);
    }





}
