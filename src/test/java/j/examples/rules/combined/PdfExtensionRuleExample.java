package j.examples.rules.combined;

import java.io.File;

public class PdfExtensionRuleExample {

    public static void main(String[] args) {
        var pdfRule = new PdfExtensionFileRule();
        var file = new File("invoice.pdf");

        // usage for flow control
        if (pdfRule.validate(file).isSuccess()) {
            System.out.println("... processing the .pdf file");
        }

        // usage as a guard clause
        pdfRule.validate(file).check();

        // usage for feedback messages
        var messages = pdfRule.validate(file).messages();
    }
}
