package com.acordei.api.service;

import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.google.common.collect.Lists;
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

    public List<PoliticoProjetosDeLei> findProjetosDeLei() {
        String wsUrl = "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=2011&datApresentacaoIni=14/11/2011&datApresentacaoFim=16/11/2011&parteNomeAutor=&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=";
        return parseResponse(wsUrl);
    }

    private List<PoliticoProjetosDeLei> parseResponse(String wsUrl){
        List<PoliticoProjetosDeLei> result = Lists.newArrayList();
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(wsUrl);
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList nomes = (NodeList) xPath.compile("//proposicao/tipoProposicao/nome").evaluate(doc, XPathConstants.NODESET);
            NodeList descricoes = (NodeList) xPath.compile("//txtEmenta").evaluate(doc, XPathConstants.NODESET);
            NodeList situacoes = (NodeList) xPath.compile("//situacao/descricao").evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < descricoes.getLength(); i++) {
                String descricao = descricoes.item(i).getFirstChild().getNodeValue();
                String nome = nomes.item(i).getFirstChild().getNodeValue();
                String situacao = situacoes.item(i).getFirstChild().getNodeValue();
                result.add(new PoliticoProjetosDeLei(nome, descricao, situacao));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
