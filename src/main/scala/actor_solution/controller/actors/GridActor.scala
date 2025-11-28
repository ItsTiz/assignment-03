package actor_solution.controller.actors

import actor_solution.controller.actors.BoidWorker.Neighbours
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import actor_solution.model.Boid.Boid
import actor_solution.model.{GridCell, P2d}

import scala.collection.mutable

object GridActor:

  sealed trait GridCommand
  final case class UpdateGrid(boids: List[Boid] /*, replyTo: ActorRef[GridUpdated]*/ ) extends GridCommand
  // final case object GridUpdatedAck
  final case class NeighboursRequest(boids: Seq[Boid], radius: Double, replyTo: ActorRef[Neighbours])
      extends GridCommand

  def apply(cellSize: Double): Behavior[GridCommand] = {
    var cells = mutable.Map.empty[GridCell, Seq[Boid]]

    Behaviors.receiveMessage {
      case UpdateGrid(newBoids) =>
        val newCells = mutable.Map.empty[GridCell, Seq[Boid]]

        newBoids.foreach { boid =>
          newCells.updateWith(boid.pos.toGridCell(cellSize)) {
            case Some(vec) => Some(vec :+ boid)
            case None => Some(Vector(boid))
          }
        }

        cells = newCells
        // replyTo ! GridUpdated
        Behaviors.same

      case NeighboursRequest(boids, radius, replyTo) =>
        def singleBoidSearch(boid: Boid): Seq[Boid] =
          val centerCell = boid.pos.toGridCell(cellSize)
          val cellRadius = Math.ceil(radius / cellSize).toInt

          val neighborCells = for {
            dx <- -cellRadius to cellRadius
            dy <- -cellRadius to cellRadius
          } yield GridCell(centerCell.x + dx, centerCell.y + dy)

          neighborCells
            .flatMap(cells.get)
            .flatten
            .filter(_ ne boid)
            .filter(other => boid.pos.distance(other.pos) < radius)

        val nearbyMap = boids.map(boid => boid -> singleBoidSearch(boid)).toMap

        replyTo ! Neighbours(nearbyMap)
        Behaviors.same
    }
  }
