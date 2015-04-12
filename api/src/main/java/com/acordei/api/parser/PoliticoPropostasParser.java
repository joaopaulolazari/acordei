package com.acordei.api.parser;

import com.acordei.api.domain.PoliticoProjetoDeLei;
import com.acordei.api.domain.PoliticoPropostas;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.List;

public class PoliticoPropostasParser {
    private Logger logger = Logger.getLogger(PoliticoPropostasParser.class);
    private Document response;

    public PoliticoPropostasParser(Document response) {
        this.response = response;
    }

    public PoliticoPropostas parse(){
        List<PoliticoProjetoDeLei> propostas = Lists.newArrayList();
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList nomes = (NodeList) xPath.compile("//proposicao/tipoProposicao/nome").evaluate(response, XPathConstants.NODESET);
            NodeList descricoes = (NodeList) xPath.compile("//txtEmenta").evaluate(response, XPathConstants.NODESET);
            NodeList descricaoSituacoes = (NodeList) xPath.compile("//situacao/descricao").evaluate(response, XPathConstants.NODESET);
            NodeList idSituacoes = (NodeList) xPath.compile("//situacao/id").evaluate(response, XPathConstants.NODESET);

            for (int i = 0; i < descricoes.getLength(); i++) {
                String descricao = descricoes.item(i).getFirstChild().getNodeValue();
                String nome = nomes.item(i).getFirstChild().getNodeValue();
                String descricaoSituacao = descricaoSituacoes.item(i).getFirstChild().getNodeValue();
                String idSituacao = idSituacoes.item(i).getFirstChild().getNodeValue();

                propostas.add(new PoliticoProjetoDeLei(nome, descricao, idSituacao, descricaoSituacao));
            }
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar processar resposta.");
        }

        return new PoliticoPropostas(propostas);
    }

}
