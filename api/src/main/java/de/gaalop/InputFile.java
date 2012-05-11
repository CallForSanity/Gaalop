package de.gaalop;

/**
 * Represents an input source file for the Gaalop frontend. *
 */
public final class InputFile {

    private final String name;

    private final String content;

    /**
     * Constructs a new input file for Gaalop.
     *
     * @param name The filename of the input. Must not be null.
     * @param content A string containing the source code of the input file.
     */
    public InputFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    /**
     * Gets the filename of this input file.
     * @return A string containing the filename of the input file.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the source code of this input file.
     * @return A string containing the source code of this input file.
     */
    public String getContent() {
        return content;
    }

    /**
     * Compares two input files for equality.
     *
     * Input files are equal if and only if their name and content are equal and their class is the same.
     *
     * @param o The other object.
     * @return True if and only if this input file is equal to the other input file.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputFile inputFile = (InputFile) o;

        if (content != null ? !content.equals(inputFile.content) : inputFile.content != null) return false;
        if (name != null ? !name.equals(inputFile.name) : inputFile.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
