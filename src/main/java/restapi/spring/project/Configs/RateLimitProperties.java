package restapi.spring.project.Configs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {
    private List<RateLimitPolicyConfig> policies;

    public List<RateLimitPolicyConfig> getPolicies() {
        return policies;
    }

    public void setPolicies(List<RateLimitPolicyConfig> policies) {
        this.policies = policies;
    }
}
