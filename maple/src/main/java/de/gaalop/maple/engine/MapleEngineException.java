package de.gaalop.maple.engine;

/**
 * This class wraps an error produced by the maple engine as an exception.
 * It contains the string that was evaluated before the error occured and
 * the error message itself.
 */
public class MapleEngineException extends Exception {
    private String lastEvaluatedCommand;

    public MapleEngineException(String lastEvaluatedCommand) {
        this.lastEvaluatedCommand = lastEvaluatedCommand;
    }

    public MapleEngineException(String message, String lastEvaluatedCommand) {
        super(message);
        this.lastEvaluatedCommand = lastEvaluatedCommand;
    }

    public MapleEngineException(String message, String lastEvaluatedCommand, Throwable cause) {
        super(message, cause);
        this.lastEvaluatedCommand = lastEvaluatedCommand;
    }

    public MapleEngineException(String lastEvaluatedComman, Throwable cause) {
        super(cause);
        this.lastEvaluatedCommand = lastEvaluatedCommand;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + lastEvaluatedCommand + ")";
    }
}
