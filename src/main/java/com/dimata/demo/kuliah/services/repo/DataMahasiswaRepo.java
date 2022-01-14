package com.dimata.demo.kuliah.services.repo;

import com.dimata.demo.kuliah.models.table.DataMahasiswa;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataMahasiswaRepo extends R2dbcRepository<DataMahasiswa, Long> {
    
    Mono<DataMahasiswa> findById(Long id);
}
