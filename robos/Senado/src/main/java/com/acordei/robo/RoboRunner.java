package com.acordei.robo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.xml.sax.SAXException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class RoboRunner {

    static String DESPESA_HANDLER = "DESPESA";
    public static int QTD_HANDLER = 0;
    static Jedis db;

    public static Jedis getDb(){
        if ( db != null ) return db;

        JedisShardInfo shardInfo = new JedisShardInfo("transparenciadb.redis.cache.windows.net", 6379);
        shardInfo.setConnectionTimeout(1800000);
        shardInfo.setPassword("sFgits7PxyuzM6liZWLdlZBawT2KqqfdhapOsECYyOo="); /* Use your access key. */
        db = new Jedis(shardInfo);

        return db;
    }

    static void parseCsv() throws IOException, SAXException, ParserConfigurationException {
        CSVFormat csvFileFormat = CSVFormat.newFormat(';');
        csvFileFormat.withQuote(Character.valueOf('\"'));
        FileReader fileReader = new FileReader("/Users/deivison/Downloads/2015.csv");
        CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
        List<CSVRecord> csvRecords = csvFileParser.getRecords();
        for (int i = 1; i < csvRecords.size(); i++) {
            CSVRecord record = csvRecords.get(i);
            String name = record.get(2);
            String expenseType = record.get(3);
            String expenseValue = record.get(9);
            String key = generateKeyTemp("br",name);

            RoboRunner.getDb().lpush(key,expenseType+"="+expenseValue);
            RoboRunner.getDb().flushAll();
            RoboRunner.QTD_HANDLER += 1;
            System.out.println("did ::"+RoboRunner.QTD_HANDLER);
        }

        fileReader.close();
        csvFileParser.close();

    }

    static String generateKeyTemp(String uf,String name){
        return generateKey(uf,name)+"_tmp";
    }
    static String generateKey(String uf,String name){
        return new String(Base64.encodeBase64((uf + "_" + name).getBytes()));
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        RoboRunner.parseCsv();

    }
}
