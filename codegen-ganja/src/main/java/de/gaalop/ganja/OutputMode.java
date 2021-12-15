package de.gaalop.ganja;

/**
 *
 * @author CSteinmetz15
 */
public enum OutputMode {
    COFFEESHOP,
    GAALOPWEB,
    JUPYTER_NOTEBOOK,
    STANDALONE_PAGE;
    
    public static OutputMode fromString(String modeStr) {
        try {
            return OutputMode.valueOf(modeStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return OutputMode.STANDALONE_PAGE;
        }
    }
}
