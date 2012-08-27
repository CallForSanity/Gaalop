package de.gaalop.gui;

/**
 *
 * @author Christian Steinmetz
 */
public class AlgebraChooserItem {

    public boolean ressource;
    public String algebraName;
    public String showString;

    public AlgebraChooserItem(boolean ressource, String algebraName, String showString) {
        this.ressource = ressource;
        this.algebraName = algebraName;
        this.showString = showString;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.ressource ? 1 : 0);
        hash = 19 * hash + (this.algebraName != null ? this.algebraName.hashCode() : 0);
        hash = 19 * hash + (this.showString != null ? this.showString.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AlgebraChooserItem other = (AlgebraChooserItem) obj;
        if (this.ressource != other.ressource) {
            return false;
        }
        if ((this.algebraName == null) ? (other.algebraName != null) : !this.algebraName.equals(other.algebraName)) {
            return false;
        }
        if ((this.showString == null) ? (other.showString != null) : !this.showString.equals(other.showString)) {
            return false;
        }
        return true;
    }
    

    
    @Override
    public String toString() {
        return showString;
    }

}
