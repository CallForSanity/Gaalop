package de.gaalop.productComputer.dataStruct;

/**
 * Represents a terminal
 * @author Christian Steinmetz
 */
public abstract class TCTerminal extends TCExpression {

    @Override
    public boolean isComposite() {
        return false;
    }

}
