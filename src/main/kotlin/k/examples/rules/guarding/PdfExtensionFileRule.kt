package k.examples.rules.guarding

import k.examples.validation.ValidationException
import java.io.File

class PdfExtensionFileRule {
    fun validate(file: File) {
        if (file.extension != "pdf") {
            throw ValidationException(
                "The file ${file} is not a .pdf file"
            )
        }
    }
}