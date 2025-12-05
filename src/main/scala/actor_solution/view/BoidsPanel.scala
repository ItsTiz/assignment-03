package actor_solution.view

import java.awt.{Color, Graphics2D}
import scala.swing.event.MousePressed
import scala.swing.{Panel, Swing}

class BoidsPanel(val width: Int, val height: Int) extends Panel with DrawablePanel[Graphics2D] {
  private var boids: Seq[Drawable[Graphics2D]] = Nil

  //listenTo(mouse.clicks)
  peer.setSize(width, height)

  def updateBoids(newBoids: Seq[Drawable[Graphics2D]]): Unit =
    boids = newBoids
    Swing.onEDT(repaint())

//  override def whenClicked(listener: (Int, Int) => Unit): Unit =
//    reactions += { case MousePressed(_, point, _, _, _) =>
//      listener(point.x, point.y)
//    }

  override def paintComponent(g: Graphics2D): Unit =
    super.paintComponent(g)
    boids.foreach(_.draw(g))
    g.setColor(Color.BLACK)
    g.drawString("Num. Boids: " + boids.size, 10, 25)
}
