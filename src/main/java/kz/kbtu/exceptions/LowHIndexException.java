package kz.kbtu.exceptions;

/**
 * Thrown when a Researcher with h-index &lt; 3 is assigned as a supervisor of a 4th-year student.
 */
public class LowHIndexException extends Exception {
    public LowHIndexException(String message) {
        super(message);
    }
}
