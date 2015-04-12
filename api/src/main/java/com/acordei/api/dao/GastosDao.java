package com.acordei.api.dao;

import com.acordei.api.domain.Gasto;
import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by deivison on 4/12/15.
 */
@Component
public class GastosDao {

    @Autowired
    private MongoSingletonClient client;

    public List<Gasto> findGastosPorMatricula(String matricula) {
        List<Gasto> datas = Lists.newArrayList();
        FindIterable<Document> data = client.getDb().getCollection("politicosconsolidado_tmp").find(new Document("matricula", matricula));
        MongoCursor<Document> cursor = data.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            List<Document> gastos = document.get("gastos",List.class);
            for(Document gasto : gastos) {
                datas.add(new Gasto(gasto.getString("tipo"), gasto.getDouble("valor")));
            }
        }
        cursor.close();
        return datas;
    }
}
