package com.acordei.api.parser;

import com.acordei.api.domain.Politico;
import org.apache.log4j.Logger;

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
