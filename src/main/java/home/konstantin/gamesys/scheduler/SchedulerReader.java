package home.konstantin.gamesys.scheduler;

import home.konstantin.gamesys.service.RrsReader;
import home.konstantin.gamesys.service.RrsService;
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

    private final RrsService rrsService;

    @Getter
    @Setter
    private volatile boolean enabled = false;

    @Scheduled(fixedDelayString = "${test-data.rrs.source.schedule.fixed-delay}", initialDelay = 1000)
    public void scheduler() {
        if (enabled) {
            try {
                rrsService.processRrs();
            }catch (Exception e){
                log.error("Error while reading rrs", e);
            }
            log.info("Reading complete");
        }
    }

}
