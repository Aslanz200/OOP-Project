package kz.kbtu.exceptions;

/** Thrown when authentication fails (unknown user or wrong password). */
public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }
}
