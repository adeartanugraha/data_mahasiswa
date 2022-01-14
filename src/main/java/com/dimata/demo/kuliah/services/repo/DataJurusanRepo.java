package com.dimata.demo.kuliah.services.repo;

import com.dimata.demo.kuliah.models.table.DataJurusan;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataJurusanRepo extends R2dbcRepository<DataJurusan, Long> {
    Mono<DataJurusan> findById(long id);
    
}
