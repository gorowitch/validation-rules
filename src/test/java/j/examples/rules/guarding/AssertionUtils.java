package j.examples.rules.guarding;

import j.examples.validation.ValidationException;
import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssertionUtils {
    private AssertionUtils() {}

    static void assertValid(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatCode(throwingCallable).doesNotThrowAnyException();
    }

    static void assertNotValid(ThrowableAssert.ThrowingCallable throwingCallable, String message) {
        assertThatThrownBy(throwingCallable)
            .isInstanceOf(ValidationException.class)
            .hasMessage(message);
    }
}
