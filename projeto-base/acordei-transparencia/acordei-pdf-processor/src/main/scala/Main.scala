import java.io._
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

/**
 * Created by joelcorrea on 07/12/14.
 */
object PdfToText {
  System.setProperty("java.awt.headless", "true")

  def getTextFromPdf(startPage: Int, endPage: Int, filename: String): Option[String] = {
    try {
      val pdf = PDDocument.load(new File(filename))
      val stripper = new PDFTextStripper
      stripper.setStartPage(startPage)
      stripper.setEndPage(endPage)
      Some(stripper.getText(pdf))
    } catch {
      case t: Throwable =>
        t.printStackTrace
        None
    }
  }

}

object Main extends App{
  private val pdfAbsolutePath: String = "..."
  println(PdfToText.getTextFromPdf(0, 5, pdfAbsolutePath))
}
