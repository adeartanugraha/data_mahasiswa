package com.dimata.demo.kuliah.controllers;

import com.dimata.demo.kuliah.core.search.CommonParam;
import com.dimata.demo.kuliah.forms.DataJurusanForm;
import com.dimata.demo.kuliah.models.table.DataJurusan;
import com.dimata.demo.kuliah.services.api.DataJurusanApi;

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
public class JurusanController {
    @Autowired
    private DataJurusanApi dataJurusanApi;

    private static final String BASE_URL = "/maintainer/v2";

    @PostMapping(path = BASE_URL + "/jurusan", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataJurusan> maintainerAddDataJurusan(@RequestBody DataJurusanForm form) {
        // TODO : Tidak boleh ada dua return dalam satu method.

        return dataJurusanApi.createDataJurusan(form);

    }

    @GetMapping(path = BASE_URL + "/jurusan")
    public Flux<DataJurusan> maintainerGetAllDataJurusan(CommonParam param) {
        return dataJurusanApi.getAllDataJurusan(param);
    }

    @GetMapping(path = BASE_URL + "/jurusan/{id_jurusan}")
    public Mono<DataJurusan> maintainerGetDataJurusan(@PathVariable("id_jurusan") Long id_jurusan) {
        // TODO : ini typo ? gak kepakek auto correcnya ?
        // tinggal pencel spasi + ctrl
        return dataJurusanApi.getDatajurusan(id_jurusan);
    }

    @PutMapping(path = BASE_URL + "/jurusan/{id_jurusan}")
    public Mono<DataJurusan> maintainerUpdateDataJurusan(@PathVariable("id_jurusan") long idJurusan, @RequestBody DataJurusanForm form) {
        return dataJurusanApi.updateDataJurusan(idJurusan, form);
    }
}
