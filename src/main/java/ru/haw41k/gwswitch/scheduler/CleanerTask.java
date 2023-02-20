package ru.haw41k.gwswitch.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import ru.haw41k.gwswitch.tools.Cleaner;

@Configuration
@ConditionalOnProperty(name = "gw-switch.auto-clean")
public class CleanerTask {

    private final Cleaner cleaner;

    public CleanerTask(Cleaner cleaner) {
        this.cleaner = cleaner;
    }

    @Scheduled(cron = "@daily")
    public void clean() {
        cleaner.clean();
    }
}
