package de.gaalop.tba.cfgImport.optimization.maxima;

/**
 * Stores the maxima input and it's according output
 * @author Christian Steinmetz
 */
public class MaximaInOut {

    private String input;
    private String output;

    public MaximaInOut(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return input+" -> "+output;
    }

    

}
