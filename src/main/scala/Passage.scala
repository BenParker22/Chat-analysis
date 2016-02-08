package ohAnalysis

class Passage(val messages: Vector[Message], val humor: Int) extends Object {

  val length: Double = messages.size
  def print = messages.foreach(println)
  //override def toString = {for (message <- messages.map(x => x.message))}
}
