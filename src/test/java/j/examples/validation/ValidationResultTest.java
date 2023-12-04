package j.examples.validation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ValidationResultTest {

    @Nested
    class AtomicValidationResults {
        @Test
        public void a_successful_result_is_Success() {
            assertThat(ValidationResult.success()).isSameAs(ValidationResult.Success.INSTANCE);
        }

        @Test
        public void a_failure_result_is_a_Failure_with_a_single_error_message() {
            var failure = ValidationResult.failure(MESSAGE);

            assertThat(failure).isInstanceOf(ValidationResult.Failure.class);
            assertThat(failure.errorMessages()).containsExactly(MESSAGE);
        }
    }

    @Nested
    class CombinedValidationResults {
        @Test
        public void an_empty_combination_yields_Success() {
            var combination = ValidationResult.combine();

            assertThat(combination).isInstanceOf(ValidationResult.Success.class);
        }

        @Test
        public void a_combination_of_a_single_Success_yields_Success() {
            var combination = ValidationResult.combine(ValidationResult.success());

            assertThat(combination).isInstanceOf(ValidationResult.Success.class);
        }

        @Test
        public void a_combination_of_a_single_Failure_yields_Failure() {
            var combination = ValidationResult.combine(ValidationResult.failure(MESSAGE));

            assertThat(combination).isInstanceOf(ValidationResult.Failure.class);
            assertThat(combination.errorMessages()).containsExactly(MESSAGE);
        }

        @Test
        public void a_combination_of_a_multiple_Successes_yields_Success() {
            var combination = ValidationResult.combine(
                ValidationResult.success(),
                ValidationResult.success()
            );

            assertThat(combination).isInstanceOf(ValidationResult.Success.class);
        }

        @Test
        public void a_combination_of_a_Success_and_Failure_yields_Success() {
            var combination = ValidationResult.combine(
                ValidationResult.success(),
                ValidationResult.failure(MESSAGE)
            );

            assertThat(combination).isInstanceOf(ValidationResult.Failure.class);
            assertThat(((ValidationResult.Failure) combination).errorMessages()).containsExactly(MESSAGE);
        }

        @Test
        public void a_combination_of_a_multiple_Failures_yields_Failure() {
            var combination = ValidationResult.combine(
                ValidationResult.failure(MESSAGE_1),
                ValidationResult.failure(MESSAGE_2)
            );

            assertThat(combination).isInstanceOf(ValidationResult.Failure.class);
            assertThat(combination.errorMessages()).containsExactly(MESSAGE_1, MESSAGE_2);
        }
    }

    @Nested
    class CombinedCollectionOfValidationResults {
        @Test
        public void a_combination_of_a_multiple_Failures_yields_Failure() {
            var combination = ValidationResult.combine(
                List.of(
                    ValidationResult.failure(MESSAGE_1),
                    ValidationResult.failure(MESSAGE_2)
                )
            );

            assertThat(combination).isInstanceOf(ValidationResult.Failure.class);
            assertThat(combination.errorMessages()).containsExactly(MESSAGE_1, MESSAGE_2);
        }
    }

    @Nested
    class GuardingBehavior {
        @Test
        public void checking_success_resumes_program_flow() {
            var result = ValidationResult.success();

            assertThatCode(() -> result.check()).doesNotThrowAnyException();
        }

        @Test
        public void checking_failure_aborts_program_flow_with_message() {
            var result = ValidationResult.failure(MESSAGE);

            assertThatThrownBy(() -> result.check())
                .isInstanceOf(ValidationException.class)
                .hasMessage(MESSAGE);
        }

        @Test
        public void checking_combined_failure_aborts_program_flow_with_all_messages() {
            var result = ValidationResult.combine(
                ValidationResult.failure(MESSAGE_1),
                ValidationResult.failure(MESSAGE_2)
            );

            assertThatThrownBy(() -> result.check())
                .isInstanceOf(ValidationException.class)
                .hasMessage("message-1,message-2");
        }
    }

    @Nested
    class ConditionBehavior {
        @Test
        public void evaluating_success() {
            var result = ValidationResult.success();

            assertThat(result.isSuccess()).isTrue();
        }

        @Test
        public void evaluating_failure() {
            var result = ValidationResult.failure("reason");

            assertThat(result.isSuccess()).isFalse();
        }
    }

    @Nested
    class FeedbackBehavior {
        @Test
        public void feedback_success_has_no_messages() {
            var result = ValidationResult.success();

            assertThat(result.errorMessages()).isEmpty();
        }

        @Test
        public void feedback_failure_has_a_message() {
            var result = ValidationResult.failure(MESSAGE);

            assertThat(result.errorMessages()).containsExactly(MESSAGE);
        }

        @Test
        public void feedback_combined_failure_has_multiple_messages() {
            var result = ValidationResult.combine(
                ValidationResult.failure(MESSAGE_1),
                ValidationResult.failure(MESSAGE_2)
            );

            assertThat(result.errorMessages()).containsExactly(MESSAGE_1, MESSAGE_2);
        }
    }

    private static final String MESSAGE = "message";
    private static final String MESSAGE_1 = "message-1";
    private static final String MESSAGE_2 = "message-2";
}