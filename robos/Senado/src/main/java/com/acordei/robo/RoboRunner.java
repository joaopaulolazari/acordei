package com.acordei.robo;

import com.acordei.robo.handler.SenadoNodeHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class RoboRunner {

    public static int QTD_HANDLER_UPDATE = 0;
    public static int QTD_HANDLER = 0;
    static MongoDatabase db;

    public static MongoDatabase getDb(){
        if ( db != null ) return db;

        MongoClient cliente = new MongoClient("localhost",new MongoClientOptions.Builder().build());
        db = cliente.getDatabase("transparencia");
        return db;
    }

    static void parseCsv() throws IOException, SAXException, ParserConfigurationException {
        CSVFormat csvFileFormat = CSVFormat.newFormat( ";".charAt(0));
        csvFileFormat.withQuote(Character.valueOf('\"'));
        FileReader fileReader = new FileReader("/home/azureuser/2015.csv");
        //FileReader fileReader = new FileReader("/Users/deivison/Downloads/2015.csv");
        CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
        List<CSVRecord> csvRecords = csvFileParser.getRecords();
        for (int i = 1; i < csvRecords.size(); i++) {
           new SenadoNodeHandler().process(csvRecords.get(i),i);
        }
        fileReader.close();
        csvFileParser.close();
        System.out.println("did insert ::" + RoboRunner.QTD_HANDLER);
        System.out.println("did update ::" + RoboRunner.QTD_HANDLER_UPDATE);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        RoboRunner.parseCsv();

    }
}
