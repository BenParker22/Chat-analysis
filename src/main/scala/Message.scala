package ohAnalysis

//import java.util.Date
import com.github.nscala_time.time.Imports._

class Message(val who: String, val When: DateTime, val message: String) extends Object {

  override def toString: String = who + ':' + message

  val words = message.toLowerCase.split(' ')

  def printWho = println(who)
  def printWhen = println(When)
  def printMessage = println(message)
}



