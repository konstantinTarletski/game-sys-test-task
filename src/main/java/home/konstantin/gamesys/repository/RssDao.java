package home.konstantin.gamesys.repository;

import home.konstantin.gamesys.db.SqlInsert;
import home.konstantin.gamesys.db.SqlSelect;
import home.konstantin.gamesys.db.SqlUpdate;
import home.konstantin.gamesys.enums.RssEnum;
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
public class RssDao {

    private final SqlInsert sqlInsert;
    private final SqlUpdate sqlUpdate;
    private final SqlSelect sqlSelect;

    @Value("classpath:sql/creating-database-scheme.sql")
    private Resource table;

    @Value("classpath:sql/insert-into-rrs.sql")
    private Resource insert;

    @Value("classpath:sql/select-rss.sql")
    private Resource select;

    @PostConstruct
    public void createDatabaseSchema() {
        log.info("Creating database schema");
        sqlUpdate.update(table);
    }

    public void insertRows(List<Rss> rssList) {
        sqlInsert.insert(insert, rssList, (preparedStatement, rss) -> {
            preparedStatement.setString(1, rss.getTitle());
            preparedStatement.setString(2, rss.getDescription());
            preparedStatement.setString(3, rss.getUri());
            preparedStatement.setTimestamp(4,
                rss.getPublishedDate() == null ? null : Timestamp.valueOf(rss.getPublishedDate())
            );
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        });
    }

    public List<Rss> readData() {
        return sqlSelect.select(select, resultSet ->
            Rss.builder()
                .description(resultSet.getNString(RssEnum.DESCRIPTION.name()))
                .uri(resultSet.getNString(RssEnum.URI.name()))
                .publishedDate(getDate(resultSet, RssEnum.PUBLISHED_DATE))
                .title(resultSet.getNString(RssEnum.TITLE.name()))
                .insertedDate(getDate(resultSet, RssEnum.INSERTED_DATE))
                .build()
        );
    }

    private LocalDateTime getDate(ResultSet resultSet, RssEnum rssEnum) throws SQLException {
        var date = resultSet.getTimestamp(rssEnum.name());
        return date == null ? null : date.toLocalDateTime();
    }

}
