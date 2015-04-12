package com.acordei.api.parser;

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

public class PoliticoAssiduidadeParser {
    private Logger logger = Logger.getLogger(PoliticoAssiduidadeParser.class);
    private Document response;

    public PoliticoAssiduidadeParser(Document response) {
        this.response = response;
    }

    public PoliticoAssiduidade parse(){
        PoliticoAssiduidade politicoAssiduidade = new PoliticoAssiduidade();
        List<PoliticoAssiduidadeEvento> result = Lists.newArrayList();
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList dataEvento = (NodeList) xPath.compile("//dia/data").evaluate(response, XPathConstants.NODESET);
            NodeList frequenciaDia = (NodeList) xPath.compile("//dia/frequencianoDia").evaluate(response, XPathConstants.NODESET);
            for (int i = 0; i < frequenciaDia.getLength(); i++) {
                String data = dataEvento.item(i).getFirstChild().getNodeValue();
                String frequencia = frequenciaDia.item(i).getFirstChild().getNodeValue();
                result.add(new PoliticoAssiduidadeEvento(data, frequencia));
            }
        } catch (Exception e) {
            logger.info("Ocorreu um erro ao tentar processar resposta.");
        }
        politicoAssiduidade.setEventos(result);

        return politicoAssiduidade;
    }
}
