package edu.yale.sml.view;

/**
 * Checked exception to ensure file presence
 */
public class NullFileException extends Exception {

    public NullFileException(String string) {
        super(string);
    }
}
