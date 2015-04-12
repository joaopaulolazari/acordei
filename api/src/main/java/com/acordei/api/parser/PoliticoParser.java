package com.acordei.api.parser;

import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoAssiduidadeEvento;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.List;

public class PoliticoParser {
    private Logger logger = Logger.getLogger(PoliticoParser.class);
    private Document response;

    public PoliticoParser(Document response) {
        this.response = response;
    }

    public List<Politico> parse(){
        List<Politico> result = Lists.newArrayList();
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList matriculas = (NodeList) xPath.compile("//deputado/matricula").evaluate(response, XPathConstants.NODESET);
            NodeList nomes = (NodeList) xPath.compile("//deputado/nome").evaluate(response, XPathConstants.NODESET);
            NodeList fotos = (NodeList) xPath.compile("//deputado/urlFoto").evaluate(response, XPathConstants.NODESET);
            NodeList ufs = (NodeList) xPath.compile("//deputado/uf").evaluate(response, XPathConstants.NODESET);

            for (int i = 0; i < matriculas.getLength(); i++) {
                String matricula = matriculas.item(i).getFirstChild().getNodeValue();
                String nome = nomes.item(i).getFirstChild().getNodeValue();
                String foto = fotos.item(i).getFirstChild().getNodeValue();
                String uf = ufs.item(i).getFirstChild().getNodeValue();
                result.add(new Politico(matricula, nome, foto, uf));
            }
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar processar resposta.");
        }

        return result;
    }

}
