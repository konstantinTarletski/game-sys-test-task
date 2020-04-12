package home.konstantin.gamesys.service;

import home.konstantin.gamesys.model.Rss;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RssServiceTest {

    @Mock
    private RssReader rssReader;

    @Mock
    private DatabaseService databaseService;

    @Mock
    private RssProcessor rssProcessor;

    @InjectMocks
    private RssService rssService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessRss() {
        var rss = getRrs();
        var rssList = List.of(rss);

        when(rssReader.read()).thenReturn(rssList);
        when(rssProcessor.processRss(rss)).thenReturn(rss);

        rssService.processRss();

        verify(rssReader, times(1)).read();
        verify(rssProcessor, times(1)).processRss(rss);
        verify(databaseService, times(1)).insertRows(rssList);
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
