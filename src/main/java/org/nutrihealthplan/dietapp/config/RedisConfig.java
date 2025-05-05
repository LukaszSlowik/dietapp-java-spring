package org.nutrihealthplan.dietapp.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
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

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        ObjectMapper redisMapper = new ObjectMapper();
        redisMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // Wyłączenie zapisywania dat jako timestamp
        redisMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Konfiguracja walidatora typów dla obiektów w org.nutrihealthplan
//        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
//                .allowIfSubType("org.nutrihealthplan") // Umożliwia deserializację tylko podtypów z tego pakietu
//                .build();
//        redisMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL); // Aktywacja polimorficznego typowania

        // Domyślny serializator dla ogólnych obiektów
        Jackson2JsonRedisSerializer<Object> serializerDefault = new Jackson2JsonRedisSerializer<>(redisMapper, Object.class);

        // Typy dla list obiektów PublicProductResponse i ProductBasicInfoResponse
        JavaType publicProductResponseListType = redisMapper.getTypeFactory().constructCollectionType(List.class, PublicProductResponse.class);
        JavaType productResponseListType = redisMapper.getTypeFactory().constructCollectionType(List.class, ProductBasicInfoResponse.class);

        // Serializatory dla list obiektów
        Jackson2JsonRedisSerializer<List<PublicProductResponse>> publicProductResponseListSerializer =
                new Jackson2JsonRedisSerializer<>(redisMapper, publicProductResponseListType);
        Jackson2JsonRedisSerializer<List<ProductBasicInfoResponse>> productResponseListSerializer =
                new Jackson2JsonRedisSerializer<>(redisMapper, productResponseListType);

        // Domyślna konfiguracja cache
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // Czas życia cache
                .disableCachingNullValues() // Wyłączenie cachowania wartości null
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializerDefault));

        // Dostosowanie konfiguracji dla konkretnych cache
        RedisCacheConfiguration publicProductsListConfig = defaultConfig
                .entryTtl(Duration.ofHours(2)) // Czas życia cache dla produktów
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(publicProductResponseListSerializer));

        RedisCacheConfiguration productListConfig = defaultConfig
                .entryTtl(Duration.ofHours(2)) // Czas życia cache dla podstawowych informacji produktów
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(productResponseListSerializer));

        // Mapowanie nazw cache do konfiguracji
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("globalProductListCache", publicProductsListConfig);
        cacheConfigurations.put("productListCache", productListConfig);

        // Tworzenie RedisCacheManager
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
    }
}
