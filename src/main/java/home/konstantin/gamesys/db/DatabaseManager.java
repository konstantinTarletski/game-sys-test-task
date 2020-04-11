package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static home.konstantin.gamesys.utils.Utils.resourceAsString;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseManager {

    private final ConnectionConfiguration connectionConfiguration;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            connectionConfiguration.getUrl(),
            connectionConfiguration.getUsername(),
            connectionConfiguration.getPassword());
    }

    public <T> List<T> executeSql(Resource sql, T parameters,
        StatementParameterPopulator<T> statementParameterPopulator, ResultSetReader<T> resultSetReader
    ) {
        List<T> result = new ArrayList<>();
        try (var connection = getConnection();
            var preparedStatement = connection.prepareStatement(resourceAsString(sql))
        ) {
            if (statementParameterPopulator != null && parameters != null) {
                statementParameterPopulator.populate(preparedStatement, parameters);
            }
            if (resultSetReader == null) {
                preparedStatement.executeUpdate();
            } else {
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(resultSetReader.read(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            String logText = format("There was error while executing SQL, error message is %s", e.getMessage());
            log.error(logText, e);
        }
        return result;
    }

}
