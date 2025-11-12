package actor_solution.model

import actor_solution.model.Boid.Boid
import actor_solution.model.P2d.P2d
import actor_solution.model.V2d.V2d

import scala.util.Random

case class BoidsModel(
    boids: List[Boid],
    separationWeight: Double,
    alignmentWeight: Double,
    cohesionWeight: Double,
    width: Double,
    height: Double,
    maxSpeed: Double,
    perceptionRadius: Double,
    avoidRadius: Double
):
  def minX: Double = -width / 2
  def maxX: Double = width / 2
  def minY: Double = -height / 2
  def maxY: Double = height / 2

object BoidsModel:

  /** Creates a new BoidsModel with an initial, randomized list of boids. */
  def apply(
      nboids: Int,
      initialSeparationWeight: Double,
      initialAlignmentWeight: Double,
      initialCohesionWeight: Double,
      width: Double,
      height: Double,
      maxSpeed: Double,
      perceptionRadius: Double,
      avoidRadius: Double
  ): BoidsModel =

    val rand = new Random()
    
    val initialBoids = List.fill(nboids) {
      val pos = P2d(
        -width / 2 + rand.nextDouble() * width,
        -height / 2 + rand.nextDouble() * height
      )
      val vel = V2d(
        rand.nextDouble() * maxSpeed / 2 - maxSpeed / 4,
        rand.nextDouble() * maxSpeed / 2 - maxSpeed / 4
      )
      Boid(pos, vel)
    }
    
    BoidsModel(
      boids = initialBoids,
      separationWeight = initialSeparationWeight,
      alignmentWeight = initialAlignmentWeight,
      cohesionWeight = initialCohesionWeight,
      width = width,
      height = height,
      maxSpeed = maxSpeed,
      perceptionRadius = perceptionRadius,
      avoidRadius = avoidRadius
    )
