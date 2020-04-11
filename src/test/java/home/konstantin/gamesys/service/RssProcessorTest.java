package home.konstantin.gamesys.service;

import home.konstantin.gamesys.model.Rss;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class RssProcessorTest {

    @InjectMocks
    private RssProcessor rssProcessor;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void successfulProcessPerson() {
        var result = rssProcessor.processRss(getRrs());
        assertEquals(result.getDescription(), "cba");
        assertEquals(result.getTitle(), "ABC");
        assertEquals(result.getUri(), "abc");
    }

    public Rss getRrs(){
        return Rss.builder()
            .description("abc")
            .title("abc")
            .uri("abc")
            .publishedDate(LocalDateTime.now())
            .build();
    }
}
