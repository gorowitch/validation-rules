package j.examples.rules.combined;

import j.examples.validation.ValidationResult;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class AssertionUtils {
    private AssertionUtils() {}

    static void assertValid(Supplier<ValidationResult> supplier) {
        assertThat(supplier.get()).isInstanceOf(ValidationResult.Success.class);
    }

    static void assertNotValid(Supplier<ValidationResult> supplier, String... messages) {
        var result = supplier.get();

        if (result instanceof ValidationResult.Failure s) {
            assertThat(s.errorMessages()).containsExactly(messages);
        } else {
            fail();
        }
    }
}
