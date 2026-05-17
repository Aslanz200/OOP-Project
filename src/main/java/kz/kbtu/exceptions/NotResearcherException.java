package kz.kbtu.exceptions;

/**
 * Thrown when someone who is not a Researcher tries to join a ResearchProject.
 */
public class NotResearcherException extends Exception {
    public NotResearcherException(String message) {
        super(message);
    }
}
