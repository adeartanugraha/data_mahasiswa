package com.dimata.demo.kuliah.forms;

import java.time.LocalDate;

import com.dimata.demo.kuliah.core.api.RecordAdapter;
import com.dimata.demo.kuliah.core.util.jackson.DateDeserialize;
import com.dimata.demo.kuliah.enums.GenderSiswa;
import com.dimata.demo.kuliah.models.table.DataMatakuliah;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataMatakuliahForm implements RecordAdapter<DataMatakuliah> {

    private Long id;
    private String namaMatakuliah;
    private String deskripsi;
    @JsonDeserialize(converter = DateDeserialize.class)
    @Override
    public DataMatakuliah convertNewRecord() {
        return DataMatakuliah.Builder.createNewRecord(namaMatakuliah)
            .deskripsi(deskripsi)
            .id(id)
            .build();
    }
    @Override
    public DataMatakuliah convertToRecord() {
        return DataMatakuliah.Builder.emptyBuilder()
            .deskripsi(deskripsi)
            .namaMatakuliah(namaMatakuliah)
            .id(id)
            .build();
    }
}
