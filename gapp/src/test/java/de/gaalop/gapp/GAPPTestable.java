package de.gaalop.gapp;

import de.gaalop.gapp.executer.Executer;
import java.util.HashMap;

/**
 *
 * @author christian
 */
public interface GAPPTestable {

    public String getSource();

    public HashMap<String, Float> getInputs();

    public void testOutput(Executer executer);

}
