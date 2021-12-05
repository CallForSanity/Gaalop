package datapath.graph.modlib;

/**
 *
 * @author jh
 */
public class WireIO extends IO<Wire> {

    private String name;

    public WireIO(Wire value, String name) {
        super(value);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WireIO other = (WireIO) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


}
