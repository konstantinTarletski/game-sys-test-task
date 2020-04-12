package home.konstantin.gamesys.service;

import home.konstantin.gamesys.model.Rss;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RssProcessorTest {

    private RssProcessor rssProcessor = new RssProcessor();

    @Test
    public void successfulProcessRss() {
        var result = rssProcessor.processRss(getRrs());
        assertEquals(result.getDescription(), "cba");
        assertEquals(result.getTitle(), "ABC");
        assertEquals(result.getUri(), "abc");
    }

    public Rss getRrs() {
        return Rss.builder()
            .description("abc")
            .title("abc")
            .uri("abc")
            .publishedDate(LocalDateTime.now())
            .build();
    }
}
