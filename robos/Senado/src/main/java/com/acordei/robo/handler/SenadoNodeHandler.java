package com.acordei.robo.handler;

import com.acordei.robo.RoboRunner;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;


public class SenadoNodeHandler  {

    private String expenseType;
    private Double expenseValue;
    private static final String DATABASE = "politicos_tmp";
    int maxTry = 30;

    private Double formatStringToDouble(String value){
        String expenseValue = clearString(value);
        if ( expenseValue.isEmpty() ) return  0D;

        return new Double(expenseValue);
    }
    public Double findAndFormatDoubleColumn(CSVRecord record,int index,int line){


        String expenseValue = clearString(record.get(index));
        try{
            return new Double(expenseValue);
        }catch(java.lang.NumberFormatException e){
            if ( index+1 > maxTry ) {
                System.out.println("Não achei valor para linha: "+line+", tentei com : "+expenseValue);
                return 0D;
            }else {
                return findAndFormatDoubleColumn(record, index + 1, line);
            }
        }
    }

    private String clearString(String expenseValue) {
        expenseValue = expenseValue.trim();
        expenseValue = expenseValue.replace(".", "").replace(",", ".").replaceAll("\"", "");
        return expenseValue;
    }

    public void process(CSVRecord record,int line) {
        try {
            String key = generateKeyTemp("br", record.get(2));
            expenseType = record.get(3);
            expenseValue = findAndFormatDoubleColumn(record,9,line);
            insertOrUpdate(record, key, createBaseDocumentWithKey(key));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private void insertOrUpdate(CSVRecord node, String key, Document searchQuery) throws XPathExpressionException {
        if ( !updateDocumentIfNeed(searchQuery) ) createPolitiqueWithExpense(node, key);
    }

    private void createPolitiqueWithExpense(CSVRecord node, String key) throws XPathExpressionException {
        Document document = createBaseDocumentWithKey(key);
        document.put("nome", node.get(2));
        document.put("uf", "br");
        List<Document> expenseList = new ArrayList<>();
        insertNewExpense(expenseList);
        document.put("gastos", expenseList);
        RoboRunner.getDb().getCollection(DATABASE).insertOne(document);
        RoboRunner.QTD_HANDLER += 1;
    }

    private boolean updateDocumentIfNeed(Document searchQuery){
        FindIterable cursor = RoboRunner.getDb().getCollection(DATABASE).find(searchQuery);
        MongoCursor c = cursor.iterator();

        if ( !c.hasNext() )  return false;

        Document old = (Document) c.next();
        List<Document> gastos = (List<Document>) old.get("gastos");
        insertOrUpdateExpense(gastos);

        updateDocument(searchQuery, old);

        c.close();
        RoboRunner.QTD_HANDLER_UPDATE += 1;
        return true;
    }

    private void insertOrUpdateExpense(List<Document> gastos) {
        if ( !updateExpenseByType(gastos)) insertNewExpense(gastos);
    }

    private void updateDocument(Document searchQuery, Document old) {
        Document updateObj = new Document();
        updateObj.put("$set", old);
        RoboRunner.getDb().getCollection(DATABASE).updateOne(searchQuery, updateObj);
    }

    private void insertNewExpense(List<Document> gastos) {
        Document expense = new Document();
        expense.put("tipo",expenseType);
        expense.put("valor",expenseValue);
        gastos.add(expense);
    }

    private boolean updateExpenseByType(List<Document> gastos) {
        boolean find = false;
        for(Document gasto : gastos){
            if ( gasto.get("tipo").toString().equals(expenseType) ){
                gasto.put("valor", formatStringToDouble("" + gasto.get("valor")) + expenseValue);
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

    String generateKeyTemp(String uf,String name){
        return generateKey(uf,name);
    }
    String generateKey(String uf,String name){
        return new String(Base64.encodeBase64((uf + "_" + name).getBytes()));
    }

}
