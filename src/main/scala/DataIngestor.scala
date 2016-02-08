package ohAnalysis

//import java.util.Date
import com.github.nscala_time.time.Imports._

import scala.io.Source._

object DataIngestor {

  val ohString = fromFile("JustOh2.txt").getLines.mkString
  val rawTextArray =  ohString.split("</P>")

  val allMessages = messageAccumilator(0,0,List()).sortBy(_.When).toVector

  def messageAccumilator(numberOfEmptyMessages: Int, n: Int, acc: List[Message]): List[Message] = {

    if (n + numberOfEmptyMessages >= ((rawTextArray.length - 3) /2)) acc
    else if (checkForEmptyMessage(rawTextArray(2*n + numberOfEmptyMessages), rawTextArray((2 * n) + 1 + numberOfEmptyMessages))) {
      messageAccumilator(numberOfEmptyMessages + 1, n, acc)
    }
    else
      messageAccumilator(numberOfEmptyMessages, n+1,combinePair(rawTextArray(2*n + numberOfEmptyMessages),rawTextArray(2*n+ numberOfEmptyMessages + 1 )) :: acc )
  }

  def checkForEmptyMessage(input1: String, input2: String): Boolean = {
    val names = Seq("<P>Paul Truman", "<P>Lee Roberts", "<P>Ben Parker", "<P>Beni Bienz", "<P>Ashley Salisbury", "<P>John Gunn")

    names.exists(input2.startsWith(_))
  }

  def combinePair(whoAndWhen: String, message: String): Message = {

    //println(whoAndWhen + " " + message)
    // Returns the first name of message sender
    def whoFrom(Input:String): String = {
      val indexOfSpace = Input.indexOf(' ')
      Input.substring(3, indexOfSpace)
    }

    def whenFrom(Input:String): DateTime = {

      val indexOfComma = Input.indexOf(',')

      val format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm")

      val unFormattedDate = Input.substring(indexOfComma + 2) // dd Month YYYY at hh:mm
      val splitDate = unFormattedDate.split(" ")

      def monthWordToNumber(monthWord: String): String = monthWord match {
        case "January" => "01"
        case "February" => "02"
        case "March" => "03"
        case "April" => "04"
        case "May" => "05"
        case "June" => "06"
        case "July" => "07"
        case "August" => "08"
        case "September" => "09"
        case "October" => "10"
        case "November" => "11"
        case "December" => "12"
        case _ => println(monthWord)
          "Error"
      }

      val day = splitDate(0)
      val month = monthWordToNumber(splitDate(1))
      val year = splitDate(2)
      val time = splitDate(4)


      new DateTime(format.parse(s"$year-$month-$day $time"))
    }

    new Message(whoFrom(whoAndWhen), whenFrom(whoAndWhen), message.substring(3))
  }
}
