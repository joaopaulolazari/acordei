package com.acordei.robo;

import com.acordei.robo.handler.CamaraNodeHandler;
import com.scireum.open.xml.XMLReader;
import org.xml.sax.SAXException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
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
        shardInfo.setPassword("sFgits7PxyuzM6liZWLdlZBawT2KqqfdhapOsECYyOo="); /* Use your access key. */
        System.out.println("Default conn timeout :: " + shardInfo.getConnectionTimeout());
        System.out.println("Default so   timeout :: " + shardInfo.getSoTimeout());
        shardInfo.setConnectionTimeout(30000000);
        shardInfo.setSoTimeout(30000000);
        System.out.println("updated Default conn timeout :: " + shardInfo.getConnectionTimeout());
        System.out.println("updated Default so   timeout :: " + shardInfo.getSoTimeout());
        db = new Jedis(shardInfo);
        return db;
    }
    public static Jedis forceReconnect(){
        db = null;
        return getDb();
    }

    static void parseXml() throws IOException, SAXException, ParserConfigurationException {
        XMLReader reader = new XMLReader();
        reader.addHandler(DESPESA_HANDLER, new CamaraNodeHandler());
        reader.parse(new FileInputStream("/home/azureuser/AnoAtual.xml"));
        System.out.println("DONE");
        // debugKeys();
    }

    static void debugKeys(){
        Set<String> list = getDb().keys("*");
        Iterator<String> keys = list.iterator();
        while(keys.hasNext()){
            String key = keys.next();
            db.del(key);
            //System.out.println("List of stored keys:: " + keys.next());
        }
        System.out.println("DOne");

    }
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        RoboRunner.parseXml();

    }
}
