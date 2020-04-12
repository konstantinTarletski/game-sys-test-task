package home.konstantin.gamesys.scheduler;

import home.konstantin.gamesys.service.RssService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerReader {

    private final RssService rssService;

    @Getter
    @Setter
    private volatile boolean enabled = false;

    @Scheduled(fixedDelayString = "${test-data.rrs.source.schedule.fixed-delay}", initialDelay = 1000)
    public void scheduler() {
        if (enabled) {
            rssService.processRss();
            log.info("Reading complete");
        }
    }

}
