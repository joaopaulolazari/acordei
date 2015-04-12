package com.acordei.api.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class MongoSingletonClient {
    private static final String HOST = "acordei.cloudapp.net";
    private static final String DB_NAME = "transparencia";
    private static MongoDatabase db;

    public static MongoDatabase getDb(){
        if ( db != null ) return db;

        int timeout = Integer.MAX_VALUE;
        MongoClient cliente = new MongoClient(HOST, new MongoClientOptions.Builder()
                .connectTimeout(timeout)
                .heartbeatConnectTimeout(timeout)
                .heartbeatSocketTimeout(timeout)
                .serverSelectionTimeout(timeout)
                .socketTimeout(timeout).build());

        MongoSingletonClient.db = cliente.getDatabase(DB_NAME);
        return MongoSingletonClient.db;
    }
}
