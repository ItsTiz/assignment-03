package actor_solution.controller.actors

import actor_solution.controller.actors.GridActor.NeighboursRequest
import actor_solution.model.Boid.Boid
import actor_solution.model.BoidsModel
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

  def apply(boids: List[Boid]): Behavior[BoidWorkerMessage] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        case UpdateState(model, aggregator, grid) =>
          // TODO: compute next state for each boid using model and neighbours via grid
          aggregator ! UpdatedBoids(boids)
          Behaviors.same
        case _ => Behaviors.same
      }
    }

end BoidWorker


