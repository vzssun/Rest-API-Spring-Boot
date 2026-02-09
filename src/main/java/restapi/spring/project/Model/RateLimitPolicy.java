package restapi.spring.project.Model;

import java.time.Duration;

public class RateLimitPolicy {
    private final int capacity;
    private final int refillTokens;
    private final Duration refillDuration;

    public RateLimitPolicy(int capacity, int refillTokens, Duration refillDuration) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillDuration = refillDuration;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRefillTokens() {
        return refillTokens;
    }

    public Duration getRefillDuration() {
        return refillDuration;
    }
}