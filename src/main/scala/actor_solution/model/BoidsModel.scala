package actor_solution.model

import actor_solution.model.Boid.Boid
import actor_solution.model.P2d.P2d
import actor_solution.model.V2d.V2d
import actor_solution.utils.SimulationParameters.{environmentWidth, environmentHeight, boidNumber, boidMaxSpeed}

import scala.util.Random

case class BoidsModel(
    separationWeight: Double,
    alignmentWeight: Double,
    cohesionWeight: Double
):
  def minX: Double = -environmentWidth / 2
  def maxX: Double = environmentWidth / 2
  def minY: Double = -environmentHeight / 2
  def maxY: Double = environmentHeight / 2

object BoidsModel:

  def randomBoids(): List[Boid] =
    val rand = new Random()
    List.fill(boidNumber) {
      val pos = P2d(
        -environmentWidth / 2 + rand.nextDouble() * environmentWidth,
        -environmentHeight / 2 + rand.nextDouble() * environmentHeight
      )
      val vel = V2d(
        rand.nextDouble() * boidMaxSpeed / 2 - boidMaxSpeed / 4,
        rand.nextDouble() * boidMaxSpeed / 2 - boidMaxSpeed / 4
      )
      Boid(pos, vel)
    }

  def partition[A](list: List[A], n: Int): List[List[A]] = {
    val size = list.size
    val base = math.floor(size / n).toInt
    val extra = size % n

    (0 until n).toList.map { i =>
      val start = i * base + math.min(i, extra)
      val end = start + base + (if (i < extra) 1 else 0)
      list.slice(start, end)
    }
  }
