package com.example.aston.mapper;

public interface AbstractMapper<D, E> {
    D mapToDto(E entity);
    E mapToEntity(D dto);
}

