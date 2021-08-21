package de.gaalop;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Represents a string list which may produce delimited output of its elements
 * @author Christian Steinmetz, 2020
 */
public class StringList extends LinkedList<String> {

    public StringList() {
    }
    
    public StringList(Collection<String> c) {
        super(c);
    }
    
    /**
     * Returns a new {@code String} composed of copies of the
     * elements in @this joined together with a copy of ", ".
     *
     * @return a new {@code String} that is composed from @this
     **/
    public String join() {
        return join(", ");
    }
    
    /**
     * Returns a new {@code String} composed of copies of the
     * elements in @this joined together with a copy of the
     * specified {@code delimiter}.
     * 
     * @param  delimiter a sequence of characters that is used to separate each
     *         of elements in @this in the resulting {@code String}
     *
     * @return a new {@code String} that is composed from @this
     **/
    public String join(String delimiter) {
        return String.join(delimiter, this);
    }

    @Override
    public String toString() {
        return join();
    }
    
    private static final Comparator<String> ignoredCaseComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    };
    
    /**
     * Sorts this list alphabetically with ignoring the case
     * @return @this pointer
     */
    public StringList sortIgnoringCase() {
        Collections.sort(this, ignoredCaseComparator);
        return this;
    }
}
