package j.examples.rules.feedback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfExtensionFileRule {

    public List<String> validate(File file) {
        var messages = new ArrayList<String>();

        String fileExtension = file.getName().substring(file.getName().lastIndexOf('.') +1);

        if (!fileExtension.equals("pdf")) {
            messages.add("The file %s is not a .pdf file".formatted(file));
        }

        return messages;
    }
}