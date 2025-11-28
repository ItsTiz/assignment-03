package actor_solution.view

import actor_solution.model.Boid.Boid
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object RendererActor:

  sealed trait RenderCommand
  final case class UpdateGUI(boids: List[Boid]) extends RenderCommand

  def apply(): Behavior[RenderCommand] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        case UpdateGUI(boids) =>
          //boids
          Behaviors.same
      }
    }



