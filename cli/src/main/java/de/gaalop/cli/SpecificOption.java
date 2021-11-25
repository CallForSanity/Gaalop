package de.gaalop.cli;

import java.util.LinkedList;

/**
 * Represents an option value assignment in the Commandline spec option.
 * @author Christian Steinmetz
 */
class SpecificOption {

    public String classname;
    public String optionname;
    public String value;

    public SpecificOption(String classname, String optionname, String value) {
        this.classname = classname;
        this.optionname = optionname;
        this.value = value;
    }
    
    /**
     * Parses the Commandline spec option into a list of specific options.
     * @param str The commandline string. The format ia a comma-separated list of {Plugin1-Classname}:{Plugin1-Optionname}={Plugin1-Value} items.
     * @return The list of specific options.
     */
    public static LinkedList<SpecificOption> parseSpecificOptions(String str) {
        LinkedList<SpecificOption> options = new LinkedList<>();
        if (str.trim().isEmpty()) 
            return options;
        for (String singleOption: str.split(",")) {
            String[] parts = singleOption.trim().split(":");
            String[] optionValue = parts[1].split("=");
            options.add(new SpecificOption(parts[0].trim(), optionValue[0].trim(), optionValue[1].trim()));
        }
        return options;
    }
}
