package home.konstantin.gamesys.reader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import home.konstantin.gamesys.model.Rrs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static home.konstantin.gamesys.utils.Utils.javaDateToLocalDateTime;

@Service
@Slf4j
public class RrsReader {

    @Value("${test-data.rrs.source}")
    private String url;

    public List<Rrs> read() throws IOException, IllegalArgumentException, FeedException {
        log.info("Entering reading rrs from URL = {}", url);

        XmlReader reader = new XmlReader(new URL(url));
        SyndFeed feed = new SyndFeedInput().build(reader);
        var entryList = feed.getEntries();

        log.info("Rrs entries reading completed title is = {}, total entries is = {}",
            feed.getTitle(), entryList.size());

        return entryList.stream()
            .map(entry -> getRrsFromSyndEntry(entry))
            .collect(Collectors.toList());
    }

    protected Rrs getRrsFromSyndEntry(SyndEntry entry) {
        return Rrs.builder()
            .title(entry.getTitle())
            .publishedDate(javaDateToLocalDateTime(entry.getPublishedDate()))
            .uri(entry.getUri())
            .description(entry.getDescription() == null ? null : entry.getDescription().getValue()).build();
    }

}
