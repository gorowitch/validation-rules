package k.examples.rules.guarding

import k.examples.validation.ValidationException
import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableAssert

object AssertionUtils {

    fun assertValid(throwingCallable: ThrowableAssert.ThrowingCallable) {
        Assertions.assertThatCode(throwingCallable).doesNotThrowAnyException()
    }

    fun assertNotValid(throwingCallable: ThrowableAssert.ThrowingCallable, message: String) {
        Assertions.assertThatThrownBy(throwingCallable)
            .isInstanceOf(ValidationException::class.java)
            .hasMessage(message)
    }
}