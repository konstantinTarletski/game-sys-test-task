package home.konstantin.gamesys.service;

import home.konstantin.gamesys.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UtilsTest {

    @Mock
    private Resource resource;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testJavaDateToLocalDateTimeNull() {
        assertNull(Utils.javaDateToLocalDateTime(null));
    }

    @Test
    public void testJavaDateToLocalDateTimeSuccess() {
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

    @Test(expected = IOException.class)
    public void testResourceAsStringIOException() throws IOException {
        assertNull(Utils.resourceAsString(resource));
    }

}
