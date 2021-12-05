package datapath.graph;

import datapath.graph.operations.Operation;
import datapath.graph.operations.Dummy;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jens
 */
public class Mapping<E> {

    private HashMap<E, Operation> map;
    private HashMap<E, Dummy> dummys;
    private HashMap<Dummy, HashSet<E>> dummyKeys;

    public Mapping() {
        map = new HashMap<E, Operation>();
        dummys = new HashMap<E, Dummy>();
        dummyKeys = new HashMap<Dummy, HashSet<E>>();
    }

    public void addToMap(E key, Operation tree) {
        assert key != null : "no declaration";
        assert tree != null : "no operation";
        assert !map.containsKey(key) : "new value for declaration "
                + key + " -> " + map.get(key) + " new: " + tree;

        HashSet<E> removedDummyKeys = null;

        if (dummys.containsKey(key)) {
            Dummy dummy = dummys.get(key);
            dummy.replaceWith(tree);
            //dummys.remove(key);
            removedDummyKeys = removeDummy(key);
        }
        if (tree instanceof Dummy) {
            addDummy(key, (Dummy) tree);
        } else {
            if(removedDummyKeys != null){
                for(E k : removedDummyKeys)
                    map.put(k, tree);
            }
            map.put(key, tree);
        }
    }

    public Operation getFromMap(E decl) {
        Operation ret = map.get(decl);
        if (ret == null) {
            ret = dummys.get(decl);
            if (ret == null) {
                ret = new Dummy(null);
                ret.setDebugMessage(decl.toString());
                addDummy(decl, (Dummy) ret);
            }
        }
        return ret;
    }

    public Set<E> keySet() {
        return map.keySet();
    }

    public Set<E> dummyKeySet() {
        return dummys.keySet();
    }

    public Collection<Dummy> getDummys() {
        return dummys.values();
    }

    private void addDummy(E key, Dummy dummy) {
        assert !dummys.containsKey(key);
        dummys.put(key, dummy);
        HashSet<E> keys = dummyKeys.get(dummy);
        if (keys == null) {
            keys = new HashSet<E>();
            dummyKeys.put(dummy, keys);
        }
        keys.add(key);
    }

    private HashSet<E> removeDummy(E key) {
        assert dummys.containsKey(key);
        Dummy dummy = dummys.remove(key);
        HashSet<E> keys = dummyKeys.get(dummy);
        assert keys != null;
        for(E k : keys){
            dummys.remove(k);
        }

        return dummyKeys.remove(dummy);
    }
}
