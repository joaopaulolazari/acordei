package com.acordei.api.dao;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstatisticaDao {
    @Autowired
    private MongoSingletonClient client;

    public void test(){
        FindIterable<Document> politicos = client.getDb().getCollection("politicos").find();
    }
}
