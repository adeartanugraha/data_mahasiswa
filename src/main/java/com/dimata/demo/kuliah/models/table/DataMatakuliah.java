package com.dimata.demo.kuliah.models.table;


import java.util.Objects;
import static com.dimata.demo.kuliah.core.util.ManipulateUtil.changeItOrNot;

import com.dimata.demo.kuliah.core.api.UpdateAvailable;
import com.dimata.demo.kuliah.core.util.GenerateUtil;
import com.dimata.demo.kuliah.core.util.ManipulateUtil;
import com.dimata.demo.kuliah.core.util.jackson.DateDeserialize;
import com.dimata.demo.kuliah.core.util.jackson.DateSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import io.r2dbc.spi.Row;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DataMatakuliah implements Persistable<Long>, UpdateAvailable<DataMatakuliah> {
    public static final String TABLE_NAME = "data_matakuliah";
    public static final String ID_COL = "id_matakuliah";
    public static final String NAMA_MATAKULIAH_COL = "nama_matakuliah";
    public static final String DESKRIPSI_COL = "deskripsi";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private String namaMatakuliah;
        private String deskripsi;
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(String namaMatakuliah) {
            return new Builder().newRecord(true)
                .namaMatakuliah(Objects.requireNonNull(namaMatakuliah, "Nama Matakuliah tidak boleh kosong"));
        }

        public static Builder updateBuilder(DataMatakuliah oldRecord, DataMatakuliah newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .namaMatakuliah(changeItOrNot(newRecord.getNamaMatakuliah(), oldRecord.getNamaMatakuliah()))
                .deskripsi(changeItOrNot(newRecord.getDeskripsi(), oldRecord.getDeskripsi()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataMatakuliah build() {
            DataMatakuliah result = new DataMatakuliah();
            result.setId(id);
            result.setNamaMatakuliah(namaMatakuliah);
            result.setDeskripsi(deskripsi);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private String namaMatakuliah;
    private String deskripsi;

    
    @Transient
    @JsonIgnore
    private Long insertId;


    public static DataMatakuliah fromRow(Row row) {
        var result = new DataMatakuliah();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setNamaMatakuliah(ManipulateUtil.parseRow(row, NAMA_MATAKULIAH_COL, String.class));
        result.setDeskripsi(ManipulateUtil.parseRow(row, DESKRIPSI_COL, String.class));
        return result;
    }

    

    @Override
    public boolean isNew() {
        if (id == null && insertId == null) {
            id = new GenerateUtil().generateOID();
            return true;
        } else if (id == null) {
            id = insertId;
            return true;
        }
        return false;
    }

    @Override
    public DataMatakuliah update(DataMatakuliah newData) {
        return Builder.updateBuilder(this, newData).build();
    }
}
