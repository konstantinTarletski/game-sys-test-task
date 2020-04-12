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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class SqlSelectTest {

    private SqlSelect sqlSelect;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ConnectionConfiguration connectionConfiguration;

    @Mock
    private Resource resource;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(Utils.class);
        when(Utils.resourceAsString(resource)).thenReturn("ABC");

        sqlSelect = new SqlSelect(connectionConfiguration);
        sqlSelect = Mockito.spy(sqlSelect);
        doReturn(connection).when(sqlSelect).getConnection();
    }

    @Test
    public void testSelect() throws Exception {
        var rss = getRrs();
        when(connection.prepareStatement("ABC")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(
            new Answer<Boolean>() {
                private int count = 0;
                public Boolean answer(InvocationOnMock invocation) {
                    return count++ < 1 ? true : false;
                }
            }
        );
        List<Rss> result = sqlSelect.select(resource, t -> rss);
        verify(preparedStatement, times(1)).executeQuery();
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).getDescription(), "abc");
        assertEquals(result.get(0).getTitle(), "abc");
        assertEquals(result.get(0).getUri(), "abc");
        assertNotNull(result.get(0).getPublishedDate());
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
