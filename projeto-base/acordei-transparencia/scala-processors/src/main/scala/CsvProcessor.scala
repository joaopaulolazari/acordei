import java.io.FileWriter
import java.text.Normalizer

import akka.actor.{ActorSystem, Props}
import reactivemongo.bson.{BSONDocument, BSONDouble, BSONString}
import workpulling.WorkPullingPattern._
import workpulling._

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Success, Failure}

case class CommandLineArgs(csvFile:String = "", db:String = "", dbCollection:String="", workers:Int=5, encoding:String="ISO-8859-1",appendToJson:String="", logRoot:String="", skip:Int=0, retries:Int=5)

object CsvProcessorApp extends App {
  //val system = ActorSystem("CSV-File-Processor")
  //val master = system.actorOf(Props[Master[Iterable[String]]], "master")

  val parser = new scopt.OptionParser[CommandLineArgs]("scopt"){
    head("CsvProcessor", "0.1.x")
    opt[String]('f', "file") required() valueName("<file>") action { (x, c) =>
      c.copy(csvFile = x) } text("(CSV) File is a required property")
    opt[String]('d', "db") valueName("<database>") action { (x, c) =>
      c.copy(db = x) }
    opt[String]('c', "db_collection") valueName("<db_collection>") action { (x, c) =>
      c.copy(dbCollection = x) }
    opt[Int]('w', "workers") valueName("<workers>") action { (x, c) =>
      c.copy(workers = x) }
    opt[String]('a', "append") valueName("<append>") action { (x, c) =>
      c.copy(appendToJson = x) }
    opt[String]('l', "logroot") required() valueName("<logroot>") action { (x, c) =>
      c.copy(logRoot = x) }
  }

  /*
  def withActors(config:CommandLineArgs) = {
    val lines = Source.fromFile(config.csvFile, config.encoding).getLines()
    val dao = new MongoDao(config.db)

    def newEpic[String](work: Iterator[String]) = new Epic[String] { override def iterator = work }

    def worker = system.actorOf(Props(new Worker[(String,String,Int)](master) {
      override def doWork(work: (String,String,Int)) = Future {
        new CsvProcessor().process(config,work._1,work._2,dao,work._3)
      }
    }))

    for(i <- 1 to config.workers) master ! RegisterWorker(worker)
    master ! (lines.next(),newEpic(lines))
  }*/

  parser.parse(args, CommandLineArgs()) map { config =>
    val lines = Source.fromFile(config.csvFile, config.encoding).getLines()
    val dao = new MongoDao(config.db)

    val head = lines.next
    var currentLine = 2
    lines.grouped(100000).foreach{ x =>
      x.map{line=>
        new CsvProcessor().process(config,head,line,dao,currentLine)
        currentLine += 1
      }
    }
  }

}

class CsvProcessor {
  import scala.concurrent.ExecutionContext.Implicits.global

  def isNumeric(x: String) = (x forall Character.isDigit) && !x.startsWith("0")

  def normalize(str:String) = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")

  def log(message:String, file:String): Future[Unit] =
    Future{
      val fw = new FileWriter(file, true)
      try fw.write(message)
      finally fw.close()
    }

  def logFailure(config:CommandLineArgs,lineNumber:Int, input:BSONDocument) =
    log(s"Not able to sync line $lineNumber\nInput: ${BSONDocument.pretty(input)}\n", s"${config.logRoot}acordei-failure.log")

  def logSuccess(config:CommandLineArgs,lineNumber:Int) =
    log(s"Synced line $lineNumber\n", s"${config.logRoot}acordei-successfull.log")

  def process(config:CommandLineArgs, header:String, line:String, dao:MongoDao, currentLine:Int) = {
    val bson: BSONDocument = BSONDocument( (header.split(";") zip line.split(";")).map(serializeBson) )
    dao.insert(config.dbCollection, bson).onComplete{
      case Success(lastError) => {logSuccess(config,currentLine)}
      case Failure(e) => logFailure(config,currentLine, bson)
    }
  }

  def serializeBson(fieldAndProperties: (String,String)) ={
    val field = fieldAndProperties._1
    val property = fieldAndProperties._2
    val propertyValue = if(isNumeric(field)) BSONDouble(property.toDouble) else BSONString(property)
    ( normalize(field) -> propertyValue )
  }
}
