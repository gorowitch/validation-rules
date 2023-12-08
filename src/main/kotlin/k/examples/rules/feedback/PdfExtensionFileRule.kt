package k.examples.rules.feedback

import java.io.File

class PdfExtensionFileRule {
    fun validate(file: File): List<String> {
        val messages = mutableListOf<String>()
        if (file.extension != "pdf") {
            messages += "The file ${file} is not a .pdf file"
        }
        return messages
    }
}