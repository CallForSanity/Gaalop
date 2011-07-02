/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public abstract class InputOutput {

    public abstract LinkedList<VariableValue> getInputs();

    public abstract String getCheckOutputsCode();

    public abstract int getNo();

}
