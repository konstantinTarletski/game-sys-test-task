package home.konstantin.gamesys;

import home.konstantin.gamesys.model.Rss;
import home.konstantin.gamesys.repository.RssDao;
import home.konstantin.gamesys.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssDaoIntegrationTest {

    private static final String TITLE = "title";
    private static final String URI = "uri";
    private static final String DESCRIPTION = "description";

    @Autowired
    private RssDao rssDao;

    @Test
    public void testDao() {
        var rss = getRrs();
        var rssList = List.of(rss);

        rssDao.insertRows(rssList);
        var resultList = rssDao.readData();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertEquals(resultList.size(), 1);
        assertEquals(resultList.get(0).getTitle(), TITLE);
        assertEquals(resultList.get(0).getDescription(), DESCRIPTION);
        assertEquals(resultList.get(0).getUri(), URI);
    }

    public Rss getRrs() {
        return Rss.builder()
            .description(DESCRIPTION)
            .title(TITLE)
            .uri(URI)
            .publishedDate(LocalDateTime.now())
            .build();
    }

}
