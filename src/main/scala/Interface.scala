package ohAnalysis

import DataIngestor._
import FunnyAnalysis._
import WordAnalysis._

import scala.io.StdIn

object Interface {

  def takeInput: Unit =

    readIntWithInstruction("\n Main Menu\nEnter 0 for passage by keyword\nEnter 1 for statistics") match {
    case 0 => passagesByKeywordInterface
    case 1 => statisticsInterface()
    case _ => printAndMainMenu("Unknown input please try again")
  }

  def statisticsInterface(printSomething: Unit): Unit = {
    readIntWithInstruction("\nStatistics Menu\nEnter 0 for total characters sent per person as a percentage\nEnter 1 for total messages sent per person\nEnter -1 for main menu ") match {
      case -1 => takeInput
      case 0 => statisticsInterface(printTotalCharactersBySenderPercentage)
      case 1 => statisticsInterface(printMessagesBySenderTotal)
    }
  }

  def passagesByKeywordInterface: Unit = {

    val keyword = StdIn.readLine("Enter keyword: \n")
    val sortedPassages = sortPassagesByHumor(messagesToPassage(findPassageByKeyword(allMessages, keyword), readIntWithInstruction("Enter passage length: ")))

    def foundPassages(printSomething: Unit): Unit = {

      println("\nFound: " + sortedPassages.size + " Passages containing keyword: " + keyword)

      readIntWithInstruction("Enter a number to read that passage starting from '0', sorted by humor \nEnter '-1' for main menu\n Enter -2 to search by a new keyword ") match {
        case -2 => passagesByKeywordInterface
        case -1 => takeInput
        case x: Int => foundPassages(sortedPassages(x).print)
        case _ => foundPassages(println("unknown input, please try again"))
      }
    }
    foundPassages()
  }

  def readIntWithInstruction(instruction: String): Int = {
    println(instruction)
    StdIn.readInt()
  }

  def printAndMainMenu(input: String) = {
    println(input)
    takeInput
  }

}
