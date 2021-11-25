package de.gaalop.tba.table;

import de.gaalop.tba.Blade;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import java.util.Vector;

/**
 * Provides methods for parsing blades, blades references and multivectors
 * @author Christian Steinmetz
 */
public class Parser {

    /**
     * Parses a blade refrence from a string
     * @param parse The string to parse
     * @return The parsed blade reference
     */
    public static BladeRef parseBladeRef(String parse) {

        byte prefactor = 1;
        int index = 0;

        String trimmed = parse.trim();
        if (trimmed.isEmpty() || trimmed.equals("0")) {
            prefactor = 0;
        } else {

            if (trimmed.startsWith("-E")) {
                // for instance -E10
                prefactor = -1;
                index = Integer.parseInt(trimmed.substring(2));
            } else {
                if (trimmed.startsWith("E")) {
                    // for instance E10
                    index = Integer.parseInt(trimmed.substring(1));
                } else {
                    // for instance -1E10
                    String[] parts = trimmed.split("E");
                    prefactor = (byte) Integer.parseInt(parts[0]);
                    index = Integer.parseInt(parts[1]);
                }
            }
        }

        return new BladeRef(prefactor, index);
    }

    /**
     * Parses a blade from a string
     * @param parse The string to parse
     * @param algebra The current algebra
     * @return The parsed blade
     */
    public static Blade parseBlade(String parse) {
        Vector<String> bases = new Vector<String>();
        String[] parts = parse.split("\\^");
        for (String part : parts) {
            bases.add(part.trim());
        }
        return new Blade(bases);
    }

    /**
     * Parses a multivector from a string
     * @param readed The string to parse
     * @param algebra The current algebra
     * @return The parsed blade
     */
    public static Multivector parseMultivector(String parse) {
        String trimmed = parse.trim();
        Multivector result = new Multivector();

        while (!trimmed.isEmpty()) {
            int index = -1;
            int indexPlus = -1;
            int indexMinus = -1;

            if (trimmed.charAt(0) == '-') {
                indexPlus = trimmed.indexOf('+', 1);
                indexMinus = trimmed.indexOf('-', 1);
            } else {
                indexPlus = trimmed.indexOf('+');
                indexMinus = trimmed.indexOf('-');
            }

            if (indexPlus == -1) {
                index = (indexMinus == -1) ? -1 : indexMinus;
            } else {
                index = (indexMinus == -1) ? indexPlus : Math.min(indexMinus, indexPlus);
            }


            if (index == -1) {
                // one blade only
                result.addBlade(parseBladeRef(trimmed));
                trimmed = "";
            } else {
                // more blades
                result.addBlade(parseBladeRef(trimmed.substring(0, index)));

                if (trimmed.charAt(index) == '+') {
                    trimmed = trimmed.substring(index + 1);
                } else {
                    trimmed = trimmed.substring(index);
                }
            }
        }

        return result;
    }
}
