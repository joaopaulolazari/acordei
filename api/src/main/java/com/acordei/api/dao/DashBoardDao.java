package com.acordei.api.dao;

import com.acordei.api.domain.DashBoard;
import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DashBoardDao {

    @Autowired
    private MongoSingletonClient client;

    public List<DashBoard> findDashBoardData() {
        List<DashBoard> datas = Lists.newArrayList();
        FindIterable<Document> data = client.getDb().getCollection("dashboard").find();
        MongoCursor<Document> cursor = data.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            datas.add(new DashBoard(
                document.getString("tipo"),
                document.getString("titulo"),
                document.getString("conteudo"),
                document.getString("nomePoliticoRelacionado"),
                document.getString("fotoPoliticoRelacionado"),
                document.getString("idPoliticoRelacionado"),
                document.getInteger("totalfatias"),
                null,
                null
            ));
        }
        cursor.close();
        return datas;
    }
}
