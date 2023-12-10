package j.examples.rules.condition;

import org.junit.jupiter.api.Test;

import java.io.File;

import static j.examples.rules.condition.AssertionUtils.assertNotValid;
import static j.examples.rules.condition.AssertionUtils.assertValid;

public class PdfExtensionFileRuleTest {
    private static final PdfExtensionFileRule rule = new PdfExtensionFileRule();

    @Test
    void files_without_extension_are_rejected() {
        assertNotValid(() -> rule.validate(new File("invoice")));
    }

    @Test
    void files_with_extension_pdf_are_accepted() {
        assertValid(() -> rule.validate(new File("invoice.pdf")));
    }

    @Test
    void files_with_extension_other_than_pdf_are_rejected() {
        assertNotValid(() -> rule.validate(new File("invoice.doc")));
    }
}