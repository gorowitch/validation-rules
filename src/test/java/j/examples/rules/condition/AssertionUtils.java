package j.examples.rules.condition;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {
    private AssertionUtils() {}

    static void assertValid(Supplier<Boolean> supplier) {
        assertThat(supplier.get()).isTrue();
    }

    static void assertNotValid(Supplier<Boolean> supplier) {
        assertThat(supplier.get()).isFalse();
    }
}
