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

public class PoliticoSenadorParser {
    private Logger logger = Logger.getLogger(PoliticoSenadorParser.class);
    private Document response;

    public PoliticoSenadorParser(Document response) {
        this.response = response;
    }

    public List<Politico> parse(){
        List<Politico> result = Lists.newArrayList();
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            NodeList matriculas = (NodeList) xPath.compile("//Parlamentar/CodigoParlamentar").evaluate(response, XPathConstants.NODESET);
            NodeList nomes = (NodeList) xPath.compile("//Parlamentar/NomeCompleto").evaluate(response, XPathConstants.NODESET);
            NodeList fotos = (NodeList) xPath.compile("//Parlamentar/Foto").evaluate(response, XPathConstants.NODESET);
            NodeList ufs = (NodeList) xPath.compile("//Parlamentar/SiglaUf").evaluate(response, XPathConstants.NODESET);
            NodeList emails = (NodeList) xPath.compile("//Parlamentar/EnderecoEletronico").evaluate(response, XPathConstants.NODESET);
            NodeList nomesParlamentar = (NodeList) xPath.compile("//Parlamentar/NomeParlamentar").evaluate(response, XPathConstants.NODESET);

            for (int i = 0; i < matriculas.getLength(); i++) {
                String matricula = matriculas.item(i).getFirstChild().getNodeValue();
                String nome = nomes.item(i).getFirstChild().getNodeValue();
                String nomeParlamentar = formatNomeParlamentar(nomesParlamentar.item(i).getFirstChild().getNodeValue());
                String foto = fotos.item(i).getFirstChild().getNodeValue();
                String uf = ufs.item(i).getFirstChild().getNodeValue();
                String email = emails.item(i).getFirstChild().getNodeValue();
                result.add(new Politico(matricula, nome, nomeParlamentar, email, foto, uf));
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
