package k.examples.rules.combined

import org.junit.jupiter.api.Test
import java.io.File

class PdfExtensionFileRuleExample {

    @Test
    fun example() {

        val pdfRule = PdfExtensionFileRule()
        val file = File("invoice.pdf")

        // usage for flow control
        if (pdfRule.validate(file).success) {
            println("... processing the .pdf file")
        }

        // usage as a guard clause
        pdfRule.validate(file).check()

        // usage for feedback messages
        val messages = pdfRule.validate(file).messages

    }
}