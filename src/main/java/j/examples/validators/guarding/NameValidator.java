package j.examples.validators.guarding;

import j.examples.validation.ValidationException;

public class NameValidator {

    private static final int MINIMAL_LENGTH = 1;
    private static final int MAXIMAL_LENGTH = 64;

    void validate(String name) {
        if (name.length() < MINIMAL_LENGTH || name.length() > MAXIMAL_LENGTH) {
            throw new ValidationException("The name \"%s\" must have a length >=%d and <=%d. Was %d".formatted(name, MINIMAL_LENGTH, MAXIMAL_LENGTH, name.length()));
        }

        if (Character.isWhitespace(name.charAt(0)) || Character.isWhitespace(name.charAt(name.length() - 1))) {
            throw new ValidationException("The name \"%s\" must start and end with a non-whitespace-character".formatted(name));
        }
    }
}
