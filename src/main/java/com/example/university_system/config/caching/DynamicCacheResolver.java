package com.example.university_system.config.caching;

import com.example.university_system.service.BaseService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import java.util.Collection;
import java.util.Collections;

public class DynamicCacheResolver extends SimpleCacheResolver {
    public DynamicCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Object target = context.getTarget();
        if (target instanceof BaseService) {
            BaseService<?, ?> baseService = (BaseService<?, ?>) target;
            String cacheName = context.getOperation().getCacheNames().contains("cacheName")
                    ? baseService.getCacheName()
                    : baseService.getAllCacheName();
            return Collections.singletonList(cacheName);
        }
        return context.getOperation().getCacheNames();
    }
}
