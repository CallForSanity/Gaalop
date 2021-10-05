package de.gaalop.cfg;

import de.gaalop.dfg.Expression;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

/**
 * Defines an algebra
 * @author Christian Steinmetz
 */
public class AlgebraDefinitionFile {

    /**
     * The zeroinf base
     */
    public String[] base;
    /**
     * The line contating the map to transform from the plusminus base to the zeroinf base
     */
    public String lineMapPlusMinusToZeroInf;
    /**
     * The plusminus base
     */
    public String[] base2;
    /**
     * The squares of the plusminus base
     */
    public HashMap<String, Byte> baseSquares = new HashMap<String, Byte>();
    /**
     * The line contating the map to transform from the zeroinf base to the plusminus base
     */
    public String lineMapZeroInfToPlusMinus;


    //generated attributes
    public Expression[] blades;

    private boolean usePrecalculatedTable;
    private String productsFilePath;
    private boolean useAsRessource;

    /**
     * The indices of the base vectors
     */
    public HashMap<String, Integer> indices = new HashMap<String, Integer>();

    /**
     * Returns the string of blade with a given index
     * @param index The index
     * @return The string representing the blade
     */
    public String getBladeString(int index) {
        return blades[index].toString();
    }

    /**
     * Retruns the number of blades
     * @return The number of blades
     */
    public int getBladeCount() {
        return blades.length;
    }

    /**
     * Retruns the expression of a blade with a given index
     * @param index The index
     * @return The expression
     */
    public Expression getBladeExpression(int index) {
        return blades[index];
    }

    /**
     * Returns the index of a base vector
     * @param baseVectorString The string, representing the base vector
     * @return The index
     */
    public int getIndex(String baseVectorString) {
        return getIndexInArray(baseVectorString, base);
    }

    /**
     * Determines the index of a given string in a given string array
     * @param str The string to search
     * @param arr The array to search
     * @return The index, -1 if the array does not contain the string
     */
    private int getIndexInArray(String str, String[] arr) {
        for (int i=0;i<arr.length;i++)
            if (arr[i].equals(str))
                return i;
        return -1;
    }

    public boolean isUsePrecalculatedTable() {
        return usePrecalculatedTable;
    }

    public void setUsePrecalculatedTable(boolean usePrecalculatedTable) {
        this.usePrecalculatedTable = usePrecalculatedTable;
    }

    public boolean isUseAsRessource() {
        return useAsRessource;
    }

    public void setUseAsRessource(boolean useAsRessource) {
        this.useAsRessource = useAsRessource;
    }

    /**
     * Loads a algebra definition from a Reader
     * @param reader The reader to be used
     */
    public void loadFromFile(Reader reader) throws IOException {
        BufferedReader d = new BufferedReader(reader);
        base = parseStrArray(d.readLine());
        lineMapPlusMinusToZeroInf = d.readLine();
        base2 = parseStrArray(d.readLine());
        baseSquares = parseMapStrByte(d.readLine());
        lineMapZeroInfToPlusMinus = d.readLine();
        d.close();
        createIndices();
    }

    /**
     * Parses a String array, splitet with commas
     * @param The string to be parsed
     */
    private String[] parseStrArray(String str) {
        return str.replaceAll(" ", "").split(",");
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
     * Creates indices from the two bases
     */
    public void createIndices() {
        indices.clear();
        for (int i = 1; i < base.length; i++) {
            indices.put(base[i], i);
        }
        for (int i = 1; i < base2.length; i++) {
            indices.put(base2[i], i);
        }
    }

    public String getProductsFilePath() {
        return productsFilePath;
    }

    public void setProductsFilePath(String productsFilePath) {
        this.productsFilePath = productsFilePath;
    }


}
