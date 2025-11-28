package actor_solution.controller.actors

import actor_solution.controller.actors.GridActor.NeighboursRequest
import actor_solution.model.Boid.Boid
import actor_solution.model.BoidsModel
import actor_solution.utils.SimulationParameters
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object BoidWorker:

  sealed trait BoidWorkerMessage
  final case class UpdateState(
    model: BoidsModel,
    aggregator: ActorRef[UpdatedBoids],
    grid: ActorRef[NeighboursRequest]
  ) extends BoidWorkerMessage
  final case class UpdatedBoids(boids: List[Boid]) extends BoidWorkerMessage
  final case class Neighbours(boidsMap: Map[Boid, Seq[Boid]]) extends BoidWorkerMessage

  def apply(boids: List[Boid]): Behavior[BoidWorkerMessage] =
    Behaviors.setup { context =>

      def idle(): Behavior[BoidWorkerMessage] =
        Behaviors.receiveMessage {
          case UpdateState(model, aggregator, grid) =>
            grid ! NeighboursRequest(boids, SimulationParameters.boidAvoidRadius, context.self)
            waitingNeighbours(model, aggregator)
          case UpdatedBoids(_) => Behaviors.ignore
          case Neighbours(_) => Behaviors.ignore
        }

      def waitingNeighbours(model: BoidsModel, aggregator: ActorRef[UpdatedBoids]): Behavior[BoidWorkerMessage] =
        Behaviors.receiveMessage {
          case Neighbours(boidsMap) =>
            val updatedBoids = boidsMap.map { (boid, neighbours) =>
              boid.withUpdatedVel(model, neighbours.toList).withUpdatedPos(model)
            }.toList
            aggregator ! UpdatedBoids(updatedBoids)
            BoidWorker(updatedBoids)
          case _ => Behaviors.ignore
        }

      idle();

    }

end BoidWorker


