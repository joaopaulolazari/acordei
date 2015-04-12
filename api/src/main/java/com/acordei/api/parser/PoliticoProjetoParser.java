package com.acordei.api.parser;

import com.acordei.api.domain.PoliticoProjetosDeLei;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.List;

public class PoliticoProjetoParser {
    private Logger logger = Logger.getLogger(PoliticoProjetoParser.class);
    private Document response;

    public PoliticoProjetoParser(Document response) {
        this.response = response;
    }

    public List<PoliticoProjetosDeLei> parse(){
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
