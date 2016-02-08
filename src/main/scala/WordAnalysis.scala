package ohAnalysis

import ohAnalysis.DataIngestor._
import ohAnalysis.Message

object WordAnalysis {

  val names = Seq("Paul", "Lee", "Ben", "Beni", "Ashley", "John")
  val girls = Seq("mal", "leigh", "saffy", "sita", "kat", "amey")

  def printMessagesBySenderTotal = {
    val mapSenderToMessages = allMessages.groupBy(x => x.who)
    for ((sender, messages) <- mapSenderToMessages) println (sender + " sent " + messages.size + " messages")
  }

  def filterByName(name: String, Messages: Vector[Message]): Vector[Message] = {
    Messages.filter(x => x.who == name)
  }

  val messagesBySender = {for (name <- names) yield filterByName(name, allMessages)}

  def printTotalCharactersBySenderPercentage = {
    def findTotalCharactersInPassage(Messages: Vector[Message]): Double = {
      val messageLengths = Messages.map(x => x.message.length)
      messageLengths.sum
    }

    println("Total Character by Sender as a Percentage")
    for (x <- messagesBySender) yield println(x(0).who + ": " + (findTotalCharactersInPassage(x) * 100 / findTotalCharactersInPassage(allMessages)) + "%")
  }

  def sortWordsByOccurence(messages: Vector[Message]): Vector[(String, Int)] = {

    val allWords = {for (x <- allMessages) yield x.words}.flatten
    val wordsToOccurence = allWords.groupBy(identity).mapValues(_.size)

    (wordsToOccurence.toVector sortBy {_._2}).reverse
  }

}
