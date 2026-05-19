package restapi.spring.project.Configs;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {

        CaffeineCacheManager manager = new CaffeineCacheManager();
        
        

        manager.registerCustomCache("catalogo", 
            Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(500)
            .build()
        );
        manager.registerCustomCache("categorias", 
        Caffeine.newBuilder()
        .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(100)
            .build()
        );
        manager.registerCustomCache("disponibilidade", 
            Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(200)
            .build()
        );
        manager.registerCustomCache("usuarios", 
            Caffeine.newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .maximumSize(200)
            .build()
        );
        
        return manager;
        
    }

}
