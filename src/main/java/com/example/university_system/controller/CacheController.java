package com.example.university_system.controller;

import com.example.university_system.component.CacheCleaner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {
    private final CacheCleaner cacheCleaner;

    public CacheController(CacheCleaner cacheCleaner) {
        this.cacheCleaner = cacheCleaner;
    }

    @Operation(summary = "حذف کش با نام دریافتی از کاربر", tags = {"حذف کش"})
    @GetMapping(value = "/clear-cache")
    public String clearSpecificCache(@Parameter(name="cache-name", description = "نام کش")
                                     @RequestParam("cache-name") String cacheName) {
        cacheCleaner.clearCache(cacheName);
        return "Cache cleared for "+ cacheName;
    }

    @Operation(summary = "حذف تمام کش ها", tags = {"حذف کش"})
    @GetMapping(value = "/clear-cache/all")
    public String clearAllCaches() {
        cacheCleaner.clearAllCaches();
        return "All caches cleared";
    }
}
