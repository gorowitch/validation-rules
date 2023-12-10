package j.examples.rules.feedback;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {
    static void assertValid(Supplier<List<String>> supplier) {
        assertThat(supplier.get()).isEmpty();
    }

    static void assertNotValid(Supplier<List<String>> supplier, String... message) {
        assertThat(supplier.get()).containsExactly(message);
    }
}
