
import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame
import scala.swing.TextArea
import scala.swing.Button
import scala.swing.Action
import scala.swing.BoxPanel
import scala.swing.Orientation
import obj._
import scala.swing.Panel
import java.awt.Dimension
import scala.swing.Component
import java.awt.Graphics2D
import java.awt.Color
import java.awt.Shape
import java.awt.Rectangle
import scala.collection.immutable.Vector
import java.awt.Polygon
import scala.swing.Label
import scala.swing.event.ValueChanged
import scala.swing.Swing
import java.awt.BasicStroke

object Main {
  def main(args: Array[String]) {
    GUI.main(args)
    val t = Token(TokenFace(2,AXE),TokenFace(NoIcon))
    //println(t)
    println(Icon.list)
  }

  object GUI extends SimpleSwingApplication {
    var x,y = 50
    val rotator = new scala.swing.Slider() {
      min = -180
      max = 180
      value = 0
    }
    val lbl = new Label() {
        text = rotator.value.toString()
        listenTo(rotator)
        reactions += { 
          case e: ValueChanged => text = rotator.value.toString()
        }
      }
    val drawArea = new BoxPanel(Orientation.Vertical){
      border = Swing.EmptyBorder(10)
      preferredSize = new Dimension(500,500)
      
      contents += rotator
      contents += lbl
      contents += new Component {
        listenTo(rotator)
        
        val width = 100d
        var pos = Array(200,200,200)
        //val s1 = new Rectangle(x,y,width,width)
        //val s2 = new Rectangle(x,y,width,width)
        //val s3 = new Rectangle(x,y,width,width)
        //val s4 = new Rectangle(x,y,width,width)
        //val s5 = new Rectangle(x,y,width,width)
        //val s6 = new Rectangle(x,y,width,width)
        
       // val v4 = Array[Int](200,100,5)
       //   val v1 = Array[Int](200,0,0)
       //   val v2 = Array[Int](300,200,0)
       //   val v3 = Array[Int](100,200,0)
          
        val height = (math.sqrt(6d)/3d) * width
        
        reactions += {
          case e: ValueChanged => repaint
        }
        override def paintComponent(g: Graphics2D) {
          g.translate(pos(0), pos(1))
          super.paintComponent(g)
          g.setStroke(new BasicStroke(3))
          
          val v1 = Array[Int](0,0-height.toInt,pos(2))
          val v2 = Array[Int](0+width.toInt/2-rotator.value,0,pos(2))
          val v3 = Array[Int](0-width.toInt/2+rotator.value,0,pos(2))
          val v4 = Array[Int](0+rotator.value,0,pos(2))
          
          g.setColor(Color.BLUE)
          g.fillPolygon(new Polygon(Array(v1(0),v2(0),v3(0)), Array(v1(1),v2(1),v3(1)), 3))
          
          g.setColor(Color.GREEN)
          g.fillPolygon(new Polygon(Array(v1(0),v2(0),v4(0)), Array(v1(1),v2(1),v4(1)), 3))
          
          g.setColor(Color.RED)
          g.fillPolygon(new Polygon(Array(v4(0),v2(0),v3(0)), Array(v4(1),v2(1),v3(1)), 3))
          
          g.setColor(Color.YELLOW)
          g.fillPolygon(new Polygon(Array(v1(0),v4(0),v3(0)), Array(v1(1),v4(1),v3(1)), 3))
        }
      }
      
    }
    val tarea = new TextArea() {
      editable = false
      columns = 100
      rows = 5
    }
    val bstate = new Button {
      action = new Action("tog") { def apply() { selected = !selected } }
    }
    
    val console = new BoxPanel(Orientation.Vertical) {
      contents += tarea
      contents += bstate
    }
    
    def top = new MainFrame {
      title = "test"
      contents = drawArea
    }
  }
}
