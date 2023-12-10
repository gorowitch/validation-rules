package j.examples.rules.guarding;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static j.examples.rules.guarding.AssertionUtils.assertNotValid;
import static j.examples.rules.guarding.AssertionUtils.assertValid;

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
                () -> rule.validate(""),
                "The name \"\" must have a length >=1 and <=64. Was 0");
        }

        @Test
        void have_a_length_exceeding_64_characters() {
            assertNotValid(
                () -> rule.validate("01234567890123456789012345678901234567890123456789012345678901234"),
                "The name \"01234567890123456789012345678901234567890123456789012345678901234\" must have a length >=1 and <=64. Was 65");
        }

        @Test
        void have_all_whitespace_characters() {
            assertNotValid(
                () -> rule.validate(" \t "),
                "The name \" \t \" must start and end with a non-whitespace-character");
        }

        @Test
        void have_leading_whitespace_characters() {
            assertNotValid(
                () -> rule.validate("\tname"),
                "The name \"\tname\" must start and end with a non-whitespace-character");
        }

        @Test
        void have_trailing_whitespace_characters() {
            assertNotValid(
                () -> rule.validate("name\t"),
                "The name \"name\t\" must start and end with a non-whitespace-character");
        }
    }
}