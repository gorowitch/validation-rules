package j.examples.rules.guarding;

import j.examples.validation.ValidationException;

import java.io.File;

public class PdfExtensionFileRule {

    public void validate(File file) {
        String fileExtension = file.getName().substring(file.getName().lastIndexOf('.') + 1);

        if (!fileExtension.equals("pdf")) {
            throw new ValidationException(
                "The file %s is not a .pdf file".formatted(file)
            );
        }
    }
}