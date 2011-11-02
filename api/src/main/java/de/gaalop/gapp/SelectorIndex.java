package de.gaalop.gapp;

/**
 * Represents an index of a selector
 * @author Christian Steinmetz
 */
public class SelectorIndex {

    private int index;
    private String bladeName; //(pc/chs) only to use from gcd

    public SelectorIndex(int index, String bladeName) {
        this.index = index;
        this.bladeName = bladeName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBladeName() {
        return bladeName;
    }

    public void setBladeName(String bladeName) {
        this.bladeName = bladeName;
    }

}
