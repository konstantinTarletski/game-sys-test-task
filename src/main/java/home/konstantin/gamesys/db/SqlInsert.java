package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class SqlInsert extends DatabaseManager {

    public SqlInsert(ConnectionConfiguration connectionConfiguration) {
        super(connectionConfiguration);
    }

    public <T> void insert(Resource sql, List<T> parameters, StatementParameterPopulator<T> populator) {
        executeSql(sql, parameters, populator, null);
    }

    @Override
    public <T> List<T> processSql(List<T> parameters, StatementParameterPopulator<T> populator,
        ResultSetReader<T> resultSetReader, PreparedStatement preparedStatement
    ) throws SQLException {
        if (populator != null && parameters != null) {
            for (var parameter : parameters) {
                populator.populate(preparedStatement, parameter);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return null;
    }

}
