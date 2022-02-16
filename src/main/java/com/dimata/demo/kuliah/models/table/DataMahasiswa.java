package com.dimata.demo.kuliah.models.table;

import java.time.LocalDate;
import java.util.Objects;

import static com.dimata.demo.kuliah.core.util.ManipulateUtil.changeItOrNot;

import com.dimata.demo.kuliah.core.api.UpdateAvailable;
import com.dimata.demo.kuliah.core.util.GenerateUtil;
import com.dimata.demo.kuliah.core.util.ManipulateUtil;
import com.dimata.demo.kuliah.core.util.jackson.DateSerialize;
import com.dimata.demo.kuliah.enums.GenderMahasiswa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class DataMahasiswa implements UpdateAvailable<DataMahasiswa>, Persistable<Long>{
    
    public static final String TABLE_NAME = "data_mahasiswa";
    public static final String ID_COL = "id_mahasiswa";
    public static final String NAMA_MAHASISWA_COL = "nama_mahasiswa";
    public static final String ALAMAT_COL = "alamat";
    public static final String JENIS_KELAMIN_COL = "jenis_kelamin";
    public static final String NIM_COL = "nim";
    public static final String ID_JURUSAN_COL = "id_jurusan";
    public static final String TELP_COL = "telp";
    public static final String KELAS_COL = "kelas";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private String nim;
        private String namaMahasiswa;
        private String alamat;
        private Long idJurusan;
        private GenderMahasiswa jenisKelamin;
        private String telp;
        private String kelas;
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(String namaMahasiswa, GenderMahasiswa jenisKelamin) {
            return new Builder().newRecord(true)
                .namaMahasiswa(Objects.requireNonNull(namaMahasiswa, "Nama mahasiswa tidak boleh kosong"))
                .jenisKelamin(Objects.requireNonNull(jenisKelamin, "jenis kelamin tidak boleh kosong"));
        }

        public static Builder updateBuilder(DataMahasiswa oldRecord, DataMahasiswa newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .alamat(changeItOrNot(newRecord.getAlamat(), oldRecord.getAlamat()))
                .idJurusan(changeItOrNot(newRecord.getIdJurusan(), oldRecord.getIdJurusan()))
                .kelas(changeItOrNot(newRecord.getKelas(), oldRecord.getKelas()))
                .jenisKelamin(changeItOrNot(newRecord.getJenisKelamin(), oldRecord.getJenisKelamin()))
                .namaMahasiswa(changeItOrNot(newRecord.getNamaMahasiswa(), oldRecord.getNamaMahasiswa()))
                .nim(changeItOrNot(newRecord.getNim(), oldRecord.getNim()))
                .telp(changeItOrNot(newRecord.getTelp(), oldRecord.getTelp()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataMahasiswa build() {
            DataMahasiswa result = new DataMahasiswa();
            
            result.setAlamat(alamat);
            result.setIdJurusan(idJurusan);
            result.setKelas(kelas);
            result.setJenisKelamin(jenisKelamin);
            result.setId(id);
            result.setNamaMahasiswa(namaMahasiswa);
            result.setNim(nim);
            result.setTelp(telp);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private String nim;
    private String namaMahasiswa;
    private String alamat;
    private Long idJurusan;
    private Integer jenisKelamin;
    private String telp;
    private String kelas;
    @Transient
    @JsonIgnore
    private Long insertId;

    public void setJenisKelamin(GenderMahasiswa jenisKelamin) {
        if (jenisKelamin != null) {
            this.jenisKelamin= jenisKelamin.getCode();
        }
    }

    public GenderMahasiswa getJenisKelamin() {
        if (jenisKelamin != null) {
            return GenderMahasiswa.getJenisKelamin(this.jenisKelamin);
        }
        return null;
    }

    public static DataMahasiswa fromRow(Row row) {
        var result = new DataMahasiswa();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setNim(ManipulateUtil.parseRow(row, NIM_COL, String.class));
        result.setNamaMahasiswa(ManipulateUtil.parseRow(row, NAMA_MAHASISWA_COL, String.class));
        result.setTelp(ManipulateUtil.parseRow(row, TELP_COL, String.class));
        result.setAlamat(ManipulateUtil.parseRow(row, ALAMAT_COL, String.class));
        result.setJenisKelamin(GenderMahasiswa.getJenisKelamin(ManipulateUtil.parseRow(row, JENIS_KELAMIN_COL, Integer.class)));
        result.setKelas(ManipulateUtil.parseRow(row, KELAS_COL, String.class));
        result.setIdJurusan(ManipulateUtil.parseRow(row, ID_JURUSAN_COL, Long.class));
        
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
    public DataMahasiswa update(DataMahasiswa newData) {
        return Builder.updateBuilder(this, newData).build();
    }

    
}
