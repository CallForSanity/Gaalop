package de.gaalop.gapp;

/**
 * Represents a selector with a index and a sign
 * @author Christian Steinmetz
 */
public class Selector extends SelectorIndex {

    private byte sign;

    public Selector(int index, byte sign) {
        super(index);
        this.sign = sign;
    }

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

}
