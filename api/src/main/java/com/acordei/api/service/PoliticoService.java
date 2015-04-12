package com.acordei.api.service;

import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.acordei.api.parser.PoliticoAssiduidadeParser;
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
     */
    public Politico getPolitico(String matricula){
        String uri = "http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasParlamentar?dataIni=01/02/2011&dataFim=31/12/2014&numMatriculaParlamentar="+matricula;
        Document response = request(uri);
        PoliticoAssiduidade assiduidade = new PoliticoAssiduidadeParser(response).parse();
        return new Politico(assiduidade);
    }

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
