package de.gaalop.productComputer;

import de.gaalop.cfg.AlgebraDefinitionFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class AlgebraPC {
    
    public String[] base;
    public String[] base2;
    public HashMap<String, Byte> baseSquaresStr;
    public HashMap<String, LinkedList<BladeStr>> mapToPlusMinus;
    public HashMap<String, LinkedList<BladeStr>> mapToZeroInf;

    public AlgebraPC(AlgebraDefinitionFile alFile) {
        base = Arrays.copyOfRange(alFile.base, 1, alFile.base.length);
        base2 = Arrays.copyOfRange(alFile.base2, 1, alFile.base2.length);
        baseSquaresStr = alFile.baseSquares;
        mapToPlusMinus = parseMapStringListBladeStr(alFile.lineMapZeroInfToPlusMinus);
        mapToZeroInf = parseMapStringListBladeStr(alFile.lineMapPlusMinusToZeroInf);
    }

    /**
     * Parses a map <string, LinkedList<BladeStr>>
     * @param str The string to be parsed
     * @return The parsed map
     */
    private HashMap<String, LinkedList<BladeStr>> parseMapStringListBladeStr(String str) {
        str = str.trim();
        HashMap<String, LinkedList<BladeStr>> result = new HashMap<String, LinkedList<BladeStr>>();
        if (str.isEmpty()) {
            return result;
        }
        String[] parts = str.split(",");
        for (String part : parts) {
            String[] parts2 = part.split("=");
            result.put(parts2[0], parseListBladeStr(parts2[1]));
        }
        return result;
    }

    /**
     * Parses a list of bladeStr
     * @param str The string to be parsed
     * @return The parsed list of bladeStr
     */
    private LinkedList<BladeStr> parseListBladeStr(String str) {
        int index;
        LinkedList<BladeStr> result = new LinkedList<BladeStr>();
        while ((index = suchenextIndex(str)) != Integer.MAX_VALUE) {
            result.add(parseBladeStr(str.substring(0, index)));
            if (str.charAt(index) == '-') {
                str = str.substring(index);
            } else {
                str = str.substring(index + 1);
            }
        }
        if (!str.isEmpty()) {
            result.add(parseBladeStr(str));
        }
        return result;
    }

    /**
     * Returns the index from the next '+' or '-' character
     * @param str The string to be parsed
     * @return The index
     */
    private int suchenextIndex(String str) {
        int indexPlus = str.indexOf('+');
        int indexMinus = str.indexOf('-', 1);
        if (indexPlus == -1) {
            indexPlus = Integer.MAX_VALUE;
        }
        if (indexMinus == -1) {
            indexMinus = Integer.MAX_VALUE;
        }
        return Math.min(indexPlus, indexMinus);
    }

    /**
     * Parses a bladeStr
     * @param str The string to be parsed
     * @return The parsed BladeStr
     */
    private BladeStr parseBladeStr(String str) {
        // Contains only Product, Blade or Constant!
        if (str.contains("*")) {
            return parseProduct(str);
        } else if (str.contains("e")) {
            return parseBlade(str);
        } else {
            return new BladeStr(Float.parseFloat(str.trim()), "0");
        }
    }

    /**
     * Parses a Blade
     * @param str The string to be parsed
     * @return The parsed Blade
     */
    private BladeStr parseBlade(String str) {
        boolean negated = false;
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            negated = true;
        }
        return new BladeStr((negated ? -1 : 1),new String[]{str});
    }

    /**
     * Parses a Product
     * @param str The string to be parsed
     * @return The parsed Product
     */
    private BladeStr parseProduct(String str) {
        String[] parts = str.split("\\*");


        if (parts[0].contains("e")) {
            //parts[0]: Blade
            //parts[1]: Constant
            BladeStr result = parseBlade(parts[0]);
            result.setPrefactor(result.getPrefactor()*Float.parseFloat(parts[1].trim()));
            return result;
        } else {
            //parts[0]: Constant
            //parts[1]: Blade
            BladeStr result = parseBlade(parts[1]);
            result.setPrefactor(result.getPrefactor()*Float.parseFloat(parts[0].trim()));
            return result;
        }
    }
}
