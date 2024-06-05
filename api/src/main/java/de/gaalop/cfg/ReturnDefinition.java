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

/*
  This class stores data that defines a #pragma return statement.


    Consider following Gaalop script:

         //#pragma return line typed Vector2 as new Vector2(e1, e2)
         ?line = e1 + e2

       The pragma return statement will alter the generated return statement in C#:

         return (line_e1, line_e2);            // without pragma
         return new Vector2(line_e1, line_e2)  // with pragma

 */
public final class ReturnDefinition {

    public String[] variableNames;
    public String returnType;
    public String returnText;

    public ReturnDefinition(String[] variableNames, String returnType, String returnText) {
        this.variableNames = variableNames;
        this.returnType = returnType;
        this.returnText = returnText;
    }
}
