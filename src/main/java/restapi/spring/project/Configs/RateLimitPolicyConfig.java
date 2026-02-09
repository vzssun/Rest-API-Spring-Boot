package restapi.spring.project.Configs;

import java.time.Duration;

public class RateLimitPolicyConfig {
    private String path;
    private int capacity;
    private int refillTokens;
    private String refillDuration;  // ← This one is a String!

    // Getters and setters
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(int refillTokens) {
        this.refillTokens = refillTokens;
    }

    public String getRefillDuration() {
        return refillDuration;
    }

    public void setRefillDuration(String refillDuration) {
        this.refillDuration = refillDuration;
    }

    // This method parses the String → Duration
    public Duration getRefillDurationAsDuration() {
        if (refillDuration.endsWith("m")) {
            long minutes = Long.parseLong(refillDuration.replace("m", ""));
            return Duration.ofMinutes(minutes);
        } else if (refillDuration.endsWith("s")) {
            long seconds = Long.parseLong(refillDuration.replace("s", ""));
            return Duration.ofSeconds(seconds);
        }
        throw new IllegalArgumentException("Invalid duration format: " + refillDuration);
    }
}