package k.examples.rules.guarding

import k.examples.rules.guarding.AssertionUtils.assertNotValid
import k.examples.rules.guarding.AssertionUtils.assertValid
import org.junit.jupiter.api.Test
import java.io.File


class PdfExtensionFileRuleTest {
    private val rule = PdfExtensionFileRule()

    @Test
    fun `Files with extension pdf are accepted`() {
        assertValid { rule.validate(File("invoice.pdf")) }
    }

    @Test
    fun `Files with extension other than pdf are rejected`() {
        assertNotValid(
            { rule.validate(File("invoice.doc")) },
            """The file invoice.doc is not a .pdf file"""
        )
    }
}