package k.examples.rules.condition

import java.io.File

class PdfExtensionFileRule {
    fun validate(file: File): Boolean {
        return file.extension == "pdf"
    }
}