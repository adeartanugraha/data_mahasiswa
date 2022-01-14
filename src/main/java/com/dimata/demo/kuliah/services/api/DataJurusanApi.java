package com.dimata.demo.kuliah.services.api;


import com.dimata.demo.kuliah.core.search.CommonParam;
import com.dimata.demo.kuliah.core.search.SelectQBuilder;
import com.dimata.demo.kuliah.core.search.WhereQuery;
import com.dimata.demo.kuliah.forms.DataJurusanForm;
import com.dimata.demo.kuliah.models.table.DataJurusan;
import com.dimata.demo.kuliah.services.crude.DataJurusanCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class DataJurusanApi {
    @Autowired
    private DataJurusanCrude dataJurusanCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataJurusan> createDataJurusan(DataJurusanForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataJurusanCrude.Option option = DataJurusanCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataJurusanCrude::create);
    }

    public Flux<DataJurusan> getAllDataJurusan(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataJurusan.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataJurusan::fromRow)
            .all();
    }

    public Mono<DataJurusan> getDatajurusan(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataJurusan.TABLE_NAME)
            .addWhere(WhereQuery.when(DataJurusan.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataJurusan::fromRow)
            .one();
    }

    public Mono<DataJurusan> updateDataJurusan(Long id, DataJurusanForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataJurusanCrude.Option option = DataJurusanCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataJurusanCrude::updateRecord);
    }    
}
