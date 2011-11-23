package de.gaalop.productcomputer2;

import java.util.HashMap;

/**
 * Defines a doubled Hashmap
 * @author christian
 */
public class DoubledHashMap<K,V> {

    private HashMap<K,V> keyToValue = new HashMap<K, V>();
    private HashMap<V,K> valueToKey = new HashMap<V, K>();

    public void clear() {
        keyToValue.clear();
        valueToKey.clear();
    }

    public void setAssociation(K key, V value) {
        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public boolean containsValue(V value) {
        return valueToKey.containsKey(value);
    }

    public boolean containsKey(K key) {
        return keyToValue.containsKey(key);
    }
}
