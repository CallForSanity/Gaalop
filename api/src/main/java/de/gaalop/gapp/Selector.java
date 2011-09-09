package de.gaalop.gapp;

/**
 * Represents a selector with a index and a sign
 * @author Christian Steinmetz
 */
public class Selector {

    private int index;
    private byte sign;

    public Selector(int index, byte sign) {
        this.index = index;
        this.sign = sign;
    }

    public int getIndex() {
        return index;
    }

    public byte getSign() {
        return sign;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

}
