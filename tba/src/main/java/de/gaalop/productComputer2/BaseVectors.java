package de.gaalop.productComputer2;

/**
 * Defines all basevectors
 * @author christian
 */
public class BaseVectors {

    private int cur = 0;
    private DoubledHashMap<String, Integer> bases = new DoubledHashMap<String, Integer>();

    public int addBase(String base) {
        bases.setAssociation(base, cur);
        return cur++;
    }

    public String getBaseString(Integer index) {
        return bases.getKey(index);
    }

    public Integer getIndex(String base) {
        return bases.getValue(base);
    }

    public boolean containsBase(String base) {
        return bases.containsKey(base);
    }

    public boolean containsIndex(Integer index) {
        return bases.containsValue(index);
    }
}
