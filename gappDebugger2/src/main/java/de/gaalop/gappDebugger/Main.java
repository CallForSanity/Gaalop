package de.gaalop.gappDebugger;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.antlr.runtime.RecognitionException;

/**
 * The main class for the Gapp debugger
 * @author christian
 */
public class Main {

    /**
     * @param args no args are used
     */
    public static void main(String[] args) throws RecognitionException, FileNotFoundException, IOException {
        UI ui = new UI();
        ui.controller = new Controller(ui);
        ui.setVisible(true);
    }


}
