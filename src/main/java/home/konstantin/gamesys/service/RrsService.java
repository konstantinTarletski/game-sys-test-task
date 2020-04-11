package home.konstantin.gamesys.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RrsService {

    private final RrsReader rrsReader;
    private final DatabaseService databaseService;
    private final RrsProcessor rrsProcessor;

    public void processRrs() {
        databaseService.insertRows(
            rrsReader.read().stream()
                .map(rrsProcessor::processRrs)
                .collect(Collectors.toList()));

    }

}
