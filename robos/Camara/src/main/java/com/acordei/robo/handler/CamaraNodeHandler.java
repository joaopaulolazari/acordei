package com.acordei.robo.handler;

import com.acordei.robo.RoboRunner;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.scireum.open.xml.NodeHandler;
import com.scireum.open.xml.StructuredNode;
import org.apache.commons.codec.binary.Base64;
import org.bson.Document;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;


public class CamaraNodeHandler implements NodeHandler {

    public static final String POLITICOS = "politicosconsolidado";
    private String expenseType;
    private Double expenseValue;

    public void process(StructuredNode node) {
        try {
            String key = generateKeyTemp(node.queryString("sgUF"), node.queryString("txNomeParlamentar"));
            expenseType = node.queryString("txtDescricao");
            expenseValue = new Double(node.queryString("vlrLiquido"));
            insertOrUpdate(node, key, createBaseDocumentWithKey(key));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        System.out.println("did insert ::" + RoboRunner.QTD_HANDLER);
        System.out.println("did update ::" + RoboRunner.QTD_HANDLER_UPDATE);
    }

    private void insertOrUpdate(StructuredNode node, String key, Document searchQuery) throws XPathExpressionException {
        if (!updateDocumentIfNeed(searchQuery)) createPolitiqueWithExpense(node, key);
    }

    private void createPolitiqueWithExpense(StructuredNode node, String key) throws XPathExpressionException {
        Document document = createBaseDocumentWithKey(key);
        document.put("id", node.queryString("ideCadastro"));
        List<Document> expenseList = new ArrayList<>();
        insertNewExpense(expenseList);
        document.put("gastos", expenseList);
        RoboRunner.getDb().getCollection(POLITICOS).insertOne(document);
        RoboRunner.QTD_HANDLER += 1;
    }

    private boolean updateDocumentIfNeed(Document searchQuery) {
        FindIterable cursor = RoboRunner.getDb().getCollection(POLITICOS).find(searchQuery);
        MongoCursor c = cursor.iterator();

        if (!c.hasNext()) return false;

        Document old = (Document) c.next();
        List<Document> gastos = (List<Document>) old.get("gastos");
        insertOrUpdateExpense(gastos);

        updateDocument(searchQuery, old);

        c.close();
        RoboRunner.QTD_HANDLER_UPDATE += 1;
        return true;
    }

    private void insertOrUpdateExpense(List<Document> gastos) {
        if (!updateExpenseByType(gastos)) insertNewExpense(gastos);
    }

    private void updateDocument(Document searchQuery, Document old) {
        Document updateObj = new Document();
        updateObj.put("$set", old);
        RoboRunner.getDb().getCollection(POLITICOS).updateOne(searchQuery, updateObj);
    }

    private void insertNewExpense(List<Document> gastos) {
        Document expense = new Document();
        expense.put("tipo", expenseType);
        expense.put("valor", expenseValue);
        gastos.add(expense);
    }

    //TODO: Usar reduce
    private boolean updateExpenseByType(List<Document> gastos) {
        boolean find = false;
        for (Document gasto : gastos) {
            if (gasto.get("tipo").toString().equals(expenseType)) {
                gasto.put("valor", new Double("" + gasto.get("valor")) + expenseValue);
                find = true;
                break;
            }
        }
        return find;
    }

    private Document createBaseDocumentWithKey(String key) {
        Document searchQuery = new Document();
        searchQuery.put("key", key);
        return searchQuery;
    }

    String generateKeyTemp(String uf, String name) {
        return generateKey(uf, name);
    }
    String generateKey(String uf, String name) {
        return new String(Base64.encodeBase64((uf + "_" + name).getBytes()));
    }

}
