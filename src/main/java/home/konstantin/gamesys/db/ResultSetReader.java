package home.konstantin.gamesys.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetReader<T> {

    T read(ResultSet resultSet) throws SQLException;

}
