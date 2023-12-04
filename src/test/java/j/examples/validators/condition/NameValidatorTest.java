package j.examples.validators.condition;

import j.examples.validators.condition.NameValidator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

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
                () -> validator.validate(""));
        }

        @Test
        void have_a_length_exceeding_64_characters() {
            assertNotValid(() -> validator.validate("01234567890123456789012345678901234567890123456789012345678901234"));
        }

        @Test
        void have_all_whitespace_characters() {
            assertNotValid(() -> validator.validate(" \t "));
        }

        @Test
        void have_leading_whitespace_characters() {
            assertNotValid(() -> validator.validate("\tname"));
        }

        @Test
        void have_trailing_whitespace_characters() {
            assertNotValid(() -> validator.validate("name\t"));
        }
    }

    private static void assertValid(Supplier<Boolean> supplier) {
        assertThat(supplier.get()).isTrue();
    }

    private static void assertNotValid(Supplier<Boolean> supplier) {
        assertThat(supplier.get()).isFalse();
    }
}