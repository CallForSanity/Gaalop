package de.gaalop.cfg;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.InputFile;
import de.gaalop.StringList;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.Collection;

public final class ReturnDefinition {

    public String variableName;
    public String className;
    public String[] variables;

    // Constructor with string parameters
    public ReturnDefinition(String variableName, String className, String[] variables) {
        this.variableName = variableName;
        this.className = className;
        this.variables = variables;
    }
}
