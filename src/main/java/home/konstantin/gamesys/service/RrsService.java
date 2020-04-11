package home.konstantin.gamesys.service;

import home.konstantin.gamesys.db.DatabaseManager;
import home.konstantin.gamesys.enums.RrsEnum;
import home.konstantin.gamesys.model.Rrs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RrsService {

    private final DatabaseManager databaseManager;

    @Value("classpath:migrations/creating-database-scheme.sql")
    private Resource table;

    @Value("classpath:migrations/insert-into-rrs.sql")
    private Resource insert;

    @Value("classpath:migrations/select-rss.sql")
    private Resource select;

    public void createTable(){
        databaseManager.executeSql(table, null, null, null);
    }

    public void insertRow(){
        databaseManager.executeSql(insert, getRrs(), (preparedStatement, rrs) -> {
            preparedStatement.setString(1, rrs.getTitle());
            preparedStatement.setString(2, rrs.getDescription());
            preparedStatement.setString(3, rrs.getUri());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(rrs.getPublishedDate()));

        }, null);
    }

    public List<Rrs> readData(){
        return databaseManager.executeSql(select, null, null, resultSet ->
            Rrs.builder()
                .description(resultSet.getNString(RrsEnum.DESCRIPTION.name()))
                .uri(resultSet.getNString(RrsEnum.URI.name()))
                .publishedDate(resultSet.getTimestamp(RrsEnum.PUBLISHED_DATE.name()).toLocalDateTime())
                .title(resultSet.getNString(RrsEnum.TITLE.name()))
                .build()
        );
    }

    public Rrs getRrs(){
        return Rrs.builder().description("3333").title("tttt").uri("54645645").publishedDate(
            LocalDateTime.now()).build();
    }


}
