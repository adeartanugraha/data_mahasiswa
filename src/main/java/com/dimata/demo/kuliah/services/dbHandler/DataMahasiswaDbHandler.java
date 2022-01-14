package com.dimata.demo.kuliah.services.dbHandler;

import com.dimata.demo.kuliah.core.api.DbHandlerBase;
import com.dimata.demo.kuliah.models.table.DataMahasiswa;
import com.dimata.demo.kuliah.services.repo.DataMahasiswaRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataMahasiswaDbHandler extends DbHandlerBase<DataMahasiswa, Long> {
    
    @Autowired
    private DataMahasiswaRepo repo;

    @Override
    protected R2dbcRepository<DataMahasiswa, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<DataMahasiswa> setGenerateId(DataMahasiswa record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<DataMahasiswa> setGenerateIdBatch(Flux<DataMahasiswa> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }
    
}
