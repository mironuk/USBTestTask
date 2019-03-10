package usbtesttask.util;

import static java.lang.System.err;
import static java.lang.System.out;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class Output {

    public static void info(String message) {
        out.println(getTimestamp() + "   " + message);
    }

    public static void error(String message) {
        err.println(getTimestamp() + "   " + message);
    }

    public static String getTimestamp() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = formatter.format(ldt);
        return timestamp;
    }

}
