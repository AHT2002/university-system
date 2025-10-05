package com.example.university_system.controller.Base;

import com.example.university_system.entity.BaseEntity;
import com.example.university_system.component.maper.BaseMapper;
import com.example.university_system.service.BaseService;
import com.example.university_system.enums.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseController<T extends BaseEntity, ID, A, U, V> {

    protected abstract BaseService<T, ID, U> getService();
    protected abstract BaseMapper<T, A, V> getMapper();


    protected abstract Permission getCreatePermission();
    protected abstract Permission getReadPermission();
    protected abstract Permission getReadAllPermission();
    protected abstract Permission getUpdatePermission();
    protected abstract Permission getDeletePermission();

    protected abstract void checkMonoAndAllPermissionWithOwnership(ID resourceId, Permission permission, Permission allPermission);
    protected abstract void checkMonoPermissionWithOwnership(ID resourceId, Permission permission);

    protected void checkMonoAndAllPermissionWithOwnership(
            Permission fullAccessPermission,
            Permission ownAccessPermission,
            long resourceOwnerId,
            long currentUserId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean hasFullAccess = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(fullAccessPermission.getPermission()));
        if (hasFullAccess) return;

        boolean hasOwnAccess = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ownAccessPermission.getPermission()));
        if (hasOwnAccess && resourceOwnerId == currentUserId) return;

        throw new AccessDeniedException("Access denied");
    }


    protected void checkMonoPermissionWithOwnership(
            Permission ownAccessPermission,
            long resourceOwnerId,
            long currentUserId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasOwnAccess = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ownAccessPermission.getPermission()));
        if (hasOwnAccess && resourceOwnerId == currentUserId) return;

        throw new AccessDeniedException("Access denied");
    }


    protected void checkPermission(Permission permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().forEach(a -> System.out.println("Authority: " + a.getAuthority()));
        boolean hasPermission = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(permission.getPermission()));
        if (!hasPermission) {
            throw new AccessDeniedException("Access denied: " + permission.name());
        }
    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public V save(@RequestBody @Valid A addDto) {
        checkPermission(getCreatePermission());

        T entity = getMapper().toAddEntity(addDto);
        T savedEntity = getService().save(entity);
        return getMapper().toViewDto(savedEntity);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public V update(@RequestBody @Valid U updateDto, @PathVariable ID id) {
        checkMonoPermissionWithOwnership(id, getUpdatePermission());

        T updatedEntity = getService().update(updateDto, id);
        return getMapper().toViewDto(updatedEntity);
    }

    @GetMapping("/find/{id}")
    public V findById(@PathVariable ID id) {
        checkMonoAndAllPermissionWithOwnership(id, this.getReadPermission(), this.getReadAllPermission());

        return getMapper().toViewDto(getService().findById(id));
    }

    @GetMapping("/list")
    public List<V> findAll() {
        checkPermission(getReadAllPermission());
        return getMapper().toListViewDTO(getService().findAll());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable ID id) {
        checkMonoPermissionWithOwnership(id, getDeletePermission());

        getService().deleteById(id);
    }
}