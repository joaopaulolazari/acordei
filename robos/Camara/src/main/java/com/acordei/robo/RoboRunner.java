package com.acordei.robo;

import com.acordei.robo.handler.CamaraNodeHandler;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.scireum.open.xml.XMLReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class RoboRunner {

    public static int QTD_HANDLER_UPDATE = 0;
    static String DESPESA_HANDLER = "DESPESA";
    public static int QTD_HANDLER = 0;
    static MongoDatabase db;

    public static MongoDatabase getDb(){
        if ( db != null ) return db;

        MongoClient cliente = new MongoClient("acordei.cloudapp.net",new MongoClientOptions.Builder()
                .connectTimeout(999999999)
                .heartbeatConnectTimeout(999999999)
                .heartbeatSocketTimeout(999999999)
                .serverSelectionTimeout(999999999)
                .socketTimeout(999999999).build());

        db = cliente.getDatabase("transparencia");
        return db;
    }

    static void parseXml() throws IOException, SAXException, ParserConfigurationException {
        XMLReader reader = new XMLReader();
        reader.addHandler(DESPESA_HANDLER, new CamaraNodeHandler());
        reader.parse(new FileInputStream("/home/azureuser/AnoAtual.xml"));
        //reader.parse(new FileInputStream("/Users/deivison/Downloads/AnoAtual.xml"));
        //debugKeys();
        System.out.println("DONE");

    }

    /*static void debugKeys(){
        Set<String> list = getDb().keys("*");
        Iterator<String> keys = list.iterator();
        while(keys.hasNext()){
            String key = keys.next();
            System.out.println("List of stored keys:: " + keys.next());
        }
    }*/
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        RoboRunner.parseXml();

    }
}
