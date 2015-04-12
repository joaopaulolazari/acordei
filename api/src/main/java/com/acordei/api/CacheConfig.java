package com.acordei.api;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    @Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration responseCache = createCache("RESPONSE_CACHE", 10000);
        CacheConfiguration assiduidade = createCache("ASSIDUIDADE_RESPONSE_CACHE", 10000);
        CacheConfiguration gastos = createCache("GASTOS_RESPONSE_CACHE", 50000);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(responseCache);
        config.addCache(assiduidade);
        config.addCache(gastos);
        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    private CacheConfiguration createCache(String cacheName, int maxEntriesLocalHeap) {
        CacheConfiguration newCacheArea = new CacheConfiguration();
        newCacheArea.setName(cacheName);
        newCacheArea.setMemoryStoreEvictionPolicy("LRU");
        newCacheArea.setMaxEntriesLocalHeap(maxEntriesLocalHeap);
        return newCacheArea;
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }
}
