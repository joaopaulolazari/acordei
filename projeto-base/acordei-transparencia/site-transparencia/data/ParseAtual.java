import java.io.FileInputStream;

import javax.xml.xpath.XPathExpressionException;

import com.scireum.open.xml.NodeHandler;
import com.scireum.open.xml.StructuredNode;
import com.scireum.open.xml.XMLReader;

import com.mongodb.Mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.BasicDBList;
import java.util.List;
import java.util.Set;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
* Small example class which show how to use the {@link XMLReader}.
*/
public class ParseAtual {

  public static void parseAnoAtual(Mongo mongoClient,DB db,final DBCollection coll)throws Exception {
    parseFiles(mongoClient,db,coll,"AnoAtual","2015");
  }

  public static void parseFiles(Mongo mongoClient,DB db,final DBCollection coll,String file,final String ano)throws Exception {

    XMLReader r = new XMLReader();
    r.addHandler("DESPESA", new NodeHandler() {

      @Override
      public void process(StructuredNode node) {
        try {
          BasicDBObject query = new BasicDBObject("nome", node.queryString("txNomeParlamentar")).append("uf",node.queryString("sgUF"));
          DBCursor cursor = coll.find(query);
          if ( !cursor.hasNext() )  {
            BasicDBList list = new BasicDBList();
            list.add(new BasicDBObject("tipo",node.queryString("txtDescricao"))
            .append("data",node.queryString("datEmissao"))
            .append("valor",new Double(node.queryString("vlrLiquido"))));


            BasicDBObject politique = new BasicDBObject("nome", node.queryString("txNomeParlamentar"))
            .append("uf",node.queryString("sgUF"))
            .append("total_"+ano,node.queryString("vlrLiquido"))
            .append("idCadastro",node.queryString("ideCadastro"))
            .append("nuCarteiraParlamentar",node.queryString("nuCarteiraParlamentar"))
            .append("partido",node.queryString("sgPartido"))
            .append("gastos_"+ano,list);
            coll.insert(politique);
          } else{

            BasicDBObject item = (BasicDBObject) cursor.next();
            if ( item.get("gastos_"+ano) == null ){
              item.put("gastos_"+ano,new BasicDBList());
            }
            BasicDBList gg = (BasicDBList) item.get("gastos_"+ano);
            gg.add(new BasicDBObject("tipo",node.queryString("txtDescricao"))
            .append("data",node.queryString("datEmissao"))
            .append("valor",new Double(node.queryString("vlrLiquido"))));
            double total = 0;
            for(int i=0; i < gg.size(); i++){
              BasicDBObject oo = (BasicDBObject ) gg.get(i);
              total += new Double(oo.getString("valor"));
            }
            item.put("total_"+ano,total)                             ;
            coll.update(query,item);
          }
        } catch (XPathExpressionException e) {
          e.printStackTrace();
        }
      }
    });
    r.parse(new FileInputStream(file+".xml"));
  }
  public static void main(String[] args) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
    String formattedDate = sdf.format(new Date());

    System.out.println("Start =>"+formattedDate);
    Mongo mongoClient = new Mongo();
    DB db = mongoClient.getDB( "transparencia" );
    final DBCollection coll = db.getCollection("gastos_politicos");
    parseAnoAtual(mongoClient,db,coll);
    sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
    formattedDate = sdf.format(new Date());
    System.out.println("End =>"+formattedDate);
    System.out.println("FIM");
  }
}
// "nome": "LUCIO VIEIRA LIMA",
