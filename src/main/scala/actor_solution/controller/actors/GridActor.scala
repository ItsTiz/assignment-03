package actor_solution.controller.actors

import actor_solution.model.Boid.Boid
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object GridActor:

  sealed trait NeighboursRequest

  def apply(boids: List[Boid]): Behavior[NeighboursRequest] =
    Behaviors.setup { _ =>
      Behaviors.receiveMessage { _ =>
        // TODO: implement neighbours queries
        Behaviors.same
      }
    }