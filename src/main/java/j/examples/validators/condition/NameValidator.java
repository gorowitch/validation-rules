package j.examples.validators.condition;

public class NameValidator {

    private static final int MINIMAL_LENGTH = 1;
    private static final int MAXIMAL_LENGTH = 64;

    boolean validate(String name) {
        if (name.length() < MINIMAL_LENGTH || name.length() > MAXIMAL_LENGTH) {
            return false;
        }

        if (Character.isWhitespace(name.charAt(0)) || Character.isWhitespace(name.charAt(name.length() - 1))) {
            return false;
        }

        return true;
    }
}
