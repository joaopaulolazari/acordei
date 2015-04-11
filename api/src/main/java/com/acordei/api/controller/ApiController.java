package com.acordei.api.controller;

import com.acordei.api.domain.Sample;
import com.acordei.api.service.SampleService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "/api", description = "Api operations")
public class ApiController {

	@Autowired private SampleService service;

    @RequestMapping(value = "/api/estatisticas", method = RequestMethod.GET)
    public @ResponseBody Sample getEstatisticasNacionais() {
        return service.sample();
    }

    @RequestMapping(value = "/api/estatisticas/UF/{UF}", method = RequestMethod.GET)
    public @ResponseBody Sample getEstatisticasEstado(@PathVariable("UF") String uf) {
        return service.sample();
    }

    @RequestMapping(value = "/api/estatisticas/partido/{ID}", method = RequestMethod.GET)
    public @ResponseBody Sample getEstatisticasPartido(@PathVariable("ID") String id) {
        return service.sample();
    }

    @RequestMapping(value = "/api/politicos", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticos() {
        return service.sample();
    }

    @RequestMapping(value = "/api/politicos/UF/{UF_ID}", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticos(@PathVariable("UF_ID") String ufId) {
        return service.sample();
    }

    @RequestMapping(value = "/api/politico/", method = RequestMethod.GET)
    public @ResponseBody Sample findPoliticos(@RequestParam String filtro) {
        return service.sample();
    }

    @RequestMapping(value = "/api/politico/{ID}/estatisticas", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticoEstatisticas(@PathVariable("ID") String id) {
        return service.sample();
    }

    @RequestMapping(value = "/api/politico/{ID}/projetos", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticoProjetos(@PathVariable("ID") String id) {
        return service.sample();
    }

    @RequestMapping(value = "/api/politico/{ID}/gastos", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticoGastos(@PathVariable("ID") String id) {
        return service.sample();
    }

    @RequestMapping(value = "/api/politico/{ID}/presencas", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticoPresencas(@PathVariable("ID") String id) {
        return service.sample();
    }


}
