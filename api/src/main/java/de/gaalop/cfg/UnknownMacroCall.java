package de.gaalop.cfg;

import de.gaalop.dfg.MacroCall;

/**
 *
 * @author Christian Steinmetz
 */
public class UnknownMacroCall {
    
    public MacroCall macroCall;
    public ColorNode colorNode;

    public UnknownMacroCall(MacroCall macroCall, ColorNode colorNode) {
        this.macroCall = macroCall;
        this.colorNode = colorNode;
    }

}
