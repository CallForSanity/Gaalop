package de.gaalop;

/**
 * This exception is the base for all exceptions inside the compiler system.
 */
public class CompilationException extends Exception {

  private static final long serialVersionUID = -4173524661050025188L;

  public CompilationException(String message) {
    super(message);
  }

  public CompilationException(String message, Throwable cause) {
    super(message, cause);
  }
}
