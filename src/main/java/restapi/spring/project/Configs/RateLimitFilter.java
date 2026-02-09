package restapi.spring.project.Configs;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import restapi.spring.project.Model.BucketWrapper;
import restapi.spring.project.Model.RateLimitPolicy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.AntPathMatcher;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restapi.spring.project.Configs.RateLimitPolicyConfig;
import restapi.spring.project.Configs.RateLimitProperties;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    private final Map<String, BucketWrapper> buckets = new ConcurrentHashMap<>();
    private final AntPathMatcher matcher = new AntPathMatcher();
    
    private final Map<String, RateLimitPolicy> registry = new LinkedHashMap<>();

    public RateLimitFilter(RateLimitProperties properties) {
        for (RateLimitPolicyConfig config : properties.getPolicies()) {
            RateLimitPolicy policy = new RateLimitPolicy(
                config.getCapacity(),
                config.getRefillTokens(),
                config.getRefillDurationAsDuration()
            );
            registry.put(config.getPath(), policy);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String clientIp = getClientIp(request);

        // matching pattern and policy
        String matchedPattern = "/**"; // Default fallback
        RateLimitPolicy activePolicy = registry.get(matchedPattern);

        for (String pattern : registry.keySet()) {
            if (matcher.match(pattern, path)) {
                matchedPattern = pattern;
                activePolicy = registry.get(pattern);
                break; // first match
            }
        }

        // the unique key for the map
        String compositeKey = clientIp + ":" + matchedPattern;
        log.debug("Request from IP: {} matched pattern: {} with key: {}", clientIp, matchedPattern, compositeKey);

        // STEP C: Resolve the bucket and try to consume a token
        BucketWrapper wrapper = resolveBucket(compositeKey, activePolicy);
        Bucket bucket = wrapper.getBucket();
        
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            // 1. SUCCESS: Add headers using the probe's data
            log.debug("Token consumed for key: {}. Remaining tokens: {}", compositeKey, probe.getRemainingTokens());
            response.addHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
    
            // For the Limit, we can use the policy we found earlier!
            response.addHeader("X-RateLimit-Limit", String.valueOf(activePolicy.getCapacity()));
    
            // Reset time is usually in seconds
            long secondsToReset = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-RateLimit-Reset", String.valueOf(secondsToReset));
            filterChain.doFilter(request, response);
            } else {
                // 2. FAILURE: Even if they are blocked, they need to know when they can try again!
                log.warn("Rate limit exceeded for key: {}. Blocking request.", compositeKey);
                response.addHeader("X-RateLimit-Remaining", "0");
                long secondsToReset = probe.getNanosToWaitForRefill() / 1_000_000_000;
                response.addHeader("X-RateLimit-Reset", String.valueOf(secondsToReset));
                sendErrorResponse(response);
                return; // early return (good practice)
            }
        }

    private BucketWrapper resolveBucket(String key, RateLimitPolicy policy) {
        BucketWrapper wrapper = buckets.computeIfAbsent(key, k -> new BucketWrapper(createNewBucket(policy)));
        wrapper.updateLastAccessTime();
        return wrapper;
    }

    private Bucket createNewBucket(RateLimitPolicy policy) {
        // Use the 'policy' object to set capacity and refill
        return Bucket.builder()
                .addLimit(limit -> limit
                .capacity(policy.getCapacity())
                .refillIntervally(policy.getRefillTokens(), policy.getRefillDuration()) // att to not deprecated way
                )
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        // Check for IP in common proxy headers first
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // If multiple IPs in X-Forwarded-For, take the first one
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
    }

    public Map<String, BucketWrapper> getBuckets() {
        return buckets;
    }
}