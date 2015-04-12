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

    public List<DashBoard> findDashBoardDatas() {
        List<DashBoard> datas = Lists.newArrayList();
        FindIterable<Document> data = client.getDb().getCollection("dashboard").find();
        MongoCursor<Document> cursor = data.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();

            DashBoard dashBoard = new DashBoard();
            dashBoard.setConteudo(document.getString("conteudo"));
            dashBoard.setEixoYLabels(document.get("eixoylabels", ArrayList.class));
            dashBoard.setEixoYValores(document.get("eixoyvalores", ArrayList.class));
            dashBoard.setTitulo(document.getString("titulo"));
            dashBoard.setTipo(document.getString("tipo"));
            datas.add(dashBoard);
        }
        cursor.close();
        return datas;
    }
}
