package com.dimata.demo.kuliah.services.dbHandler;

import com.dimata.demo.kuliah.core.api.DbHandlerBase;
import com.dimata.demo.kuliah.models.table.DataJurusan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataJurusanDbhandler extends DbHandlerBase<DataJurusan, Long>{
    @Autowired
    private R2dbcRepository<DataJurusan, Long> repo;

    @Override
    protected R2dbcRepository<DataJurusan, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<DataJurusan> setGenerateId(DataJurusan record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<DataJurusan> setGenerateIdBatch(Flux<DataJurusan> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }
}
