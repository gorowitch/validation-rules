package j.examples.rules.guarding;

import org.junit.jupiter.api.Test;

import java.io.File;

import static j.examples.rules.guarding.AssertionUtils.assertNotValid;
import static j.examples.rules.guarding.AssertionUtils.assertValid;

public class PdfExtensionFileRuleTest {
    private static final PdfExtensionFileRule rule = new PdfExtensionFileRule();

    @Test
    void files_without_extension_are_rejected() {
        assertNotValid(() -> rule.validate(new File("invoice")),
            "The file invoice is not a .pdf file");
    }

    @Test
    void files_with_extension_pdf_are_accepted() {
        assertValid(() -> rule.validate(new File("invoice.pdf")));
    }

    @Test
    void files_with_extension_other_than_pdf_are_rejected() {
        assertNotValid(
            () -> rule.validate(new File("invoice.doc")),
            "The file invoice.doc is not a .pdf file"
        );
    }
}