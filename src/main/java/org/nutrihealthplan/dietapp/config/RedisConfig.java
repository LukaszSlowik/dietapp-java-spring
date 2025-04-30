package org.nutrihealthplan.dietapp.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final ObjectMapper objectMapper;

@Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory){

    Jackson2JsonRedisSerializer<Object> serializerDefault = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    JavaType publicProductResponseListType = objectMapper.getTypeFactory().constructCollectionType(List.class, PublicProductResponse.class);
    Jackson2JsonRedisSerializer<List<PublicProductResponse>> publicProductResponseListSerializer =
            new Jackson2JsonRedisSerializer<>(objectMapper, publicProductResponseListType);

    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializerDefault));
    RedisCacheConfiguration publicProductsListConfig = defaultConfig
            .entryTtl(Duration.ofHours(2))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(publicProductResponseListSerializer));


    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();


    cacheConfigurations.put("globalProductListCache", publicProductsListConfig);

    return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultConfig)
            .withInitialCacheConfigurations(cacheConfigurations)
            .transactionAware()
            .build();
}

}