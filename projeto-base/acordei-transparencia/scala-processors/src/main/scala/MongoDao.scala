import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument

/**
 * Created by joelcorrea on 22/11/14.
 */
object MongoDao extends App {
def main(args: Array[String]) = {
  import reactivemongo.api._
  import scala.concurrent.ExecutionContext.Implicits.global

  lazy val driver: MongoDriver = new MongoDriver
  lazy val connection: MongoConnection = driver.connection(List("localhost"))
  lazy val db: DefaultDB = connection("transparencia_gastos")

  db[BSONCollection]("rs_2013_gastos_executivo_legislativo_judiciario").insert(BSONDocument(( "testKey" -> BSONString("testValue") )))
}
}
