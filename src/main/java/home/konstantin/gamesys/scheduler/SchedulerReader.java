package home.konstantin.gamesys.scheduler;

import home.konstantin.gamesys.reader.RrsReader;
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

    private final RrsReader rrsReader;

    @Getter
    @Setter
    private volatile boolean enabled = false;

    @Scheduled(fixedDelayString = "${test-data.rrs.source.schedule.fixed-delay}", initialDelay = 1000)
    public void scheduler() {
        log.info("Entering scheduler, enabled = {}", enabled);
        if (enabled) {
            try {
                rrsReader.read();
            }catch (Exception e){
                log.error("Error while reading rrs", e);
            }
            log.info("Reading complete");
        }
    }

}
