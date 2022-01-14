package com.dimata.demo.kuliah.services.api;

import com.dimata.demo.kuliah.core.search.CommonParam;
import com.dimata.demo.kuliah.core.search.SelectQBuilder;
import com.dimata.demo.kuliah.core.search.WhereQuery;
import com.dimata.demo.kuliah.forms.DataMahasiswaForm;
import com.dimata.demo.kuliah.models.table.DataMahasiswa;
import com.dimata.demo.kuliah.services.crude.DataMahsasiswaCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataMahasiswaApi {
    
    @Autowired
    private DataMahsasiswaCrude dataSiswaCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataMahasiswa> createDataMahasiswa(DataMahasiswaForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataMahsasiswaCrude.Option option = DataMahsasiswaCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataSiswaCrude::create);
    }

    public Flux<DataMahasiswa> getAllDataMahasiswa(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataMahasiswa.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataMahasiswa::fromRow)
            .all();
    }

    public Mono<DataMahasiswa> getDataMahasiswa(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataMahasiswa.TABLE_NAME)
            .addWhere(WhereQuery.when(DataMahasiswa.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataMahasiswa::fromRow)
            .one();
    }

    public Mono<DataMahasiswa> updateDataMahasiswa(Long id, DataMahasiswaForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataMahsasiswaCrude.Option option = DataMahsasiswaCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataSiswaCrude::updateRecord);
    }
}
