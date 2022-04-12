package dev.colin.utilities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

// logger to record errors
public class Logger {

    public static void log(String message, LogLevel level) {

        String logMessage = "\n" + level.name() + " " + message + " " + new Date();

        try {
            Files.write(Paths.get("C:\\Users\\Colin\\Desktop\\colin_buckley_p0\\appslog.log"),
                    logMessage.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
