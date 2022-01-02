package datapath.graph.modlib;

/**
 *
 * @author jh
 */
public class Wire {

    private String name;
    private int size;

    public Wire(String name) {
        this.name = name;
        size = 1;
    }

    @Override
    public String toString() {
        return name;
    }

    public String withSize() {
        if (size > 1) {
            return String.format("[%d:0] %s", size - 1, name);
        } else {
            return toString();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wire other = (Wire) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(
                other.name)) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 23 * hash + this.size;
        return hash;
    }
}
