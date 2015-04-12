package com.acordei.api.controller;

import com.acordei.api.domain.DashBoard;
import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.acordei.api.service.DashBoardService;
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
    @Autowired private DashBoardService dashBoardService;

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/projetos", method = RequestMethod.GET)
    public @ResponseBody List<PoliticoProjetosDeLei> getPoliticoProjetos(@RequestParam("nomePolitico") String nomePolitico) {
        return politicoService.findProjetosDeLei(nomePolitico);
    }

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politicos", method = RequestMethod.GET)
    public @ResponseBody List<Politico> getPoliticoProjetos() {
        return politicoService.listPoliticos();
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
    public @ResponseBody  List<DashBoard> getDashboard() {
        return dashBoardService.findDashBoardDatas();
    }



}
