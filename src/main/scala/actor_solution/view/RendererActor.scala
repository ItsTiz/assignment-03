package actor_solution.view

import actor_solution.model.Boid.Boid
import actor_solution.utils.SimulationParameters.{environmentWidth, boidWidth, boidHeight}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.swing.MainFrame

object RendererActor:

  sealed trait RenderCommand
  final case class UpdateGUI(boids: List[Boid]) extends RenderCommand

  def apply(mainFrame: MainFrame, panel: BoidsPanel): Behavior[RenderCommand] =
  Behaviors.setup { context =>

    val w = mainFrame.peer.getWidth
    val h = mainFrame.peer.getHeight
    val xScale: Double = (800.0 / 1000.0).toDouble

    Behaviors.receiveMessage {
        case UpdateGUI(boids) =>
          panel.updateBoids(
            boids.map(b =>
              DrawableBoid(
                (w / 2 + b.pos.x * xScale).toInt,
                (h / 2 - b.pos.y * xScale).toInt,
                boidWidth,
                boidHeight))
          )
          Behaviors.same
      }
    }



