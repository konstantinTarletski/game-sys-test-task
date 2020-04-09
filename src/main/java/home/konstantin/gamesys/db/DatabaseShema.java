package home.konstantin.gamesys.db;

import home.konstantin.gamesys.config.ConnectionConfiguration;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseShema {

    @Autowired
    private ConnectionConfiguration connectionConfiguration;

    @Value("classpath:migrations/creating-database-scheme.sql")
    private Resource table;

    @Value("classpath:migrations/instert-into-rss.sql")
    private Resource insert;


    public void buildSchema() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(connectionConfiguration.getDriverClassName());

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(
                connectionConfiguration.getUrl(),
                connectionConfiguration.getUsername(),
                connectionConfiguration.getPassword());

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();

            String sql = asString(table);
            System.out.println("SQL = " + sql);
            stmt.executeUpdate(sql);
            System.out.println("Database SHEMA successfully...");

            sql = asString(insert);
            System.out.println("SQL = " + sql);
            stmt.executeUpdate(sql);
            System.out.println("Database insert...");

            PreparedStatement s1 = conn.prepareStatement("select * from " + "rrs");
            ResultSet rs = s1.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++){
                    System.out.println("select = " + rs.getObject(i));
                }
            }
            System.out.println("Database select...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main

    public String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void copy(String table, Connection from, Connection to) throws SQLException {
        try (PreparedStatement s1 = from.prepareStatement("select * from " + table);
            ResultSet rs = s1.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();

            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= meta.getColumnCount(); i++)
                columns.add(meta.getColumnName(i));

            try (PreparedStatement s2 = to.prepareStatement(
                "INSERT INTO " + table + " ("
                    + columns.stream().collect(Collectors.joining(", "))
                    + ") VALUES ("
                    + columns.stream().map(c -> "?").collect(Collectors.joining(", "))
                    + ")"
            )) {

                while (rs.next()) {
                    for (int i = 1; i <= meta.getColumnCount(); i++)
                        s2.setObject(i, rs.getObject(i));

                    s2.addBatch();
                }

                s2.executeBatch();
            }
        }
    }

}

