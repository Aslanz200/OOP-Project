package kz.kbtu.exceptions;

/** Thrown when a Student tries to register past the 21-credit limit or with too many fails. */
public class CreditLimitException extends Exception {
    public CreditLimitException(String message) {
        super(message);
    }
}
