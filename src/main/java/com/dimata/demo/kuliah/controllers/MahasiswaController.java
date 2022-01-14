package com.dimata.demo.kuliah.controllers;

import com.dimata.demo.kuliah.core.search.CommonParam;
import com.dimata.demo.kuliah.forms.DataMahasiswaForm;
import com.dimata.demo.kuliah.models.table.DataMahasiswa;
import com.dimata.demo.kuliah.services.api.DataMahasiswaApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MahasiswaController {

    @Autowired
    private DataMahasiswaApi dataMahasiswaApi;

    private static final String BASE_URL = "/maintainer/v2";

    @PostMapping(path = BASE_URL + "/data_mahasiswa", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataMahasiswa> maintainerAddDataMahasiswa(@RequestBody DataMahasiswaForm form) {
        return dataMahasiswaApi.createDataMahasiswa(form);
    }

    @GetMapping(path = BASE_URL + "/data_mahasiswa")
    public Flux<DataMahasiswa> maintainerGetAllDataMahasiswa(CommonParam param) {
        return dataMahasiswaApi.getAllDataMahasiswa(param);
    }

    @GetMapping(path = BASE_URL + "/data_mahasiswa/{id_mahasiswa}")
    public Mono<DataMahasiswa> maintainerGetDataSiswa(@PathVariable("id_mahasiswa") Long id_mahasiswa) {
        return dataMahasiswaApi.getDataMahasiswa(id_mahasiswa);
    }

    @PutMapping(path = BASE_URL + "/data_mahasiswa/{nisn}")
    public Mono<DataMahasiswa> maintainerUpdateDataMahasiswa(@PathVariable("id_mahasiswa") long idMahasiswa, @RequestBody DataMahasiswaForm form) {
        return dataMahasiswaApi.updateDataMahasiswa(idMahasiswa, form);
    }
}
