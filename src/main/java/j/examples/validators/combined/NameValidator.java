package j.examples.validators.combined;

import j.examples.validation.ValidationResult;

import java.util.ArrayList;

import static j.examples.validation.ValidationResult.combine;
import static j.examples.validation.ValidationResult.failure;

public class NameValidator {

    private static final int MINIMAL_LENGTH = 1;
    private static final int MAXIMAL_LENGTH = 64;

    ValidationResult validate(String name) {
        var validationResults = new ArrayList<ValidationResult>();

        if (name.length() < MINIMAL_LENGTH || name.length() > MAXIMAL_LENGTH) {
            validationResults.add(failure("The name \"%s\" must have a length >=%d and <=%d. Was %d".formatted(name, MINIMAL_LENGTH, MAXIMAL_LENGTH, name.length())));
        }

        if (name.length() > 0 && (Character.isWhitespace(name.charAt(0)) || Character.isWhitespace(name.charAt(name.length() - 1)))) {
            validationResults.add(failure("The name \"%s\" must start and end with a non-whitespace-character".formatted(name)));
        }

        return combine(validationResults);
    }
}
