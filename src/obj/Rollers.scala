package obj

import scala.util.Random
import scala.collection.mutable.ListBuffer

object init {

}

trait Roller[T] {
  def values: List[T]
  var lastRoll: T = _
  protected def roll(): T = {
    lastRoll = values(Random.nextInt(values.length))
    lastRoll
  }
}

case class Dice(sides: Int) extends Roller[Int] {
  require( sides >= 2, "Dice arg should to be >= 2" )
  def values = List.range(1, sides+1)
  override def roll = super.roll
}

case class Coin extends Roller[String] {
  def values = List("Heads","Tails")
  def flip = roll
}

case class Token(sideA: TokenFace, sideB: TokenFace) extends Roller[TokenFace] {
  def values = List(sideA, sideB)
  def cast = roll
}

object TokenFace {
  def apply(i: Icon) = new TokenFace(1,i)
  def apply(n: Int) = new TokenFace(n,NoIcon)
  def apply() = new TokenFace(0,NoIcon)
}
case class TokenFace(val value: Int, val icon: Icon) {}

object Icon { 
  val list = ListBuffer[Icon]()
  def apply(s: String) = list.find(_.name == s).getOrElse(NoIcon)
}
trait Icon { 
  Icon.list += this
  def name: String = toString
  implicit def toString(i: Icon): String = name
}
case object NoIcon extends Icon {  }
case object AXE extends Icon {  }




