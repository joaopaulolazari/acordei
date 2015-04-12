package com.acordei.api.service;

import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.acordei.api.parser.PoliticoAssiduidadeParser;
import com.acordei.api.parser.PoliticoBiografiaParser;
import com.acordei.api.parser.PoliticoParser;
import com.acordei.api.parser.PoliticoProjetoParser;
import com.mongodb.util.JSON;
import http.rest.RestClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoliticoService {
    private Logger logger = Logger.getLogger(PoliticoService.class);

    /**
     * Origem dos dados:
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2011&datApresentacaoIni=14/11/2011&datApresentacaoFim=16/11/2011&parteNomeAutor=&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarSiglasTipoProposicao
     * # Filtro de Propostas pelo nome do candidato:
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2015&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=rotta&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=2
     */
    public List<PoliticoProjetosDeLei> findProjetosDeLei(String nomeAutor) {
        String uri = "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=$nomeAutor&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=";
        Document response = xmlRequest(uri.replace("$nomeAutor", nomeAutor));
        return new PoliticoProjetoParser(response).parse();
    }

    /**
     * Origem dos dados:
     * http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar=1
     * Por hora retornando periodo de vigencia atual.
     */
    public Politico getPolitico(String matricula){
        String uri = "http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar="+matricula;
        Document response = xmlRequest(uri);
        PoliticoAssiduidade assiduidade = new PoliticoAssiduidadeParser(response).parse();
        Politico politico = new PoliticoBiografiaParser(jsonRequest("https://www.kimonolabs.com/api/json/ondemand/bx2r958a?apikey=10deb955005b151ee7f6d2d2c796cde6&kimpath1="+assiduidade.getNomeParlamentar())).parse();
        politico.setAssiduidade(assiduidade);
        return politico;
    }

    public List<Politico> listPoliticos() {
        Document response = xmlRequest("http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados");
        return new PoliticoParser(response).parse();
    }

    public List<Politico> getPoliticosByEstado(String ufId) {
        List<Politico> politicos = listPoliticos();
        return politicos.stream().filter(p -> p.getUf().equalsIgnoreCase(ufId)).collect(Collectors.toList());
    }


    private Map jsonRequest(String restUrl){

        System.out.println("URL :" + restUrl);
        Map callBack = new HashMap<>();
        try {
            RestClient client = RestClient.builder().build();

            Map entity = client.get(restUrl, null, Map.class);
            if ( entity == null ) return callBack;

            Map results = (Map) entity.get("results");
            if ( results == null || results.isEmpty() ) return callBack;

            List<Map> dados = (List<Map>) results.get("dados");
            if ( dados == null || dados.isEmpty() ) return callBack;

            callBack = dados.get(0);
            return callBack;
        } catch (Exception e) {
            return callBack;
        }
    }

    private Document xmlRequest(String wsUrl){
        Document result = null;
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            result = dBuilder.parse(wsUrl);
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar fazer o parsing da resposta.");
        }
        return result;
    }

}
