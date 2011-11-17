package de.gaalop;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator {

    private Object array[];
    private int pos = 0;

    public ArrayIterator(Object anArray[]) {
        array = anArray;
    }

    public boolean hasNext() {
        return pos < array.length;
    }

    public Object next() throws NoSuchElementException {
        if (hasNext()) {
            return array[pos++];
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
