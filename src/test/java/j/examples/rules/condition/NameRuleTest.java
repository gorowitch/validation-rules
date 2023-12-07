package j.examples.rules.condition;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class NameRuleTest {
    private static final NameRule rule = new NameRule();

    @Nested
    class ValidNames {
        @Test
        void have_a_minimal_length_of_1_character() {
            assertValid(() -> rule.validate("S"));
        }

        @Test
        void have_a_maximal_length_of_64_characters() {
            assertValid(() -> rule.validate("0123456789012345678901234567890123456789012345678901234567890123"));
        }

        @Test
        void have_at_least_1_nonwhitespace_character() {
            assertValid(() -> rule.validate("S"));
        }
    }

    @Nested
    class ValidNamesCanNot {
        @Test
        void have_a_length_less_than_1_character() {
            assertNotValid(
                () -> rule.validate(""));
        }

        @Test
        void have_a_length_exceeding_64_characters() {
            assertNotValid(() -> rule.validate("01234567890123456789012345678901234567890123456789012345678901234"));
        }

        @Test
        void have_all_whitespace_characters() {
            assertNotValid(() -> rule.validate(" \t "));
        }

        @Test
        void have_leading_whitespace_characters() {
            assertNotValid(() -> rule.validate("\tname"));
        }

        @Test
        void have_trailing_whitespace_characters() {
            assertNotValid(() -> rule.validate("name\t"));
        }
    }

    private static void assertValid(Supplier<Boolean> supplier) {
        assertThat(supplier.get()).isTrue();
    }

    private static void assertNotValid(Supplier<Boolean> supplier) {
        assertThat(supplier.get()).isFalse();
    }
}