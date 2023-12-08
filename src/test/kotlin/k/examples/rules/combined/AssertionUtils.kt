package k.examples.rules.combined

import k.examples.validation.ValidationResult
import org.assertj.core.api.Assertions.assertThat

object AssertionUtils {
    fun assertValid(lambda: () -> ValidationResult) {
        assertThat(lambda.invoke()).isInstanceOf(ValidationResult.Success::class.java)
    }

    fun assertNotValid(lambda: () -> ValidationResult, vararg messages: String) {
        val result = lambda.invoke()

        assertThat(result).isInstanceOf(ValidationResult.Failure::class.java)
        assertThat(result.errorMessages).containsExactly(*messages)
    }
}