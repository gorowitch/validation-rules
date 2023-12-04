package j.examples.validators.guarding;

import j.examples.validation.ValidationException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameValidatorTest {
    private static final NameValidator validator = new NameValidator();

    @Nested
    class ValidNames {
        @Test
        void have_a_minimal_length_of_1_character() {
            assertValid(() -> validator.validate("S"));
        }

        @Test
        void have_a_maximal_length_of_64_characters() {
            assertValid(() -> validator.validate("0123456789012345678901234567890123456789012345678901234567890123"));
        }

        @Test
        void have_at_least_1_nonwhitespace_character() {
            assertValid(() -> validator.validate("S"));
        }
    }

    @Nested
    class ValidNamesCanNot {
        @Test
        void have_a_length_less_than_1_character() {
            assertNotValid(
                () -> validator.validate(""),
                "The name \"\" must have a length >=1 and <=64. Was 0");
        }

        @Test
        void have_a_length_exceeding_64_characters() {
            assertNotValid(
                () -> validator.validate("01234567890123456789012345678901234567890123456789012345678901234"),
                "The name \"01234567890123456789012345678901234567890123456789012345678901234\" must have a length >=1 and <=64. Was 65");
        }

        @Test
        void have_all_whitespace_characters() {
            assertNotValid(
                () -> validator.validate(" \t "),
                "The name \" \t \" must start and end with a non-whitespace-character");
        }

        @Test
        void have_leading_whitespace_characters() {
            assertNotValid(
                () -> validator.validate("\tname"),
                "The name \"\tname\" must start and end with a non-whitespace-character");
        }

        @Test
        void have_trailing_whitespace_characters() {
            assertNotValid(
                () -> validator.validate("name\t"),
                "The name \"name\t\" must start and end with a non-whitespace-character");
        }
    }

    private static void assertValid(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatCode(throwingCallable).doesNotThrowAnyException();
    }

    private static void assertNotValid(ThrowableAssert.ThrowingCallable throwingCallable, String message) {
        assertThatThrownBy(throwingCallable)
            .isInstanceOf(ValidationException.class)
            .hasMessage(message);
    }
}