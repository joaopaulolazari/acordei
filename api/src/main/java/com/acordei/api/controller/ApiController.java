package com.acordei.api.controller;

import com.acordei.api.domain.DashBoard;
import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.acordei.api.service.PoliticoService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/api", description = "Api operations")
public class ApiController {

    @Autowired private PoliticoService politicoService;

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/projetos", method = RequestMethod.GET)
    public @ResponseBody List<PoliticoProjetosDeLei> getPoliticoProjetos(@RequestParam("nomePolitico") String nomePolitico) {
        return politicoService.findProjetosDeLei(nomePolitico);
    }

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/{matricula}", method = RequestMethod.GET)
    public @ResponseBody Politico getPolitico(@PathVariable("matricula") String matricula) {
        return politicoService.getPolitico(matricula);
    }

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/{matricula}/assiduidade", method = RequestMethod.GET)
    public @ResponseBody PoliticoAssiduidade getPoliticoAssiduidade(@PathVariable("matricula") String matricula) {
        return politicoService.getPoliticoAssiduidade(matricula);
    }


    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/dashboard", method = RequestMethod.GET)
    public @ResponseBody DashBoard getDashboard() {
        //return politicoService.getPolitico(matricula);
        return null;
    }

//    @Cacheable("RESPONSE_CACHE")
//    @RequestMapping(value = "/api/politicos/", method = RequestMethod.GET)
//    public @ResponseBody List<Politico> getPoliticos() {
//        return politicoService.listPoliticos();
//    }
//
//    @Cacheable("RESPONSE_CACHE")
//    @RequestMapping(value = "/api/politicos/UF/{idUf}", method = RequestMethod.GET)
//    public @ResponseBody List<Politico> getPoliticosByUf(@PathVariable("idUf") String ufId) {
//        return politicoService.getPoliticosByEstado(ufId);
//    }
    /*
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

    @RequestMapping(value = "/api/politico/{ID}/gastos", method = RequestMethod.GET)
    public @ResponseBody Sample getPoliticoGastos(@PathVariable("ID") String id) {
        return service.sample();
    }*/


}
