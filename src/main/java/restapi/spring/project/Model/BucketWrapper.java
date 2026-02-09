package restapi.spring.project.Model;

import io.github.bucket4j.Bucket;
import java.time.LocalDateTime;

public class BucketWrapper {
    private final Bucket bucket;
    private LocalDateTime lastAccessTime;
    
    public BucketWrapper(Bucket bucket) {
        this.bucket = bucket;
        this.lastAccessTime = LocalDateTime.now();
    }

    public Bucket getBucket() {
        return bucket;
    }

    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }
}