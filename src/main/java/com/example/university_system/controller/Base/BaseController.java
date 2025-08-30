package com.example.university_system.controller.Base;

import com.example.university_system.entity.BaseEntity;
import com.example.university_system.component.maper.BaseMapper;
import com.example.university_system.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T extends BaseEntity, ID, A, U, V> {

    protected abstract BaseService<T, ID> getService();
    protected abstract BaseMapper<T, A, U, V> getMapper();

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public V save(@RequestBody A addDto) {
        T entity = getMapper().toAddEntity(addDto);
        T savedEntity = getService().save(entity);
        return getMapper().toViewDto(savedEntity);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public V update(@RequestBody U updateDto) {
        T entity = getMapper().toUpdateEntity(updateDto);
        T updatedEntity = getService().update(entity, (ID) entity.getId());
        return getMapper().toViewDto(updatedEntity);
    }

    @GetMapping("/find/{id}")
    public V findById(@PathVariable ID id) {
        return getMapper().toViewDto(getService().findById(id));
    }

    @GetMapping("/list")
    public List<V> findAll() {
        return getMapper().toListViewDTO(getService().findAll());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable ID id) {
        getService().deleteById(id);
    }
}