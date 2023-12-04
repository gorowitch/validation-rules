package k.examples.validators.guarding

import k.examples.validation.ValidationException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class NameValidatorTest {
    private val validator = NameValidator()

    @Nested
    internal inner class ValidNames {
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
    internal inner class ValidNamesCanNot {
        @Test
        fun `Have a length less than 1 character`() {
            assertNotValid(
                { validator.validate("") },
                "The name \"\" must have a length >=1 and <=64. Was 0"
            )
        }

        @Test
        fun `Have a length exceeding 64 characters`() {
            assertNotValid(
                { validator.validate("01234567890123456789012345678901234567890123456789012345678901234") },
                "The name \"01234567890123456789012345678901234567890123456789012345678901234\" must have a length >=1 and <=64. Was 65"
            )
        }

        @Test
        fun `Have all whitespace characters`() {
            assertNotValid(
                { validator.validate(" \t ") },
                "The name \" \t \" must start and end with a non-whitespace-character"
            )
        }

        @Test
        fun `Have leading whitespace characters`() {
            assertNotValid(
                { validator.validate("\tname") },
                "The name \"\tname\" must start and end with a non-whitespace-character"
            )
        }

        @Test
        fun `Have trailing whitespace characters`() {
            assertNotValid(
                { validator.validate("name\t") },
                "The name \"name\t\" must start and end with a non-whitespace-character"
            )
        }
    }

    private fun assertValid(throwingCallable: ThrowingCallable) {
        assertThatCode(throwingCallable).doesNotThrowAnyException()
    }

    private fun assertNotValid(throwingCallable: ThrowingCallable, message: String) {
        assertThatThrownBy(throwingCallable)
            .isInstanceOf(ValidationException::class.java)
            .hasMessage(message)
    }
}