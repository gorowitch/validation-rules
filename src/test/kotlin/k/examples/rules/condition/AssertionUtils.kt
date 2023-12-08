package k.examples.rules.condition

import org.assertj.core.api.Assertions

object AssertionUtils {

    public fun assertValid(lambda: () -> Boolean) {
        Assertions.assertThat(lambda.invoke()).isTrue()
    }

    public fun assertNotValid(lambda: () -> Boolean) {
        Assertions.assertThat(lambda.invoke()).isFalse()
    }
}