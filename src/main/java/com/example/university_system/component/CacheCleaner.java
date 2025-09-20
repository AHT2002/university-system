package com.example.university_system.component;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheCleaner {
    private final CacheManager cacheManager;

    public CacheCleaner(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }

    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
