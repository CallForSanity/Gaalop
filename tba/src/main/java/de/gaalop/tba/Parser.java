package de.gaalop.tba;

import java.util.Vector;

/**
 *
 * @author christian
 */
public class Parser {

    public static BladeRef parseBladeRef(String readed) {

        byte prefactor = 1;
        int index = 0;

        String trimmed = readed.trim();
        if (trimmed.isEmpty() || trimmed.equals("0"))
            prefactor = 0;
        else {

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

    public static Blade parseBlade(String readed, Algebra algebra) {
        Vector<String> bases = new Vector<String>();
        String[] parts = readed.split("\\^");
        for (String part: parts)
            bases.add(part.trim());
        return new Blade(algebra, bases);
    }

    public static Multivector parseMultivector(String parse, Algebra algebra) {
        String trimmed = parse.trim();
        Multivector result = new Multivector(algebra);

        while (!trimmed.isEmpty()) {
            int index = -1;
            int indexPlus = -1;
            int indexMinus = -1;

            if (trimmed.charAt(0) == '-') {
                indexPlus = trimmed.indexOf('+',1);
                indexMinus =  trimmed.indexOf('-',1);
            } else {
                indexPlus = trimmed.indexOf('+');
                indexMinus =  trimmed.indexOf('-');
            }

            if (indexPlus == -1)
                index = (indexMinus == -1) ? -1 : indexMinus;
            else
                index = (indexMinus == -1) ? indexPlus : Math.min(indexMinus,indexPlus);


            if (index == -1) {
                // one blade only
                result.addBlade(parseBladeRef(trimmed));
                trimmed = "";
            } else {
                // more blades
                result.addBlade(parseBladeRef(trimmed.substring(0, index)));

                if (trimmed.charAt(index) == '+')
                    trimmed = trimmed.substring(index+1);
                else
                    trimmed = trimmed.substring(index);
            }
        }

        return result;
    }
    
}
