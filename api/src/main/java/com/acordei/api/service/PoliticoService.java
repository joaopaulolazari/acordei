package com.acordei.api.service;

import com.acordei.api.domain.PoliticoProjetosDeLei;
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

/**
 * Origem dos dados:
 * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2011&datApresentacaoIni=14/11/2011&datApresentacaoFim=16/11/2011&parteNomeAutor=&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=
 * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarSiglasTipoProposicao
 *
 * # Filtro de Propostas pelo nome do candidato:
 * http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2015&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=rotta&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=2
 */
@Service
public class PoliticoService {
    private Logger logger = Logger.getLogger(PoliticoService.class);
    private String WS_URL = "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=$nomeAutor&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=";

    public List<PoliticoProjetosDeLei> findProjetosDeLei(String nomeAutor) {
        Document response = requestProjetosDeLei(WS_URL.replace("$nomeAutor", nomeAutor));
        return parseResponse(response);
    }

    private Document requestProjetosDeLei(String wsUrl){
        Document result = null;
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            result = dBuilder.parse(wsUrl);
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar fazer o parsing da resposta.");
        }
        return result;
    }

    private List<PoliticoProjetosDeLei> parseResponse(Document response){
        List<PoliticoProjetosDeLei> result = Lists.newArrayList();
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList nomes = (NodeList) xPath.compile("//proposicao/tipoProposicao/nome").evaluate(response, XPathConstants.NODESET);
            NodeList descricoes = (NodeList) xPath.compile("//txtEmenta").evaluate(response, XPathConstants.NODESET);
            NodeList situacoes = (NodeList) xPath.compile("//situacao/descricao").evaluate(response, XPathConstants.NODESET);

            for (int i = 0; i < descricoes.getLength(); i++) {
                String descricao = descricoes.item(i).getFirstChild().getNodeValue();
                String nome = nomes.item(i).getFirstChild().getNodeValue();
                String situacao = situacoes.item(i).getFirstChild().getNodeValue();
                result.add(new PoliticoProjetosDeLei(nome, descricao, situacao));
            }
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar processar resposta.");
        }

        return result;
    }

}
