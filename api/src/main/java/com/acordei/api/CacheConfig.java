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
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("RESPONSE_CACHE");
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheConfiguration.setMaxEntriesLocalHeap(10000);

        CacheConfiguration cacheAssiduidadeConfiguration = new CacheConfiguration();
        cacheAssiduidadeConfiguration.setName("ASSIDUIDADE_RESPONSE_CACHE");
        cacheAssiduidadeConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheAssiduidadeConfiguration.setMaxEntriesLocalHeap(10000);

        CacheConfiguration cacheDashboardConfiguration = new CacheConfiguration();
        cacheDashboardConfiguration.setName("GASTOS_RESPONSE_CACHE");
        cacheDashboardConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheDashboardConfiguration.setMaxEntriesLocalHeap(50000);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);
        config.addCache(cacheAssiduidadeConfiguration);
        config.addCache(cacheDashboardConfiguration);
        return net.sf.ehcache.CacheManager.newInstance(config);
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
