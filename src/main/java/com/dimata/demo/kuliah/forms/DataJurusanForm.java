package com.dimata.demo.kuliah.forms;

// import java.time.LocalDate;

import com.dimata.demo.kuliah.core.api.RecordAdapter;
import com.dimata.demo.kuliah.core.util.jackson.DateDeserialize;
import com.dimata.demo.kuliah.models.table.DataJurusan;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class DataJurusanForm implements RecordAdapter<DataJurusan>{
    private Long id;
    private String namaJurusan;
    private String deskripsi;
    @JsonDeserialize(converter = DateDeserialize.class)
    @Override
    public DataJurusan convertNewRecord() {
        return DataJurusan.Builder.createNewRecord(namaJurusan)
            .deskripsi(deskripsi)
            .id(id)
            .build();
    }
    @Override
    public DataJurusan convertToRecord() {
        return DataJurusan.Builder.emptyBuilder()
            .id(id)
            .namaJurusan(namaJurusan)
            .deskripsi(deskripsi)
            .build();
    }
}
