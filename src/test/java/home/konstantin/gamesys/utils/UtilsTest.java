package home.konstantin.gamesys.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class UtilsTest {

    @Test
    public void testJavaDateToLocalDateTime() {
        assertNull(Utils.javaDateToLocalDateTime(null));
        var date = new Date();
        var dateTime = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        assertEquals(Utils.javaDateToLocalDateTime(date), dateTime);
    }

    @Test
    public void testResourceAsString() throws IOException {
        assertNull(Utils.resourceAsString(null));
    }

}
