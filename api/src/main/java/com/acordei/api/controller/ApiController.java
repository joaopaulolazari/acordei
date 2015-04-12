package com.acordei.api.controller;

import com.acordei.api.dao.MongoSingletonClient;
import com.acordei.api.domain.*;
import com.acordei.api.parser.PoliticoBiografiaParser;
import com.acordei.api.service.DashBoardService;
import com.acordei.api.service.PoliticoService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.wordnik.swagger.annotations.Api;
import http.rest.RestClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Api(value = "/api", description = "Api operations")
public class ApiController {

    @Autowired
    private PoliticoService politicoService;
    @Autowired
    private DashBoardService dashBoardService;
    /*int totalInseridos = 0;
    int totalUpdatiados = 0;
    int totalNaoInseridos = 0 ;*/

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/projetos", method = RequestMethod.GET)
    public
    @ResponseBody
    PoliticoPropostas getPoliticoProjetos(@RequestParam("nomePolitico") String nomePolitico) {
        return politicoService.findProjetosDeLei(nomePolitico);
    }

    @RequestMapping(value = "/api/politicos", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Politico> getPoliticoProjetos() {
        return politicoService.listPoliticos();
    }

  /*  @RequestMapping(value = "/api/all/politicos", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Politico> getAll() {
        List<Politico> politicos = pegaTodoMundo();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start thread ...");

                try {
                    Connection conn = SQLiteJDBC.getConn();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM POLITICOS");
                    while (rs.next()) {
                        String name = rs.getString("nome");
                        String nomeUrna = rs.getString("nome_urna");
                        String cargo = rs.getString("cargo");

                        Optional<Politico> politico = politicos.stream().filter(Politico -> Politico.getNome().toLowerCase().equals(name.toLowerCase())
                                || Politico.getNomeParlamentar().toLowerCase().equals(name.toLowerCase())
                                || Politico.getNome().equals(nomeUrna.toLowerCase())
                                || Politico.getNomeParlamentar().toLowerCase().equals(nomeUrna.toLowerCase())).findFirst();

                        if (politico.isPresent()) {
                            Politico p = politico.get();
                            p.setNomeUrna(nomeUrna);

                            Document findById = new Document("id", p.getMatricula());
                            Document findByNome = new Document("nome", p.getNome());
                            Document findByNomeParlamentar = new Document("nome", p.getNomeParlamentar());
                            String DATABASE = "politicosconsolidado_tmp";

                            FindIterable cursorById = MongoSingletonClient.getDb().getCollection(DATABASE).find(findById);
                            FindIterable cursorByNome = MongoSingletonClient.getDb().getCollection(DATABASE).find(findByNome);
                            FindIterable cursorByNomeUrna = MongoSingletonClient.getDb().getCollection(DATABASE).find(findByNomeParlamentar);

                            MongoCursor mongoCursorById = cursorById.iterator();
                            MongoCursor mongoCursorByNome = cursorByNome.iterator();
                            MongoCursor mongoCursorByNomeUrna = cursorByNomeUrna.iterator();

                            if (mongoCursorById.hasNext()) {
                                updateGasto(nomeUrna, cargo, p, findById, DATABASE, mongoCursorById);
                            } else if (mongoCursorByNome.hasNext()) {
                                updateGasto(nomeUrna, cargo, p, findByNome, DATABASE, mongoCursorByNome);
                            } else if (mongoCursorByNomeUrna.hasNext()) {
                                updateGasto(nomeUrna, cargo, p, findByNomeParlamentar, DATABASE, mongoCursorByNome);

                            } else {
                                insertGasto(nomeUrna, cargo, p, DATABASE);
                                totalInseridos += 1;
                                System.out.println("did insert: " + totalInseridos);
                            }
                        } else {
                            totalNaoInseridos +=1;
                            System.out.println("did not insert:: " + totalNaoInseridos);
                        }
                    }
                    rs.close();
                    stmt.close();
                    System.out.println("Done motherfuck!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return politicos;
    }*/

    private String formatNomeParlamentar(String nome){
        String normalized = removeSpecialCharacters(nome).toLowerCase().replace(" ","-");
        return  ( normalized.contains("-") ? normalized.substring(0,normalized.lastIndexOf("-")) : normalized);
    }
    private String removeSpecialCharacters(String text){
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    /*private void updateGasto(String nomeUrna, String cargo, Politico p, Document findById, String DATABASE, MongoCursor mongoCursorById) {
        Document old = (Document) mongoCursorById.next();
        old.put("cargo", cargo);
        old.put("nome_urna", nomeUrna);

        old.put("nome", p.getNome());
        old.put("nome_parlamentar", p.getNomeParlamentar());

        Politico politicoBiografia = new PoliticoBiografiaParser(jsonRequest("https://www.kimonolabs.com/api/json/ondemand/7met68b8?apikey=10deb955005b151ee7f6d2d2c796cde6&kimpath1=" + formatNomeParlamentar(nomeUrna))).parse();
        old.put("biografia", politicoBiografia.getBiografia());
        old.put("email", p.getEmail());
        old.put("foto", p.getFoto());
        old.put("matricula", p.getMatricula());
        old.put("situacao", politicoBiografia.getSituacao());
        old.put("uf", p.getUf());

        Document updateObj = new Document();
        updateObj.put("$set", old);
        MongoSingletonClient.getDb().getCollection(DATABASE).updateOne(findById, updateObj);
        totalUpdatiados += 1;
        System.out.println("did update: " + totalUpdatiados);
    }

    private void insertGasto(String nomeUrna, String cargo, Politico p, String DATABASE) {
        Document old = new Document();
        old.put("cargo", cargo);
        old.put("nome_urna", nomeUrna);

        old.put("nome", p.getNome());
        old.put("nome_parlamentar", p.getNomeParlamentar());

        Politico politicoBiografia = new PoliticoBiografiaParser(jsonRequest("https://www.kimonolabs.com/api/json/ondemand/7met68b8?apikey=10deb955005b151ee7f6d2d2c796cde6&kimpath1=" + nomeUrna)).parse();
        old.put("biografia", politicoBiografia.getBiografia());
        old.put("email", p.getEmail());
        old.put("foto", p.getFoto());
        old.put("matricula", p.getMatricula());
        old.put("situacao", politicoBiografia.getSituacao());
        old.put("uf", p.getUf());
        MongoSingletonClient.getDb().getCollection(DATABASE).insertOne(old);
    }*/

    private Map jsonRequest(String restUrl) {
        Map callBack = new HashMap<>();
        try {
            RestClient client = RestClient.builder().build();

            Map entity = client.get(restUrl, null, Map.class);
            if (entity == null) return callBack;

            Map results = (Map) entity.get("results");
            if (results == null || results.isEmpty()) return callBack;

            List<Map> dados = (List<Map>) results.get("dados");
            if (dados == null || dados.isEmpty()) return callBack;

            callBack = dados.get(0);
            return callBack;
        } catch (Exception e) {
            return callBack;
        }
    }

    private List<Politico> pegaTodoMundo() {
        List<Politico> politicos = politicoService.listSenadores();
        politicos.addAll(politicoService.listPoliticos());
        return politicos;
    }

    @RequestMapping(value = "/api/senadores/politicos", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Politico> getPoliticoSenadores() {
        return politicoService.listSenadores();
    }

    @Cacheable("RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/{matricula}", method = RequestMethod.GET)
    public
    @ResponseBody
    Politico getPolitico(@PathVariable("matricula") String matricula) {
        return politicoService.getPolitico(matricula);
    }

    @Cacheable("ASSIDUIDADE_RESPONSE_CACHE")
    @RequestMapping(value = "/api/politico/assiduidade/{matricula}", method = RequestMethod.GET)
    public
    @ResponseBody
    PoliticoAssiduidade getAssiduidade(@PathVariable("matricula") String matricula) {
        return politicoService.getPoliticoAssiduidade(matricula);
    }

    @RequestMapping(value = "/api/dashboard", method = RequestMethod.GET)
    public
    @ResponseBody
    List<DashBoard> getDashboard() {
        return dashBoardService.findDashBoardDatas();
    }

    @RequestMapping(value = "/api/politico/gastos/{nomePolitico}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Gasto> getGastosPorPolitico(@PathVariable("nomePolitico") String nomePolitico) {
        return politicoService.getGastosPorPolitico(nomePolitico);
    }


    /*public static class SQLiteJDBC {
        public static Connection getConn() {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection("jdbc:sqlite:/home/azureuser/acordei/eleicoes.db");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
                return null;
            }
        }
    }*/


}
