package actor_solution.view

import java.awt.{Color, Graphics2D}

// Implementation of a Drawable Rectangle
case class DrawableBoid(x: Int, y: Int, width: Int, height: Int, color: Color = Color.BLUE) extends Drawable[Graphics2D]:
  override def draw(graphic: Graphics2D): Unit =
    graphic.setColor(color)
    graphic.fillRect(x, y, width, height)
