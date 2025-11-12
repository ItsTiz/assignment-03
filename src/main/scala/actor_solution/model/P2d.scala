package actor_solution.model

import actor_solution.model.V2d.V2d

object P2d:

  case class P2d(x: Double, y: Double):
    override def toString: String = s"P2d($x, $y)"

  extension (point: P2d)

    def +(vector: V2d): P2d =
      P2d(point.x + vector.x, point.y + vector.y)

    def +(point2: P2d): P2d =
      P2d(point.x + point2.x, point.y + point2.y)

    def -(point2: P2d): V2d =
      V2d(point.x - point2.x, point.y - point2.y)

    def /(scalar: Double): P2d =
      P2d(point.x / scalar, point.y / scalar)

    def distance(point2: P2d): Double =
      val dx = point2.x - point.x
      val dy = point2.y - point.y
      Math.sqrt(dx * dx + dy * dy)

end P2d
