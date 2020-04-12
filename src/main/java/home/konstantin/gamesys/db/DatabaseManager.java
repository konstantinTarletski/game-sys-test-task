package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static home.konstantin.gamesys.utils.Utils.resourceAsString;
import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public abstract class DatabaseManager {

    private final ConnectionConfiguration connectionConfiguration;

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            connectionConfiguration.getUrl(),
            connectionConfiguration.getUsername(),
            connectionConfiguration.getPassword());
    }

    protected <T> List<T> executeSql(Resource sql, List<T> parameters, StatementParameterPopulator<T> populator,
        ResultSetReader<T> resultSetReader
    ) {
        try (var connection = getConnection();
            var preparedStatement = connection.prepareStatement(resourceAsString(sql))
        ) {
            return processSql(parameters, populator, resultSetReader, preparedStatement);
        } catch (SQLException | IOException e) {
            String logText = format("There was error while executing SQL, error message is %s", e.getMessage());
            log.error(logText, e);
        }
        return List.of();
    }

    public abstract <T> List<T> processSql(List<T> parameters, StatementParameterPopulator<T> populator,
        ResultSetReader<T> resultSetReader, PreparedStatement preparedStatement) throws SQLException;

}
