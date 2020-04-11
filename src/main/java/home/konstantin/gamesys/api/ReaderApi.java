package home.konstantin.gamesys.api;

import home.konstantin.gamesys.model.Rrs;
import home.konstantin.gamesys.scheduler.SchedulerReader;
import home.konstantin.gamesys.service.DatabaseService;
import home.konstantin.gamesys.service.RrsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("${application.api.base-mapping}")
@RequiredArgsConstructor
public class ReaderApi {

    private final SchedulerReader schedulerReader;
    private final RrsService rrsService;
    private final DatabaseService databaseService;

    @GetMapping(path = "/enable-scheduler")
    public String enableSender(@RequestParam boolean enabled) {
        schedulerReader.setEnabled(enabled);
        String logText = format("Scheduler status now = %s", schedulerReader.isEnabled());
        log.info(logText);
        return logText;
    }

    @GetMapping(path = "/process-rrs")
    public void insertRrs() {
        log.info("Processing RRS manually");
        rrsService.processRrs();
    }

    @GetMapping(path = "/read-last-items")
    public List<Rrs> readLastItems() {
        log.info("Reading last 10 rows from database");
        return databaseService.readData();
    }

}
