package actor_solution.model

object V2d:

  case class V2d(x: Double, y: Double):
    override def toString: String = s"V2d($x, $y)"

  extension (vector: V2d)

    def +(other: V2d): V2d =
      V2d(vector.x + other.x, vector.y + other.y)

    def -(other: V2d): V2d =
      V2d(vector.x - other.x, vector.y - other.y)

    def *(scalar: Double): V2d =
      V2d(vector.x * scalar, vector.y * scalar)

    def /(scalar: Double): V2d =
      V2d(vector.x / scalar, vector.y / scalar)

    def abs: Double =
      Math.sqrt(vector.x * vector.x + vector.y * vector.y)

    def getNormalised: V2d =
      V2d(vector.x / vector.abs, vector.y / vector.abs)

end V2d
