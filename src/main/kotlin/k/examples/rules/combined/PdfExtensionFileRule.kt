package k.examples.rules.combined

import k.examples.validation.ValidationResult
import java.io.File

class PdfExtensionFileRule {
    fun validate(file: File):ValidationResult {
        if (file.extension != "pdf") {
            return ValidationResult
                .failure("The file ${file} is not a .pdf file")
        }

        return ValidationResult.success()
    }
}