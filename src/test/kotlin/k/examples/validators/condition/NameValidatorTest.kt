package k.examples.validators.condition

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NameValidatorTest {
    private val validator = NameValidator()

    @Nested
    inner class ValidNames {
        @Test
        fun `Have a minimal length of 1 character`() {
            assertValid { validator.validate("S") }
        }

        @Test
        fun `Have a maximal length of 64 characters`() {
            assertValid { validator.validate("0123456789012345678901234567890123456789012345678901234567890123") }
        }

        @Test
        fun `Have at least 1 nonwhitespace character`() {
            assertValid { validator.validate("S") }
        }
    }

    @Nested
    inner class ValidNamesCanNot {
        @Test
        fun `Have a length less than 1 character`() {
            assertNotValid { validator.validate("") }
        }

        @Test
        fun `Have a length exceeding 64 characters`() {
            assertNotValid { validator.validate("01234567890123456789012345678901234567890123456789012345678901234") }
        }

        @Test
        fun `Have all whitespace characters`() {
            assertNotValid { validator.validate(" \t ") }
        }

        @Test
        fun `Have leading whitespace characters`() {
            assertNotValid { validator.validate("\tname") }
        }

        @Test
        fun `Have trailing whitespace characters`() {
            assertNotValid { validator.validate("name\t") }
        }
    }

    private fun assertValid(lambda: () -> Boolean) {
        assertThat(lambda.invoke()).isTrue()
    }

    private fun assertNotValid(lambda: () -> Boolean) {
        assertThat(lambda.invoke()).isFalse()
    }
}