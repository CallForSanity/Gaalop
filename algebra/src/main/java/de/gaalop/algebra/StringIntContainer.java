package de.gaalop.algebra;

/**
 * Stores a string and an int.
 * This class is typically used for macro identification.
 * A macro can be uniquely identified using its name combined with 
 * the number of its arguments.
 * @author Christian Steinmetz
 */
public class StringIntContainer {

    private String name;
    private int number;

    public StringIntContainer(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + this.number;
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
        final StringIntContainer other = (StringIntContainer) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        return true;
    }

}
