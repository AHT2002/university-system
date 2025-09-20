package com.example.university_system.component.maper;

import java.util.List;

public interface BaseMapper<T, A, U, V> {
    T toAddEntity(A addDto);
    V toViewDto(T entity);
    List<V> toListViewDTO(List<T> entities);
}
