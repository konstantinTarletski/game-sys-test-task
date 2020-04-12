package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import home.konstantin.gamesys.model.Rss;
import home.konstantin.gamesys.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class SqlInsertTest {

    private SqlInsert sqlInsert;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ConnectionConfiguration connectionConfiguration;

    @Mock
    private Resource resource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(Utils.class);
        when(Utils.resourceAsString(resource)).thenReturn("ABC");

        sqlInsert = new SqlInsert(connectionConfiguration);
        sqlInsert = Mockito.spy(sqlInsert);
        doReturn(connection).when(sqlInsert).getConnection();
    }

    @Test
    public void testInsert() throws Exception {
        var rss = getRrs();
        var rssList = List.of(rss);
        when(connection.prepareStatement("ABC")).thenReturn(preparedStatement);
        sqlInsert.insert(resource, rssList, (t, s) -> {});
        verify(preparedStatement, times(1)).addBatch();
        verify(preparedStatement, times(1)).executeBatch();
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
