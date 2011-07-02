package de.gaalop.tba;

import java.util.LinkedList;

/**
 *
 * @author christian
 */
public interface GenericTestable {

    public String getCLUScript();

    public LinkedList<InputOutput> getInputOutputs();

    public LinkedList<String> getOutputs();

}
