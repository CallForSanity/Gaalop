package de.gaalop.gappDebugger;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author christian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RecognitionException, FileNotFoundException, IOException {
        UI ui = new UI();
        ui.controller = new Controller(ui);
        ui.setVisible(true);
        //ui.controller.loadSource(new File("E:\\Frage\\frage.gapp.txt"));
        
        //ui.controller.setVariableValue("a1=3;a2=4;a3=5;b1=6;b2=7;b3=8");
        //ui.controller.repaint();
 
    }


}
