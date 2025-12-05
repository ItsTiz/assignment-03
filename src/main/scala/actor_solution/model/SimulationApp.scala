package actor_solution.model

import actor_solution.controller.actors.SimulationController
import actor_solution.view.{BoidsPanel, RendererActor}
import akka.NotUsed
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{Behavior, Terminated}

import scala.swing.{MainFrame, Panel}

object SimulationApp:
  def apply(panel: BoidsPanel, mainView: MainFrame): Behavior[NotUsed] =
    Behaviors.setup { context =>
      val renderer = context.spawn(RendererActor(mainView, panel), "Renderer")
      val controller = context.spawn(SimulationController(renderer), "SimulationController")
      //context.watch(renderer)

      Behaviors.receiveSignal {
        case (_, Terminated(_)) =>
          Behaviors.stopped
      }
    }

