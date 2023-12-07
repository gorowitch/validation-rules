package k.examples.rules.combined

import k.examples.validation.ValidationResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NameRuleTest {
    private val rule = NameRule()

    @Nested
    inner class ValidNames {
        @Test
        fun `Have a minimal length of 1 character`() {
            assertValid({ rule.validate("S") })
        }

        @Test
        fun `Have a maximal length of 64 characters`() {
            assertValid({ rule.validate("0123456789012345678901234567890123456789012345678901234567890123") })
        }

        @Test
        fun `Have at least 1 nonwhitespace character`() {
            assertValid({ rule.validate("S") })
        }
    }

    @Nested
    inner class ValidNamesCanNot {
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

    @Nested
    inner class ValidNamesCanNotCombinations {
        @Test
        fun `Have a length exceeding 64 characters with leading whitespace`() {
            assertNotValid(
                { rule.validate("\t1234567890123456789012345678901234567890123456789012345678901234") },
                "The name \"\t1234567890123456789012345678901234567890123456789012345678901234\" must have a length >=1 and <=64. Was 65",
                "The name \"\t1234567890123456789012345678901234567890123456789012345678901234\" must start and end with a non-whitespace-character"
            )
        }
    }

    private fun assertValid(lambda: () -> ValidationResult) {
        assertThat(lambda.invoke()).isInstanceOf(ValidationResult.Success::class.java)
    }

    private fun assertNotValid(lambda: () -> ValidationResult, vararg messages: String) {
        val result = lambda.invoke()
        if (result is ValidationResult.Failure) {
            assertThat(result.errorMessages).containsExactly(*messages)
        } else {
            Assertions.fail<Any>()
        }
    }
}
