package ohAnalysis

import com.github.nscala_time.time.Imports._

object FunnyAnalysis {

  val wordsOfHumor = List("lol", "haha", "lmao", "lmfao", "hehe", "rofl", "omg", "oh my god", "no way", "fucking hell")
  val sizeOfAPassage = 200

  def checkForWordsInSentence(wordsToCheckFor: List[String], sentance: String): Boolean = wordsToCheckFor match {
    case Nil => false
    case x :: xs => if (sentance.toLowerCase.contains(x.toLowerCase)) true else checkForWordsInSentence(xs, sentance)
  }

  def messagesToPassage(messages: Vector[Message], passageLength: Int): Vector[Passage] = {

    val splitMessages = messages.sliding(passageLength, passageLength).toVector

    def findLaughterRate(input: Vector[Message]): Int = {

      def onlyLaughterMessages = input.filter(x => checkForWordsInSentence(wordsOfHumor, x.message))
      onlyLaughterMessages.size
    }

    splitMessages.map(messages => new Passage(messages, findLaughterRate(messages)))
  }

  def sortPassagesByHumor(collectionOfPassages: Vector[Passage]): Vector[Passage] = {
    collectionOfPassages.sortBy(- _.humor)
  }

  def findPassageByKeyword(input: Vector[Message], keyword: String ): Vector[Message] = {

    def indexOfKeywords(input: Vector[Message], keyword: String): Vector[Int] = {
      for (x <- input
           if checkForWordsInSentence(List(keyword),x.message)) yield input.indexOf(x)
    }

    for (index <- indexOfKeywords(input,keyword.toLowerCase);
         y <- (index - sizeOfAPassage / 2) until index + sizeOfAPassage / 2)
      yield input(y)
  }

  def findUrgency(passage: Passage): Double = {
    val messagePairs = passage.messages.sliding(2, 1)
    val timeBetweenMessages = messagePairs.map(x =>  x(1).When.getMillis -  x(0).When.getMillis)

    println(timeBetweenMessages.sum / passage.length)
    timeBetweenMessages.sum / passage.length
  }

  def findPassageByDate() = {???}
}
