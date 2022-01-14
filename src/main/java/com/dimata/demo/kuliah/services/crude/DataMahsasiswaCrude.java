package com.dimata.demo.kuliah.services.crude;

import com.dimata.demo.kuliah.models.table.DataMahasiswa;
import com.dimata.demo.kuliah.services.dbHandler.DataMahasiswaDbHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataMahsasiswaCrude {
    
    @Autowired
    private DataMahasiswaDbHandler dataMahasiswaDbHandler;

    public static Option initOption(DataMahasiswa record) {
        return new Option(record);
    }

    public Mono<DataMahasiswa> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataMahasiswa> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataMahasiswa> savedRecord = dataMahasiswaDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataMahasiswa> updateRecord(Option option) {
        return dataMahasiswaDbHandler.updateOnly(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataMahasiswa record;
        
        public Option(DataMahasiswa record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
