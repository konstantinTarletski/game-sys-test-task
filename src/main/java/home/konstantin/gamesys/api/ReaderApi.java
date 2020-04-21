package home.konstantin.gamesys.api;

import home.konstantin.gamesys.model.Rss;
import home.konstantin.gamesys.scheduler.SchedulerReader;
import home.konstantin.gamesys.repository.RssDao;
import home.konstantin.gamesys.service.RssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "GameSys rss-reader", tags = "rss-reader")
@RequestMapping("${application.api.base-mapping}")
@RequiredArgsConstructor
public class ReaderApi {

    private final SchedulerReader schedulerReader;
    private final RssService rssService;
    private final RssDao rssDao;

    @ApiOperation("Enable scheduler")
    @GetMapping(path = "/enable-scheduler")
    public String enableSender(@RequestParam boolean enabled) {
        schedulerReader.getStatus().set(enabled);
        var logText = format("Scheduler status now = %s", schedulerReader.getStatus().get());
        log.info(logText);
        return logText;
    }

    @ApiOperation("Processing RRS 1 time")
    @GetMapping(path = "/process-rss")
    public String insertRrs() {
        var logText = "Processing RRS manually";
        log.info(logText);
        rssService.processRss();
        return logText;
    }

    @ApiOperation("Read last 10 rows from database")
    @GetMapping(path = "/read-last-items")
    public List<Rss> readLastItems() {
        log.info("Reading last 10 rows from database");
        return rssDao.readData();
    }

}
