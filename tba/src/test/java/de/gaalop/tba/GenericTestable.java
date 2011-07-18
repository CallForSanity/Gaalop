package de.gaalop.tba;

import java.util.LinkedList;

/**
 * Defines an interface for a test
 * @author christian
 */
public interface GenericTestable {

    public String getCLUScript();

    public LinkedList<InputOutput> getInputOutputs();

}
