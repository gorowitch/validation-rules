package j.examples.validators.feedback;

import java.util.ArrayList;
import java.util.List;

public class NameValidator {

    private static final int MINIMAL_LENGTH = 1;
    private static final int MAXIMAL_LENGTH = 64;

    List<String> validate(String name) {
        var messages = new ArrayList<String>();
        if (name.length() < MINIMAL_LENGTH || name.length() > MAXIMAL_LENGTH) {
            messages.add("The name \"%s\" must have a length >=%d and <=%d. Was %d".formatted(name, MINIMAL_LENGTH, MAXIMAL_LENGTH, name.length()));
        }

        if (name.length() > 0 && (Character.isWhitespace(name.charAt(0)) || Character.isWhitespace(name.charAt(name.length() - 1)))) {
            messages.add("The name \"%s\" must start and end with a non-whitespace-character".formatted(name));
        }

        return messages.stream().toList();
    }
}
