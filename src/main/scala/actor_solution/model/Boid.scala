package actor_solution.model

import actor_solution.model.P2d.P2d
import actor_solution.model.V2d.V2d

object Boid:

  case class Boid(pos: P2d, vel: V2d)

  object Boid:

    def calculateAlignment(boid: Boid, nearbyBoids: List[Boid]): V2d =
      val neighboursSize = nearbyBoids.size
      neighboursSize match
        case n if n > 0 =>
          val totalVelocity = nearbyBoids.foldLeft(V2d(0, 0))((accumulator, other) => accumulator + other.vel)
          ((totalVelocity / neighboursSize) - boid.vel).getNormalised
        case _ => V2d(0, 0)
    def calculateCohesion(boid: Boid, nearbyBoids: List[Boid]): V2d =
      val neighboursSize = nearbyBoids.size
      neighboursSize match
        case n if n > 0 =>
          val totalPosition = nearbyBoids.foldLeft(P2d(0, 0)) { (accumulator, other) =>
            accumulator + other.pos
          }
          val centerOfMass = totalPosition / n
          val steeringVector = centerOfMass - boid.pos
          steeringVector.getNormalised
        case _ =>
          V2d(0, 0)
    def calculateSeparation(boid: Boid, nearbyBoids: List[Boid], avoidRadius: Double): V2d =
      val tooCloseBoids = nearbyBoids.filter { other =>
        boid.pos.distance(other.pos) < avoidRadius
      }
      val count = tooCloseBoids.size
      count match
        case n if n > 0 =>
          val totalSeparationVector = tooCloseBoids.foldLeft(V2d(0, 0)) { (accumulator, other) =>
            val differenceVector = boid.pos - other.pos
            accumulator + differenceVector
          }
          val avgSeparationVector = totalSeparationVector / n
          avgSeparationVector.getNormalised
        case _ =>
          V2d(0, 0)

  extension (boid: Boid)
    def getNearbyBoids(model: BoidsModel): List[Boid] =
      model.boids.filter { other =>
        val isNotSelf = other != boid
        val isNearby = boid.pos.distance(other.pos) < model.perceptionRadius
        isNotSelf && isNearby
      }

    def withUpdatedVelocity(model: BoidsModel): Boid =
      val nearbyBoids = boid.getNearbyBoids(model)

      val separation = Boid.calculateSeparation(boid, nearbyBoids, model.avoidRadius)
      val alignment = Boid.calculateAlignment(boid, nearbyBoids)
      val cohesion = Boid.calculateCohesion(boid, nearbyBoids)

      val weightedVel: V2d =
        boid.vel +
          (separation * model.separationWeight) +
          (alignment * model.alignmentWeight) +
          (cohesion * model.cohesionWeight)

      val finalVel = weightedVel.abs match
        case s if s > model.maxSpeed =>
          weightedVel.getNormalised * model.maxSpeed
        case _ => weightedVel

      boid.copy(vel = finalVel)

    def updatePos(model: BoidsModel): Boid =
      var newPos = boid.pos + boid.vel

      if (newPos.x < model.minX) newPos = newPos + V2d(model.width, 0)
      if (newPos.x >= model.maxX) newPos = newPos + V2d(-model.width, 0)
      if (newPos.y < model.minY) newPos = newPos + V2d(0, model.height)
      if (newPos.y >= model.maxY) newPos = newPos + V2d(0, -model.height)

      boid.copy(pos = newPos)

end Boid
