package j.examples.rules.combined;

import j.examples.validation.ValidationResult;

import java.io.File;

public class PdfExtensionFileRule {

    public ValidationResult validate(File file) {
        String fileExtension = file.getName().substring(file.getName().lastIndexOf('.') + 1);

        if (!fileExtension.equals("pdf")) {
            return ValidationResult.failure("The file %s is not a .pdf file".formatted(file));
        }

        return ValidationResult.success();
    }
}