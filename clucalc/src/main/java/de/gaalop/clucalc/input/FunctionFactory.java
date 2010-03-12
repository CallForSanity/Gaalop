package de.gaalop.clucalc.input;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.input.function.*;
import de.gaalop.dfg.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * This utility class creates DFG expressions for CluCalc functions and their arguments.
 */
public class FunctionFactory {

    private static Map<String, Function> FUNCTIONS;

    static {
        FUNCTIONS = new HashMap<String, Function>();
        FUNCTIONS.put("VecE3", new VecE3());
        FUNCTIONS.put("VecN3", new VecN3());
        FUNCTIONS.put("SphereN3", new SphereN3());
        FUNCTIONS.put("RotorE3", new RotorE3());
        FUNCTIONS.put("RotorN3", new RotorN3());
        FUNCTIONS.put("TranslatorN3", new TranslatorN3());
        FUNCTIONS.put("*", new Dual());
    }

    private AlgebraMode mode;

    public FunctionFactory() {
    }

    public AlgebraMode getMode() {
        return mode;
    }

    public void setMode(AlgebraMode mode) {
        this.mode = mode;
    }

    /**
     * Checks whether the given function is defined in the current algebra mode.
     *
     * @param name The name of the function. This is case sensitive.
     * @return True if the function is defined.
     */
    public boolean isDefined(String name) {
        Function function = FUNCTIONS.get(name);

        return function != null && function.isDefinedIn(mode);
    }

    /**
     * Creates an expression that represents the function with the given name and arguments.
     *
     * @param name The function name.
     * @param args The functions arguments.
     * @return An expression with copies of the given arguments that represents the result of the given function.
     */
    public Expression createExpression(String name, Expression... args) {
        assert isDefined(name);

        Function function = FUNCTIONS.get(name);

        return function.createExpression(mode, args);
    }
}
