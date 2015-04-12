package com.acordei.api.parser;

import com.acordei.api.domain.Politico;
import com.acordei.api.domain.PoliticoAssiduidade;
import com.acordei.api.domain.PoliticoAssiduidadeEvento;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.List;
import java.util.Map;

public class PoliticoBiografiaParser {
    private Logger logger = Logger.getLogger(PoliticoBiografiaParser.class);
    private Map response;

    public PoliticoBiografiaParser(Map response) {
        this.response = response;
    }

    public Politico parse(){
        Politico politico = new Politico();
        politico.setBiografia(""+response.get("biografia"));
        politico.setSituacao("" + response.get("situacao"));
        return politico;
    }
}
