package de.gaalop.gapp;

import java.util.Vector;

public class Selectorset {

    private Vector<Integer> selectors;

    public Selectorset() {
        selectors = new Vector<Integer>();
    }

    public Selectorset(Vector<Integer> selectors) {
        this.selectors = selectors;
    }

    public int size() {
        return selectors.size();
    }

    public int get(int index) {
        return selectors.get(index);
    }

    public void add(int selector) {
        selectors.add(selector);
    }

    public Vector<Integer> getSelectors() {
        return selectors;
    }
}
