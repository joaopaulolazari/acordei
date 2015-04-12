package com.acordei.api.service;

import com.acordei.api.dao.GastosDao;
import com.acordei.api.domain.*;
import com.acordei.api.parser.*;
import com.google.common.collect.Lists;
import http.rest.RestClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoliticoService {
    private Logger logger = Logger.getLogger(PoliticoService.class);

    @Autowired GastosDao gastosDao;
    /**
     * Origem dos dados:
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2011&datApresentacaoIni=14/11/2011&datApresentacaoFim=16/11/2011&parteNomeAutor=&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarSiglasTipoProposicao
     * # Filtro de Propostas pelo nome do candidato:
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2015&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=rotta&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=2
     */
    public PoliticoPropostas findProjetosDeLei(String nomeAutor) {
        String uri = "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=$nomeAutor&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=";
        Document response = xmlRequest(uri.replace("$nomeAutor", nomeAutor));
        PoliticoPropostas propostas = new PoliticoPropostasParser(response).parse();
        calcularMetricas(propostas);
        return propostas;
    }

    /**
     * Ids situacao
     * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarSituacoesProposicao
      */
    private void calcularMetricas(PoliticoPropostas propostas) {
        List<String> statusArquivado = Lists.newArrayList("923");
        List<String> statusRejeitado = Lists.newArrayList("937", "1292");
        List<String> statusAprovado = Lists.newArrayList("1140");
        long aprovadas = propostas.getProjetos().stream().filter(p -> statusAprovado.contains(p.getIdSituacao())).count();
        long rejeitadas = propostas.getProjetos().stream().filter(p -> statusRejeitado.contains(p.getIdSituacao())).count();
        long arquivadas = propostas.getProjetos().stream().filter(p -> statusArquivado.contains(p.getIdSituacao())).count();
        propostas.setPropostasAprovadas(aprovadas);
        propostas.setPropostasArquivadas(arquivadas);
        propostas.setPropostasRejeitadas(rejeitadas);
    }

    /**
     * Origem dos dados:
     * http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar=1
     * Por hora retornando periodo de vigencia atual.
     */
    public Politico getPolitico(String matricula) {
        String uri = "http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDetalhesDeputado?numLegislatura=&ideCadastro="+matricula;
        Document detalhesPolitico = xmlRequest(uri);
        Politico politico = new PoliticoDetailParser(detalhesPolitico).parse().stream().findFirst().get();

        Politico politicoBiografia = new PoliticoBiografiaParser(jsonRequest("https://www.kimonolabs.com/api/json/ondemand/bx2r958a?apikey=10deb955005b151ee7f6d2d2c796cde6&kimpath1=" + politico.getNomeParlamentar())).parse();
        politico.setBiografia(politicoBiografia.getBiografia());
        politico.setSituacao(politicoBiografia.getSituacao());
        return politico;
    }

    public PoliticoAssiduidade getPoliticoAssiduidade(String matricula){
        String uri = "http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar="+matricula;
        Document response = xmlRequest(uri);
        return new PoliticoAssiduidadeParser(response).parse();
    }

    public List<Politico> listPoliticos() {
        Document response = xmlRequest("http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados");
        return new PoliticoParser(response).parse();
    }

    public List<Politico> getPoliticosByEstado(String ufId) {
        List<Politico> politicos = listPoliticos();
        return politicos.stream().filter(p -> p.getUf().equalsIgnoreCase(ufId)).collect(Collectors.toList());
    }
    public List<Gasto> getGastosPorPolitico(String nomePolitico){
        return gastosDao.findGastosPorPolitico(nomePolitico);
    }

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

    private Document xmlRequest(String wsUrl) {
        Document result = null;
        try {
            URL url = new URL(wsUrl);
            URLConnection connection = url.openConnection();
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            result = dBuilder.parse(connection.getInputStream());

        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar fazer o parsing da resposta.");
        }
        return result;
    }


}
