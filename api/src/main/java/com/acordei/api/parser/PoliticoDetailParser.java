package com.acordei.api.parser;

import com.acordei.api.domain.Politico;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.text.Normalizer;
import java.util.List;

public class PoliticoDetailParser {

    private Logger logger = Logger.getLogger(PoliticoDetailParser.class);
    private Document response;

    public PoliticoDetailParser(Document response) {
        this.response = response;
    }

    public List<Politico> parse(){
        List<Politico> result = Lists.newArrayList();
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList nomes = (NodeList) xPath.compile("//Deputado/nomeCivil").evaluate(response, XPathConstants.NODESET);
            NodeList ufs = (NodeList) xPath.compile("//Deputado/ufRepresentacaoAtual").evaluate(response, XPathConstants.NODESET);
            NodeList emails = (NodeList) xPath.compile("//Deputado/email").evaluate(response, XPathConstants.NODESET);
            NodeList nomesParlamentar = (NodeList) xPath.compile("//Deputado/nomeParlamentarAtual").evaluate(response, XPathConstants.NODESET);

            for (int i = 0; i < nomes.getLength(); i++) {
                String nome = nomes.item(i).getFirstChild().getNodeValue();
                String nomeParlamentar = formatNomeParlamentar(nomesParlamentar.item(i).getFirstChild().getNodeValue());
                String uf = ufs.item(i).getFirstChild().getNodeValue();
                String email = emails.item(i).getFirstChild().getNodeValue();
                result.add(new Politico(null, nome, nomeParlamentar, email, null, uf));
            }
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar processar resposta.");
        }

        return result;
    }

    private String formatNomeParlamentar(String nome){
        String normalized = removeSpecialCharacters(nome).toLowerCase().replace(" ","-");
        return  ( normalized.contains("-") ? normalized.substring(0,normalized.lastIndexOf("-")) : normalized);
    }
    private String removeSpecialCharacters(String text){
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
