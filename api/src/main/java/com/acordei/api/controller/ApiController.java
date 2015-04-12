package com.acordei.api.controller;

import com.acordei.api.domain.*;
import com.acordei.api.service.DashBoardService;
import com.acordei.api.service.PoliticoService;
import com.wordnik.swagger.annotations.Api;
import http.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "/api", description = "Api operations")
public class ApiController {

    @Autowired private PoliticoService politicoService;
    @Autowired private DashBoardService dashBoardService;

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/projetos", method = RequestMethod.GET)
    public @ResponseBody PoliticoPropostas getPoliticoProjetos(@RequestParam("nomePolitico") String nomePolitico) {
        return politicoService.findProjetosDeLei(nomePolitico);
    }

    @RequestMapping(value = "/api/politicos", method = RequestMethod.GET)
    public @ResponseBody List<Politico> getPoliticoProjetos() {
        return politicoService.listPoliticos();
    }

    private String formatNomeParlamentar(String nome){
        String normalized = removeSpecialCharacters(nome).toLowerCase().replace(" ","-");
        return  ( normalized.contains("-") ? normalized.substring(0,normalized.lastIndexOf("-")) : normalized);
    }
    private String removeSpecialCharacters(String text){
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    @RequestMapping(value = "/api/senadores/politicos", method = RequestMethod.GET)
    public @ResponseBody List<Politico> getPoliticoSenadores() {
        return politicoService.listSenadores();
    }

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/{matricula}", method = RequestMethod.GET)
    public @ResponseBody Politico getPolitico(@PathVariable("matricula") String matricula) {
        return politicoService.getPolitico(matricula);
    }

    @Cacheable("ASSIDUIDADE_RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/assiduidade/{matricula}", method = RequestMethod.GET)
    public @ResponseBody PoliticoAssiduidade getAssiduidade(@PathVariable("matricula") String matricula) {
        return politicoService.getPoliticoAssiduidade(matricula);
    }

    @RequestMapping(value = "/api/dashboard", method = RequestMethod.GET)
    public @ResponseBody List<DashBoard> getDashboard() {
        return dashBoardService.findDashBoardDatas();
    }

    @Cacheable("GASTOS_RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/gastos/{matricula}", method = RequestMethod.GET)
    public @ResponseBody List<Gasto> getGastosPorMatricula(@PathVariable("matricula") String matricula) {
        return politicoService.getGastosPorMatricula(matricula);
    }

}
