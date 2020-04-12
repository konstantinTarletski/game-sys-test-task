package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class SqlUpdate extends DatabaseManager {

    public SqlUpdate(ConnectionConfiguration connectionConfiguration) {
        super(connectionConfiguration);
    }

    public <T> void update(Resource sql) {
        executeSql(sql, null, null, null);
    }

    @Override
    public <T> List<T> processSql(List<T> parameters, StatementParameterPopulator<T> populator,
        ResultSetReader<T> resultSetReader, PreparedStatement preparedStatement
    ) throws SQLException {
        preparedStatement.executeUpdate();
        return null;
    }
}
