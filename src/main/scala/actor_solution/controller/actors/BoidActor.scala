package actor_solution.controller.actors

import actor_solution.model.Boid.Boid
import actor_solution.model.BoidsModel
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object BoidActor:
  sealed trait BoidMessage
  final case class UpdateState(model: BoidsModel) extends BoidMessage
  final case class GetBoid(handle: ActorRef[Boid]) extends BoidMessage

  def apply(boid: Boid): Behavior[BoidMessage] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        case UpdateState(model) =>
          BoidActor(boid.withUpdatedVel(model).withUpdatedPos(model))
      }
    }

end BoidActor


