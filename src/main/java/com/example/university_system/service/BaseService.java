package com.example.university_system.service;

import com.example.university_system.entity.BaseEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseService<T extends BaseEntity, ID, U> {

    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract Messages getNotFoundMessage();
    public abstract String getCacheName();
    public abstract String getAllCacheName();
    protected abstract void updateEntity(U updateDto, T existingEntity);
    protected abstract Map<Messages, Function<Object, Optional<T>>> getUniqueFieldCheckers();
    protected abstract Object getFieldValue(T entity, Messages message);


    @CacheEvict(cacheNames = {"#this.getCacheName()", "#this.getAllCacheName()"},
            allEntries = true,
            cacheResolver = "cacheResolver")
    public T save(T entity) {
        try {
            if (entity.getId() == null) {
                checkUniqueFields(entity);
            }
            return getRepository().save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("نقض یکپارچگی داده‌ها: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("خطا در ذخیره موجودیت: " + e.getMessage(), e);
        }
    }

    private void checkUniqueFields(T entity) {
        Map<Messages, Function<Object, Optional<T>>> checkers = getUniqueFieldCheckers();
        checkers.forEach((message, checker) -> {
            Object value = getFieldValue(entity, message);
            if (value != null && checker.apply(value).isPresent()) {
                throw new ConflictException(message.getDescription());
            }
        });
    }


    public T update(U updateDto, ID id) {
        if (id == null) {
            throw new IllegalArgumentException("شناسه نمی‌تواند null باشد");
        }
        T existingEntity = findById(id);
        updateEntity(updateDto, existingEntity);
        return save(existingEntity);
    }


    @Cacheable(cacheNames = "#this.getCacheName()",
            key = "#id",
            unless = "#result == null",
            cacheResolver = "cacheResolver")
    public T findById(ID id) {
        Optional<T> optional = getRepository().findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException(getNotFoundMessage().getDescription());
        }
        return optional.get();
    }

    @Cacheable(cacheNames = "#this.getAllCacheName()",
            unless = "#result.isEmpty()",
            cacheResolver = "cacheResolver")
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @CacheEvict(cacheNames = {"#this.getCacheName()",
            "#this.getAllCacheName()"},
            allEntries = true,
            cacheResolver = "cacheResolver")
    public void deleteById(ID id) {
        findById(id);
        getRepository().deleteById(id);
    }

    @CacheEvict(cacheNames = {"#this.getCacheName()", "#this.getAllCacheName()"},
            allEntries = true,
            cacheResolver = "cacheResolver")
    public void deleteAll() {
        getRepository().deleteAll();
    }

    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    public long count() {
        return getRepository().count();
    }


    @Cacheable(cacheNames = "#this.getCacheName()",
            key = "#value.toString()",
            unless = "#result == null",
            cacheResolver = "cacheResolver")
    public  <V> T findByField(V value, Function<V, Optional<T>> finder) {
        Optional<T> optional = finder.apply(value);
        if (optional.isEmpty()) {
            throw new NotFoundException(getNotFoundMessage().getDescription());
        }
        return optional.get();
    }
}