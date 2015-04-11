package com.acordei.robo.handler;

import com.acordei.robo.RoboRunner;
import com.scireum.open.xml.NodeHandler;
import com.scireum.open.xml.StructuredNode;
import org.apache.commons.codec.binary.Base64;

import javax.xml.xpath.XPathExpressionException;


public class CamaraNodeHandler implements NodeHandler {

    public void process(StructuredNode node) {
        try {
            String key = generateKeyTemp(node.queryString("sgUF"), node.queryString("txNomeParlamentar"));
            String expenseType = node.queryString("txtDescricao");
            String expenseValue = node.queryString("vlrLiquido");
            RoboRunner.getDb().lpush(key,expenseType+"="+expenseValue);
            RoboRunner.getDb().flushAll();
            RoboRunner.QTD_HANDLER += 1;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        System.out.println("did ::"+RoboRunner.QTD_HANDLER);
    }

    String generateKeyTemp(String uf,String name){
        return generateKey(uf,name)+"_tmp";
    }
    String generateKey(String uf,String name){
        return new String(Base64.encodeBase64((uf + "_" + name).getBytes()));
    }

}
