package home.konstantin.gamesys.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class UtilsTest {

    private final String TEXT = "Hello World";

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
        when(resource.getInputStream()).thenThrow(IOException.class);
        assertNull(Utils.resourceAsString(resource));
    }

    @Test
    public void testResourceAsStringSucess() throws IOException {
        when(resource.getInputStream())
            .thenReturn(new ByteArrayInputStream(TEXT.getBytes(StandardCharsets.UTF_8)));
        var text = Utils.resourceAsString(resource);
        assertEquals(text, TEXT);
    }

}
