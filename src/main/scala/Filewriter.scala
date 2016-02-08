package ohAnalysis

import Interface.takeInput
import FunnyAnalysis.findUrgency
import DataIngestor.allMessages

object File extends App {

  takeInput
}

//  val file = Source.fromFile("JustOh.txt").getLines.mkString
//
//  val createdFile = new PrintWriter(new File("JustOH2.txt"))
//
//  val newfile = file.split("START12345")
//  val newfile2 = newfile(1).split("ENDHERE123")(0)
//
//  createdFile.write(newfile2)
//  createdFile.close
