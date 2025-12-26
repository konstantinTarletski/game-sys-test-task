package home.konstantin.gamesys.service;

import home.konstantin.gamesys.model.Rss;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RssProcessor {

    private final int DB_ROW_LENGTH = 1000;

    public Rss processRss(Rss rss) {
        rss.setTitle(processTitle(rss.getTitle()));
        rss.setDescription(processDescription(rss.getDescription()));
        rss.setUri(trimToSize(rss.getUri()));
        return rss;
    }

    private String processTitle(String title) {
        return trimToSize(title.toUpperCase());
    }

    private String processDescription(String description) {
        if (description != null) {
            StringBuilder buffer = new StringBuilder(description);
            return trimToSize(buffer.reverse().toString());
        }
        return "";
    }

    private String trimToSize(String input) {
        return input.substring(0, Math.min(input.length(), DB_ROW_LENGTH));
    }

}
