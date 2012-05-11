package de.gaalop;

/**
 * This exception models error conditions that occur in the code parsers used by Gaalop.
 * 
 * @see de.gaalop.CodeParser
 */
public class CodeParserException extends CompilationException {

  private static final long serialVersionUID = -837369348101867900L;

  private final InputFile inputFile;

  public CodeParserException(InputFile inputFile, String message) {
    super(message);
    this.inputFile = inputFile;
  }

  public CodeParserException(InputFile inputFile, String message, Throwable cause) {
    super(message, cause);
    this.inputFile = inputFile;
  }

  /**
   * Gets the input file that caused the parsing exception.
   * 
   * @return An instance of the input file.
   */
  public InputFile getInputFile() {
    return inputFile;
  }
}
