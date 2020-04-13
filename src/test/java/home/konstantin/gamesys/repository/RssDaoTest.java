package home.konstantin.gamesys.repository;

import home.konstantin.gamesys.db.ResultSetReader;
import home.konstantin.gamesys.db.SqlInsert;
import home.konstantin.gamesys.db.SqlSelect;
import home.konstantin.gamesys.db.SqlUpdate;
import home.konstantin.gamesys.model.Rss;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RssDaoTest {

    @Mock
    private SqlInsert sqlInsert;

    @Mock
    private SqlUpdate sqlUpdate;

    @Mock
    private SqlSelect sqlSelect;

    @Mock
    private Resource resource;

    @InjectMocks
    private RssDao rssDao;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void successfulSelect() {
        var rss = getRrs();
        var rssList = List.of(rss);
        ReflectionTestUtils.setField(rssDao, "select", resource);

        when(sqlSelect.select(notNull(), (ResultSetReader<Rss>) notNull())).thenReturn(rssList);
        var result = rssDao.readData();

        verify(sqlSelect, times(1)).select(notNull(), notNull());
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).getDescription(), "abc");
        assertEquals(result.get(0).getTitle(), "abc");
        assertEquals(result.get(0).getUri(), "abc");
        assertNotNull(result.get(0).getPublishedDate());
    }

    @Test
    public void successfulUpdate() {
        ReflectionTestUtils.setField(rssDao, "table", resource);
        rssDao.cratingDatabaseSchema();
        verify(sqlUpdate, times(1)).update(notNull());
    }

    @Test
    public void successfulInsert() {
        var rss = getRrs();
        var rssList = List.of(rss);
        ReflectionTestUtils.setField(rssDao, "insert", resource);
        rssDao.insertRows(rssList);
        verify(sqlInsert, times(1)).insert(notNull(), eq(rssList), notNull());
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
