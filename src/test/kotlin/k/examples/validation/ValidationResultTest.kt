package k.examples.validation

import k.examples.validation.ValidationResult.Failure
import k.examples.validation.ValidationResult.Success
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationResultTest {

    @Nested
    inner class `Atomic Validation Results` {
        @Test
        fun `A successful result is Success`() {
            assertThat(ValidationResult.success()).isSameAs(Success)
        }

        @Test
        fun `A failure result is a Failure with a single error message`() {
            val failure = ValidationResult.failure(MESSAGE)

            assertThat(failure).isInstanceOf(Failure::class.java)

            assertThat(failure.errorMessages).containsExactly(MESSAGE)
        }
    }

    @Nested
    inner class `Combined Validation Results` {
        @Test
        fun `An empty combination yields Success`() {
            val combination = ValidationResult.combine()

            assertThat(combination).isInstanceOf(Success::class.java)
        }

        @Test
        fun `A combination of a single Success yields Success`() {
            val combination = ValidationResult.combine(ValidationResult.success())

            assertThat(combination).isInstanceOf(Success::class.java)
        }

        @Test
        fun `A combination of a single Failure yields Failure`() {
            val combination = ValidationResult.combine(ValidationResult.failure(MESSAGE))

            assertThat(combination).isInstanceOf(Failure::class.java)
            assertThat((combination as Failure).errorMessages).containsExactly(MESSAGE)
        }

        @Test
        fun `A combination of a multiple Successes yields Success`() {
            val combination = ValidationResult.combine(
                ValidationResult.success(),
                ValidationResult.success(),
            )

            assertThat(combination).isInstanceOf(Success::class.java)
        }

        @Test
        fun `A combination of a Success and Failure yields Success`() {
            val combination = ValidationResult.combine(
                ValidationResult.success(),
                ValidationResult.failure(MESSAGE),
            )

            assertThat(combination).isInstanceOf(Failure::class.java)
            assertThat((combination as Failure).errorMessages).containsExactly(MESSAGE)
        }

        @Test
        fun `A combination of a multiple Failures yields Failure`() {
            val combination = ValidationResult.combine(
                ValidationResult.failure(MESSAGE_1),
                ValidationResult.failure(MESSAGE_2),
            )

            assertThat(combination).isInstanceOf(Failure::class.java)
            assertThat((combination as Failure).errorMessages).containsExactly(MESSAGE_1, MESSAGE_2)
        }
    }

    @Nested
    inner class `Combined Collection of Validation Results` {
        @Test
        fun `A combination of a multiple Failures yields Failure`() {
            val combination = ValidationResult.combine(
                listOf(
                    ValidationResult.failure(MESSAGE_1),
                    ValidationResult.failure(MESSAGE_2),
                )
            )

            assertThat(combination).isInstanceOf(Failure::class.java)
            assertThat((combination as Failure).errorMessages).containsExactly(MESSAGE_1, MESSAGE_2)
        }
    }


    @Nested
    internal class GuardingBehavior {
        @Test
        fun checking_success_resumes_program_flow() {
            val result = ValidationResult.success()
            Assertions.assertThatCode { result.check() }.doesNotThrowAnyException()
        }

        @Test
        fun checking_failure_aborts_program_flow_with_message() {
            val result = ValidationResult.failure(ValidationResultTest.MESSAGE)
            Assertions.assertThatThrownBy { result.check() }
                .isInstanceOf(ValidationException::class.java)
                .hasMessage(ValidationResultTest.MESSAGE)
        }

        @Test
        fun checking_combined_failure_aborts_program_flow_with_all_messages() {
            val result = ValidationResult.combine(
                ValidationResult.failure(ValidationResultTest.MESSAGE_1),
                ValidationResult.failure(ValidationResultTest.MESSAGE_2)
            )
            Assertions.assertThatThrownBy { result.check() }
                .isInstanceOf(ValidationException::class.java)
                .hasMessage("message-1,message-2")
        }
    }


    @Nested
    internal class ConditionBehavior {
        @Test
        fun evaluating_success() {
            val result = ValidationResult.success()
            assertThat(result.success).isTrue()
        }

        @Test
        fun evaluating_failure() {
            val result = ValidationResult.failure("reason")
            assertThat(result.success).isFalse()
        }
    }


    @Nested
    internal class FeedbackBehavior {
        @Test
        fun feedback_success_has_no_messages() {
            val result = ValidationResult.success()
            assertThat(result.errorMessages).isEmpty()
        }

        @Test
        fun feedback_failure_has_a_message() {
            val result = ValidationResult.failure(MESSAGE)
            assertThat(result.errorMessages).containsExactly(MESSAGE)
        }

        @Test
        fun feedback_combined_failure_has_multiple_messages() {
            val result = ValidationResult.combine(
                ValidationResult.failure(MESSAGE_1),
                ValidationResult.failure(MESSAGE_2)
            )
            assertThat(result.errorMessages).containsExactly(MESSAGE_1, MESSAGE_2)
        }
    }

    companion object {
        val MESSAGE = "message"
        val MESSAGE_1 = "message-1"
        val MESSAGE_2 = "message-2"
    }
}