package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SqlSelect extends DatabaseManager {

    public SqlSelect(ConnectionConfiguration connectionConfiguration) {
        super(connectionConfiguration);
    }

    public <T> List<T> select(Resource sql, ResultSetReader<T> resultSetReader) {
        return executeSql(sql, null, null, resultSetReader);
    }

    @Override
    public <T> List<T> processSql(List<T> parameters, StatementParameterPopulator<T> populator,
        ResultSetReader<T> resultSetReader, PreparedStatement preparedStatement
    ) throws SQLException {
        List<T> result = new ArrayList<>();
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            result.add(resultSetReader.read(resultSet));
        }
        return result;
    }
}