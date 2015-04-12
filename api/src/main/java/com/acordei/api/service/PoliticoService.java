package com.acordei.api.service;

import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.acordei.api.parser.PoliticoAssiduidadeParser;
import com.acordei.api.parser.PoliticoParser;
import com.acordei.api.parser.PoliticoProjetoParser;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.List;
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
        Document response = request(uri.replace("$nomeAutor", nomeAutor));
        return new PoliticoProjetoParser(response).parse();
    }

    /**
     * Origem dos dados:
     * http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar=1
     * Por hora retornando periodo de vigencia atual.
     */
    public Politico getPolitico(String matricula){
        Document detalhesPolitico = request("http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados");
        Politico politico = new PoliticoParser(detalhesPolitico).parse().stream().findFirst().get();
        return politico;
    }


    public PoliticoAssiduidade getPoliticoAssiduidade(String matricula) {
        String uri = "http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar="+matricula;
        Document assiduidadeResponse = request(uri);
        return new PoliticoAssiduidadeParser(assiduidadeResponse).parse();
    }

//    public List<Politico> listPoliticos() {
//        Document response = request("http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados");
//        return new PoliticoParser(response).parse();
//    }

//    public List<Politico> getPoliticosByEstado(String ufId) {
//        List<Politico> politicos = listPoliticos();
//        return politicos.stream().filter(p -> p.getUf().equalsIgnoreCase(ufId)).collect(Collectors.toList());
//    }


    private Document request(String wsUrl){
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
