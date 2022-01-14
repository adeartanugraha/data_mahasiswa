package com.dimata.demo.kuliah.services.repo;

import com.dimata.demo.kuliah.models.table.DataMatakuliah;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataMatakuliahRepo extends R2dbcRepository<DataMatakuliah, Long> {
    
    Mono<DataMatakuliah> findById(Long id);
}
