package k.examples.rules.guarding

import k.examples.rules.guarding.AssertionUtils.assertNotValid
import k.examples.rules.guarding.AssertionUtils.assertValid
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class NameRuleTest {
    private val rule = NameRule()

    @Nested
    internal inner class ValidNames {
        @Test
        fun `Have a minimal length of 1 character`() {
            assertValid { rule.validate("S") }
        }

        @Test
        fun `Have a maximal length of 64 characters`() {
            assertValid { rule.validate("0123456789012345678901234567890123456789012345678901234567890123") }
        }

        @Test
        fun `Have at least 1 nonwhitespace character`() {
            assertValid { rule.validate("S") }
        }
    }

    @Nested
    internal inner class ValidNamesCanNot {
        @Test
        fun `Have a length less than 1 character`() {
            assertNotValid(
                { rule.validate("") },
                "The name \"\" must have a length >=1 and <=64. Was 0"
            )
        }

        @Test
        fun `Have a length exceeding 64 characters`() {
            assertNotValid(
                { rule.validate("01234567890123456789012345678901234567890123456789012345678901234") },
                "The name \"01234567890123456789012345678901234567890123456789012345678901234\" must have a length >=1 and <=64. Was 65"
            )
        }

        @Test
        fun `Have all whitespace characters`() {
            assertNotValid(
                { rule.validate(" \t ") },
                "The name \" \t \" must start and end with a non-whitespace-character"
            )
        }

        @Test
        fun `Have leading whitespace characters`() {
            assertNotValid(
                { rule.validate("\tname") },
                "The name \"\tname\" must start and end with a non-whitespace-character"
            )
        }

        @Test
        fun `Have trailing whitespace characters`() {
            assertNotValid(
                { rule.validate("name\t") },
                "The name \"name\t\" must start and end with a non-whitespace-character"
            )
        }
    }
}