package com.acordei.api.controller;

import com.acordei.api.dao.MongoSingletonClient;
import com.acordei.api.domain.*;
import com.acordei.api.service.DashBoardService;
import com.acordei.api.service.PoliticoService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.wordnik.swagger.annotations.Api;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping(value = "/api/all/politicos", method = RequestMethod.GET)
    public @ResponseBody List<Politico> getAll() {
        List<Politico> politicos = pegaTodoMundo();

        try {
            Connection conn = SQLiteJDBC.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POLITICOS");
            while (rs.next()) {
                String name = rs.getString("nome");
                String nomeUrna = rs.getString("nome_urna");
                Optional<Politico> politico = politicos.stream().filter(Politico -> Politico.getNome().toLowerCase().equals(name.toLowerCase())).findFirst();
                if (politico.isPresent()) {
                    Politico p = politico.get();
                    p.setNomeUrna(nomeUrna);

                    Document findById = new Document("id",p.getMatricula());
                    Document findByNome = new Document("nome",p.getNome());
                    Document findByNomeParlamentar = new Document("nome",p.getNomeParlamentar());

                    FindIterable cursorById = MongoSingletonClient.getDb().getCollection("politicosconsolidado").find(findById);
                    FindIterable cursorByNome = MongoSingletonClient.getDb().getCollection("politicosconsolidado").find(findByNome);
                    FindIterable cursorByNomeUrna = MongoSingletonClient.getDb().getCollection("politicosconsolidado").find(findByNomeParlamentar);

                    MongoCursor c = cursorById.iterator();
                    if ( c.hasNext() ){

                    }else{

                    }

                }else{
                    //insere o cara no mongo!
                }
            }
            rs.close();
            stmt.close();

        }catch(Exception e){

        }
        return politicos;
    }

    private List<Politico> pegaTodoMundo() {
        List<Politico> politicos = politicoService.listSenadores();
        politicos.addAll( politicoService.listPoliticos() );
        return politicos;
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
    public @ResponseBody  List<DashBoard> getDashboard() {
        return dashBoardService.findDashBoardDatas();
    }

    @RequestMapping(value = "/api/politico/gastos/{nomePolitico}", method = RequestMethod.GET)
    public @ResponseBody List<Gasto> getGastosPorPolitico(@PathVariable("nomePolitico") String nomePolitico){
        return politicoService.getGastosPorPolitico(nomePolitico);
    }


    public static class SQLiteJDBC
    {
        public static Connection getConn()
        {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection("jdbc:sqlite:/home/azureuser/acordei/eleicoes.db");
            } catch ( Exception e ) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
                return null;
            }
        }
    }


}
