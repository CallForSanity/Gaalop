package datapath.graph.modlib;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jh
 */
public class Module {

    private HashSet<Parameter> params;
    private HashSet<IO> ios;
    private String type;
    private String name;
    private int id;

    public Module(String type, int id) {
        this.params = new HashSet<Parameter>();
        this.ios = new HashSet<IO>();
        this.id = id;
        this.type = type;
    }

    public Module(String type, String name, int id) {
        this(type, id);
        this.name = name;
    }

    public void addParameter(Parameter param) {
        params.add(param);
    }

    public void addIO(IO io) {
        if(ios.contains(io))
            return;
        ios.add(io);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashSet<IO> getIos() {
        return ios;
    }

    public void setIos(HashSet<IO> ios) {
        this.ios = ios;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Set<Parameter> getParams() {
        return Collections.unmodifiableSet(params);
    }

    public void setParams(HashSet<Parameter> params) {
        this.params = params;
    }
}
