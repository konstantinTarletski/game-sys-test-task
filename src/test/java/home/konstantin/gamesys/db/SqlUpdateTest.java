package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class SqlUpdateTest {

    private final String TEST_SQL = "ABC";

    private SqlUpdate sqlUpdate;

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
        when(Utils.resourceAsString(resource)).thenReturn(TEST_SQL);

        sqlUpdate = new SqlUpdate(connectionConfiguration);
        sqlUpdate = Mockito.spy(sqlUpdate);
        doReturn(connection).when(sqlUpdate).getConnection();
    }

    @Test
    public void testUpdate() throws Exception {
        when(connection.prepareStatement(TEST_SQL)).thenReturn(preparedStatement);
        sqlUpdate.update(resource);
        verify(preparedStatement, times(1)).executeUpdate();
    }

}
