package home.konstantin.gamesys.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementParameterPopulator<T> {

    void populate(PreparedStatement preparedStatement, T object) throws SQLException;

}
