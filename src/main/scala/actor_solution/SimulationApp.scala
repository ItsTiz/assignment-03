package actor_solution

import actor_solution.controller.actors.SimulationController
import actor_solution.view.RendererActor
import akka.NotUsed
import akka.actor.typed.{Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors

object SimulationApp:
  def apply():Behavior[NotUsed] =
    Behaviors.setup { context =>
      val controller = context.spawn(SimulationController(), "SimulationController")
      val renderer = context.spawn(RendererActor(), "Renderer")
      context.watch(renderer)

      Behaviors.receiveSignal {
        case (_, Terminated(_)) =>
          Behaviors.stopped
      }
    }

