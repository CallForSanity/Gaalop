package de.gaalop.productComputer;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines an algebra
 * @author Christian Steinmetz
 */
public class AlgebraDefinitionTC extends AlgebraDefinitionFile {

    /**
     * The map to transform from the plusminus base to the zeroinf base
     */
    public HashMap<String, TCSum> mapPlusMinusToZeroInf = new HashMap<String, TCSum>();
   
    /**
     * The map to transform from the zeroinf base to the plusminus base
     */
    public HashMap<String, TCSum> mapZeroInfToPlusMinus = new HashMap<String, TCSum>();

    public AlgebraDefinitionTC(AlgebraDefinitionFile adFile) {
        base = adFile.base;
        base2 = adFile.base2;
        baseSquares = adFile.baseSquares;
        indices = adFile.indices;
        lineMapPlusMinusToZeroInf = adFile.lineMapPlusMinusToZeroInf;
        lineMapZeroInfToPlusMinus = adFile.lineMapZeroInfToPlusMinus;
        mapPlusMinusToZeroInf = parseMapStrTCSum(adFile.lineMapPlusMinusToZeroInf);
        mapZeroInfToPlusMinus = parseMapStrTCSum(adFile.lineMapZeroInfToPlusMinus);
    }

    /**
     * Parses a map<String, TCSum>
     * @param The string to be parsed
     */
    private HashMap<String, TCSum> parseMapStrTCSum(String str) {
        str = str.trim();
        HashMap<String, TCSum> result = new HashMap<String, TCSum>();
        if (str.isEmpty()) {
            return result;
        }
        String[] parts = str.split(",");
        for (String part : parts) {
            String[] parts2 = part.split("=");
            result.put(parts2[0], parseTCSum(parts2[1]));
        }
        return result;
    }

    /**
     * Parses a map<String, Byte>
     * @param The string to be parsed
     */
    private HashMap<String, Byte> parseMapStrByte(String str) {
        str = str.replaceAll(" ", "");
        HashMap<String, Byte> result = new HashMap<String, Byte>();
        if (str.isEmpty()) {
            return result;
        }
        String[] parts = str.split(",");
        for (String part : parts) {
            String[] parts2 = part.split("=");
            result.put(parts2[0], (byte) Integer.parseInt(parts2[1]));
        }
        return result;
    }

    /**
     * Returns the index from the next '+' or '-' character
     * @param The string to be parsed
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
     * Parses a TCSum
     * @param The string to be parsed
     * @return The parsed TCSum
     */
    private TCSum parseTCSum(String str) {
        int index;
        LinkedList<TCExpression> list = new LinkedList<TCExpression>();
        while ((index = suchenextIndex(str)) != Integer.MAX_VALUE) {
            list.add(parseTCExpression(str.substring(0, index)));
            if (str.charAt(index) == '-') {
                str = str.substring(index);
            } else {
                str = str.substring(index + 1);
            }
        }
        if (!str.isEmpty()) {
            list.add(parseTCExpression(str));
        }
        return new TCSum(list.toArray(new TCExpression[0]));
    }

    /**
     * Parses a TCExpression
     * @param The string to be parsed
     * @return The parsed TCExpression
     */
    private TCExpression parseTCExpression(String str) {
        // Contains only TCProduct, TCBlade or TCConstant!
        if (str.contains("*")) {
            return parseTCProduct(str);
        } else if (str.contains("e")) {
            return parseTCBlade(str);
        } else {
            return parseTCConstant(str);
        }
    }

    /**
     * Parses a TCConstant
     * @param The string to be parsed
     * @return The parsed TCConstant
     */
    private TCConstant parseTCConstant(String str) {
        return new TCConstant(Float.parseFloat(str));
    }

    /**
     * Parses a TCBlade
     * @param The string to be parsed
     * @return The parsed TCBlade
     */
    private TCBlade parseTCBlade(String str) {
        boolean negated = false;
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            negated = true;
        }
        return new TCBlade(new String[]{str}, negated);
    }

    /**
     * Parses a TCProduct
     * @param The string to be parsed
     * @return The parsed TCProduct
     */
    private TCProduct parseTCProduct(String str) {
        String[] parts = str.split("\\*");
        TCExpression[] expr = new TCExpression[parts.length];
        for (int i = 0; i < parts.length; i++) 
            expr[i] = parseTCExpression(parts[i]);
        return new TCProduct(expr);
    }
}
