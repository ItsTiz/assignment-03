package actor_solution.view

trait Drawable[G]:
  def draw(graphic: G): Unit

trait ElementFactory[G]:
  def createBoid(x: Int, y: Int, width: Int, height: Int): Drawable[G]

trait DrawablePanel[G]:
  def updateBoids(newBoids: Seq[Drawable[G]]): Unit
  //def whenClicked(listener: (Int, Int) => Unit): Unit
