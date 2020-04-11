package home.konstantin.gamesys.service;

import home.konstantin.gamesys.db.DatabaseManager;
import home.konstantin.gamesys.enums.RrsEnum;
import home.konstantin.gamesys.model.Rss;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final DatabaseManager databaseManager;

    @Value("classpath:migrations/creating-database-scheme.sql")
    private Resource table;

    @Value("classpath:migrations/insert-into-rrs.sql")
    private Resource insert;

    @Value("classpath:migrations/select-rss.sql")
    private Resource select;

    @PostConstruct
    public void handleMultipleEvents() {
        log.info("Creating database schema");
        databaseManager.executeSql(table, null, null, null);
    }

    public void insertRows(List<Rss> rssList) {
        databaseManager.executeSql(insert, rssList, (preparedStatement, rrs) -> {
            preparedStatement.setString(1, rrs.getTitle());
            preparedStatement.setString(2, rrs.getDescription());
            preparedStatement.setString(3, rrs.getUri());
            preparedStatement.setTimestamp(4,
                rrs.getPublishedDate() == null ? null : Timestamp.valueOf(rrs.getPublishedDate())
            );
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        }, null);
    }

    public List<Rss> readData() {
        return databaseManager.executeSql(select, null, null, resultSet ->
            Rss.builder()
                .description(resultSet.getNString(RrsEnum.DESCRIPTION.name()))
                .uri(resultSet.getNString(RrsEnum.URI.name()))
                .publishedDate(getDate(resultSet, RrsEnum.PUBLISHED_DATE))
                .title(resultSet.getNString(RrsEnum.TITLE.name()))
                .insertedDate(getDate(resultSet, RrsEnum.INSERTED_DATE))
                .build()
        );
    }

    private LocalDateTime getDate(ResultSet resultSet, RrsEnum rrsEnum) throws SQLException {
        var date = resultSet.getTimestamp(rrsEnum.name());
        return date == null ? null : date.toLocalDateTime();
    }

}
