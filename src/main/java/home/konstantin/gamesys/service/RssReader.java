package home.konstantin.gamesys.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import home.konstantin.gamesys.model.Rss;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static home.konstantin.gamesys.utils.Utils.javaDateToLocalDateTime;
import static java.lang.String.format;

@Slf4j
@Service
public class RssReader {

    @Value("${test-data.rrs.source}")
    private String url;

    public List<Rss> read() {
        log.info("Entering reading rrs from URL = {}", url);
        try (XmlReader reader = new XmlReader(new URL(url))){
            SyndFeed feed = new SyndFeedInput().build(reader);
            var entryList = feed.getEntries();

            log.info("Rrs entries reading completed title is = {}, total entries is = {}",
                feed.getTitle(), entryList.size());

            return entryList.stream()
                .map(entry -> getRssFromSyndEntry(entry))
                .collect(Collectors.toList());
        }catch (IOException | FeedException e){
            String logText = format("There was error while reading RRS, error message is %s", e.getMessage());
            log.error(logText, e);
        }
        return List.of();
    }

    protected Rss getRssFromSyndEntry(SyndEntry entry) {
        return Rss.builder()
            .title(entry.getTitle())
            .publishedDate(javaDateToLocalDateTime(entry.getPublishedDate()))
            .uri(entry.getUri())
            .description(entry.getDescription() == null ? null : entry.getDescription().getValue()).build();
    }

}
