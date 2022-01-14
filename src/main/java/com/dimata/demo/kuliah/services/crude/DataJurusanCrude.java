package com.dimata.demo.kuliah.services.crude;

import com.dimata.demo.kuliah.models.table.DataJurusan;
import com.dimata.demo.kuliah.services.dbHandler.DataJurusanDbhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataJurusanCrude {
    @Autowired
    private DataJurusanDbhandler dataJurusanDbhandler;

    public static Option initOption(DataJurusan record) {
        return new Option(record);
    }

    public Mono<DataJurusan> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataJurusan> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataJurusan> savedRecord = dataJurusanDbhandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataJurusan> updateRecord(Option option) {
        return dataJurusanDbhandler.update(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataJurusan record;
        
        public Option(DataJurusan record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
