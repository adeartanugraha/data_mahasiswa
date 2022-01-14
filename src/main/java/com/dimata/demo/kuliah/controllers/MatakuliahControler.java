package com.dimata.demo.kuliah.controllers;

import com.dimata.demo.kuliah.core.search.CommonParam;
import com.dimata.demo.kuliah.forms.DataMatakuliahForm;
import com.dimata.demo.kuliah.models.table.DataMatakuliah;
import com.dimata.demo.kuliah.services.api.DataMatakuliahApi;

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
public class MatakuliahControler {
    @Autowired
    private DataMatakuliahApi dataMatakuliahApi;

    private static final String BASE_URL = "/maintainer/v2";

    @PostMapping(path = BASE_URL + "/matakuliah", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataMatakuliah> maintainerAddDataMatakuliah(@RequestBody DataMatakuliahForm form) {
        // TODO : Tidak boleh ada dua return dalam satu method.

        return dataMatakuliahApi.createDataMatakuliah(form);

    }

    @GetMapping(path = BASE_URL + "/matakuliah")
    public Flux<DataMatakuliah> maintainerGetAllDataMatakuliah(CommonParam param) {
        return dataMatakuliahApi.getAllDataMatakuliah(param);
    }

    @GetMapping(path = BASE_URL + "/matakuliah/{id_matakuliah}")
    public Mono<DataMatakuliah> maintainerGetDataMatakuliah(@PathVariable("id_matakuliah") Long id_matakuliah) {
        // TODO : ini typo ? gak kepakek auto correcnya ?
        // tinggal pencel spasi + ctrl
        return dataMatakuliahApi.getDataMatakuliah(id_matakuliah);
    }

    @PutMapping(path = BASE_URL + "/matakuliah/{id_matakuliah}")
    public Mono<DataMatakuliah> maintainerUpdateDataMatakuliah(@PathVariable("id_matakuliah") long idMatakuliah, @RequestBody DataMatakuliahForm form) {
        return dataMatakuliahApi.updateDataMatakuliah(idMatakuliah, form);
    }
}
