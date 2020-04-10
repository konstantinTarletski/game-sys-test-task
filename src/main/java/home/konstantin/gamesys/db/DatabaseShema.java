package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import home.konstantin.gamesys.enums.RrsEnum;
import home.konstantin.gamesys.model.Rrs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.function.BiFunction;
import java.util.function.Function;

import static home.konstantin.gamesys.utils.Utils.javaDateToLocalDateTime;
import static home.konstantin.gamesys.utils.Utils.resourceAsString;
import static java.lang.String.format;

@Slf4j
@Service
public class DatabaseShema {

    @Autowired
    private ConnectionConfiguration connectionConfiguration;

    @Value("classpath:migrations/creating-database-scheme.sql")
    private Resource table;

    @Value("classpath:migrations/insert-into-rrs.sql")
    private Resource insert;

    @Value("classpath:migrations/select-rss.sql")
    private Resource select;

    //TODO replace with index
    private int id = 0;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            connectionConfiguration.getUrl(),
            connectionConfiguration.getUsername(),
            connectionConfiguration.getPassword());
    }

    public <R,I> List<R> executeAnySql(String sql, Function<ResultSet,List<R>> resultProcessor, BiFunction<R,PreparedStatement, PreparedStatement> parametersProcessor){
        Connection connection = null;
        Statement stmt = null;
        try {
            //Class.forName(connectionConfiguration.getDriverClassName());
            connection = getConnection();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return new ArrayList<R>();
    }

    public List<Rrs> selectRrs(Connection connection) throws SQLException, IOException {
        List<Rrs> rrsList = new ArrayList<>();
        String sql = resourceAsString(select);
        var preparedStatement = connection.prepareStatement(sql);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            rrsList.add(getRrsFromResultSet(resultSet));
        }
        preparedStatement.close();
        return rrsList;
    }

    public void insertRrs(Connection connection, Rrs prs) throws SQLException, IOException {
        var sql = resourceAsString(insert);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, ++id);//TODO - id
        preparedStatement.setString(2, prs.getTitle());
        preparedStatement.setString(3, prs.getDescription());
        preparedStatement.setString(4, prs.getUri());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(prs.getPublishedDate()));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void createRrsTable(Connection connection) throws SQLException, IOException {
        String sql = resourceAsString(table);
        var statement =  connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    private Rrs getRrsFromResultSet(ResultSet resultSet) throws SQLException {
        return Rrs.builder().description(resultSet.getNString(RrsEnum.DESCRIPTION.name()))
            .uri(resultSet.getNString(RrsEnum.URI.name()))
            .publishedDate(resultSet.getTimestamp(RrsEnum.PUBLISHED_DATE.name()).toLocalDateTime())
            .title(resultSet.getNString(RrsEnum.TITLE.name()))
            .build();
    }

    public List<Rrs> selectRrs() {
        Connection connection = null;
        Statement stmt = null;
        try {
            //Class.forName(connectionConfiguration.getDriverClassName());
            connection = getConnection();
            return selectRrs(connection);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return List.of();
    }

    public void insertRrs() {
        Connection connection = null;
        Statement stmt = null;
        try {
            //Class.forName(connectionConfiguration.getDriverClassName());
            connection = getConnection();
            insertRrs(connection, Rrs.builder().description("3333").title("tttt").uri("54645645").publishedDate(
                LocalDateTime.now()).build());

            connection.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    public void buildSchema() {
        Connection connection = null;
        Statement stmt = null;
        try {

            //Class.forName(connectionConfiguration.getDriverClassName());
            connection = getConnection();
            createRrsTable(connection);

            connection.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

}

