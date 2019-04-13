package pw.wp6.avocado_toast;


import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

public class Util {
    public static OffsetDateTime dbStringToOffsetDateTime(String input) {
        // replace space in the middle with ISO8601 T
        input = input.replace(' ', 'T');
        LocalDateTime localTime = LocalDateTime.parse(input);
        // all database dates are stored as UTC
        return OffsetDateTime.of(localTime, ZoneOffset.UTC);
    }
}
