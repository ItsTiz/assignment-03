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

  /** Creates a new BoidsModel with an initial, randomized list of boids. */
  def apply(
      initialSeparationWeight: Double,
      initialAlignmentWeight: Double,
      initialCohesionWeight: Double
  ): BoidsModel =
    BoidsModel(initialSeparationWeight, initialAlignmentWeight, initialCohesionWeight)

    /*
    val rand = new Random()
    val itialBoids = List.fill(boidNumber) {
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
    */
