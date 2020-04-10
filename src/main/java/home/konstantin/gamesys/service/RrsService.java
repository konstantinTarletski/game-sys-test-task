package home.konstantin.gamesys.service;

import home.konstantin.gamesys.db.DatabaseShema;
import home.konstantin.gamesys.enums.RrsEnum;
import home.konstantin.gamesys.model.Rrs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class RrsService {

    private final DatabaseShema databaseShema;

    //TODO replace with index
    private int id = 0;

    void process() {
        databaseShema.executeAnySql("", resultProcessor(), parametersProcessor());
    }

    public BiFunction<Rrs, PreparedStatement, PreparedStatement> parametersProcessor() {
        return (rrs, preparedStatement) -> {
            try {
                preparedStatement.setLong(1, ++id);//TODO - id
                preparedStatement.setString(2, rrs.getTitle());
                preparedStatement.setString(3, rrs.getDescription());
                preparedStatement.setString(4, rrs.getUri());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(rrs.getPublishedDate()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return preparedStatement;
        };
    }

    public Function<ResultSet, List<Rrs>> resultProcessor() {
        return resultSet -> {
            List<Rrs> rrsList = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    rrsList.add(getRrsFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rrsList;
        };
    }

    private Rrs getRrsFromResultSet(ResultSet resultSet) throws SQLException {
        return Rrs.builder().description(resultSet.getNString(RrsEnum.DESCRIPTION.name()))
                .uri(resultSet.getNString(RrsEnum.URI.name()))
                .publishedDate(resultSet.getTimestamp(RrsEnum.PUBLISHED_DATE.name()).toLocalDateTime())
                .title(resultSet.getNString(RrsEnum.TITLE.name()))
                .build();
    }


}
