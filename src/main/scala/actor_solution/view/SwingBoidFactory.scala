package actor_solution.view

import java.awt.Color
import scala.swing.Graphics2D

class SwingBoidFactory extends ElementFactory[Graphics2D]:
  override def createBoid(x: Int, y: Int, w: Int, h: Int): Drawable[Graphics2D] =
    DrawableBoid(x, y, w, h, Color.BLUE)
