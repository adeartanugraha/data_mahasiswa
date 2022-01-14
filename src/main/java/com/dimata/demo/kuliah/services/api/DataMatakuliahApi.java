package com.dimata.demo.kuliah.services.api;

import com.dimata.demo.kuliah.core.search.CommonParam;
import com.dimata.demo.kuliah.core.search.SelectQBuilder;
import com.dimata.demo.kuliah.core.search.WhereQuery;
import com.dimata.demo.kuliah.forms.DataMatakuliahForm;
import com.dimata.demo.kuliah.models.table.DataMatakuliah;
import com.dimata.demo.kuliah.services.crude.DataMatakuliahCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataMatakuliahApi {
    
    @Autowired
    private DataMatakuliahCrude dataMatakuliahCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataMatakuliah> createDataMatakuliah(DataMatakuliahForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataMatakuliahCrude.Option option = DataMatakuliahCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataMatakuliahCrude::create);
    }

    public Flux<DataMatakuliah> getAllDataMatakuliah(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataMatakuliah.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataMatakuliah::fromRow)
            .all();
    }

    public Mono<DataMatakuliah> getDataMatakuliah(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataMatakuliah.TABLE_NAME)
            .addWhere(WhereQuery.when(DataMatakuliah.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataMatakuliah::fromRow)
            .one();
    }

    public Mono<DataMatakuliah> updateDataMatakuliah(Long id, DataMatakuliahForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataMatakuliahCrude.Option option = DataMatakuliahCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataMatakuliahCrude::updateRecord);
    }
}
