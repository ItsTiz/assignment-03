package actor_solution.view

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object RendererActor:

  sealed trait RenderCommand

  def apply(): Behavior[RenderCommand] =
    Behaviors.setup {
      context => Behaviors.ignore
    }



