package home.konstantin.gamesys.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RssReaderTest {

    private RssReader rssReader = new RssReader();

    @Test
    public void successfulProcessRead() {

        ReflectionTestUtils.setField(rssReader, "url", "http://rss.cnn.com/rss/edition_world.rss");

        var result = rssReader.read();

        assertTrue(result.size() > 0);
        assertTrue(result.get(1).getTitle() != null);
        assertFalse(result.get(1).getTitle().isBlank());
        assertTrue(result.get(1).getDescription() != null);
        assertFalse(result.get(1).getDescription().isBlank());
    }

}
