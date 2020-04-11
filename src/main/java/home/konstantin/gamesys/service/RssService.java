package home.konstantin.gamesys.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssService {

    private final RssReader rssReader;
    private final DatabaseService databaseService;
    private final RrsProcessor rrsProcessor;

    public void processRss() {
        databaseService.insertRows(
            rssReader.read().stream()
                .map(rrsProcessor::processRss)
                .collect(Collectors.toList()));

    }

}
