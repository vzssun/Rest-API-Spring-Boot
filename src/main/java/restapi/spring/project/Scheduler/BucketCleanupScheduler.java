package restapi.spring.project.Scheduler;

import org.springframework.stereotype.Component;
import restapi.spring.project.Configs.RateLimitFilter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class BucketCleanupScheduler {
    private final RateLimitFilter rateLimitFilter;
    private static final Logger log = LoggerFactory.getLogger(BucketCleanupScheduler.class);


    public BucketCleanupScheduler(RateLimitFilter rateLimitFilter) {
        this.rateLimitFilter = rateLimitFilter;
    }

    @Scheduled(fixedRate = 300000)
    public void cleanupOldBuckets() {
        log.info("=== Starting bucket cleanup ===");
        
        LocalDateTime threshold = LocalDateTime.now().minus(10, ChronoUnit.MINUTES);
        
        int sizeBefore = rateLimitFilter.getBuckets().size();
        log.info("Buckets before cleanup: {}", sizeBefore);
        
        rateLimitFilter.getBuckets().entrySet().removeIf(entry -> 
            entry.getValue().getLastAccessTime().isBefore(threshold)
        );
        
        int sizeAfter = rateLimitFilter.getBuckets().size();
        log.info("Buckets after cleanup: {}", sizeAfter);
        log.info("Removed {} buckets", sizeBefore - sizeAfter);
    }
}