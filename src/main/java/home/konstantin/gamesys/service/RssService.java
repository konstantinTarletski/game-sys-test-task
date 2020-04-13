package home.konstantin.gamesys.service;

import home.konstantin.gamesys.repository.RssDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssService {

    private final RssReader rssReader;
    private final RssDao rssDao;
    private final RssProcessor rssProcessor;

    public void processRss() {
        rssDao.insertRows(
            rssReader.read().stream()
                .map(rssProcessor::processRss)
                .collect(Collectors.toList()));

    }

}
