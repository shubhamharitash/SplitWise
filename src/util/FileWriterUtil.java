package util;

import contants.ShowConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class FileWriterUtil {
    public static void writeOutputToFile(List<String> showList) {
        try {
            Path filePath = Paths.get(ShowConstants.outputFilePath);
            for (String str : showList) {
                Files.write(filePath, Collections.singleton(str),
                        new StandardOpenOption[]{StandardOpenOption.APPEND});
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}
