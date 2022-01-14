package com.dimata.demo.kuliah.forms;

import java.time.LocalDate;

import com.dimata.demo.kuliah.core.api.RecordAdapter;
import com.dimata.demo.kuliah.core.util.jackson.DateDeserialize;
import com.dimata.demo.kuliah.enums.GenderMahasiswa;
import com.dimata.demo.kuliah.models.table.DataMahasiswa;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSiswaForm implements RecordAdapter<DataMahasiswa> {

    private Long id;
    private String nim;
    private String namaMahasiswa;
    private String alamat;
    private String telp;
    private GenderMahasiswa jenisKelamin;
    private String kelas;
    private Long idJurusan;
    @JsonDeserialize(converter = DateDeserialize.class)
    @Override
    public DataMahasiswa convertNewRecord() {
        return DataMahasiswa.Builder.createNewRecord(namaMahasiswa, jenisKelamin)
            .alamat(alamat)
            .telp(telp)
            .kelas(kelas)
            .idjurusan(idJurusan)
            .nim(nim)
            .id(id)
            .build();
    }
    @Override
    public DataMahasiswa convertToRecord() {
        return DataMahasiswa.Builder.emptyBuilder()
            .alamat(alamat)
            .alamat(alamat)
            .telp(telp)
            .kelas(kelas)
            .idjurusan(idJurusan)
            .nim(nim)
            .jenisKelamin(jenisKelamin)
            .namaMahasiswa(namaMahasiswa)
            .id(id)
            .build();
    }
}
