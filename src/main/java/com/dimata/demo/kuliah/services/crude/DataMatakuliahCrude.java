package com.dimata.demo.kuliah.services.crude;

import com.dimata.demo.kuliah.models.table.DataMatakuliah;
import com.dimata.demo.kuliah.services.dbHandler.DataMatakuliahDbHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataMatakuliahCrude {
    
    @Autowired
    private DataMatakuliahDbHandler dataMatakuliahDbHandler;

    public static Option initOption(DataMatakuliah record) {
        return new Option(record);
    }

    public Mono<DataMatakuliah> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataMatakuliah> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataMatakuliah> savedRecord = dataMatakuliahDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataMatakuliah> updateRecord(Option option) {
        return dataMatakuliahDbHandler.updateOnly(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataMatakuliah record;
        
        public Option(DataMatakuliah record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
