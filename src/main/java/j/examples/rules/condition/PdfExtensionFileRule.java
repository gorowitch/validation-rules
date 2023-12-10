package j.examples.rules.condition;

import java.io.File;

public class PdfExtensionFileRule {

    public boolean validate(File file) {
        String fileExtension = file.getName().substring(file.getName().lastIndexOf('.') + 1);

        return fileExtension.equals("pdf");
    }
}