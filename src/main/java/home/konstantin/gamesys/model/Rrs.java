package home.konstantin.gamesys.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rrs {

    private String title;
    private String description;
    private LocalDateTime publishedDate;
    private String uri;
    private LocalDateTime insertedDate;

}
