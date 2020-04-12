package home.konstantin.gamesys.utils;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UtilsTest {

    @Test
    public void testJavaDateToLocalDateTime() {
        var date = new Date();
        var dateTime = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        assertEquals(Utils.javaDateToLocalDateTime(date), dateTime);
        assertNull(Utils.javaDateToLocalDateTime(null));
    }

}
