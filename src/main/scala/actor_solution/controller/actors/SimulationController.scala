package actor_solution.controller.actors

import actor_solution.model.Boid.Boid
import actor_solution.utils.SimulationParameters
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object SimulationController:
  sealed trait SimulationSyncCommand
  case object Tick extends SimulationSyncCommand
  final case class UpdateAck(stepId: Long, boidState: Boid) extends SimulationSyncCommand

  def apply(): Behavior[SimulationSyncCommand] = {
    Behaviors.withTimers { timers =>
      
      timers.startTimerWithFixedDelay(
        key = Tick,
        msg = Tick,
        delay = FiniteDuration(SimulationParameters.updateInterval, TimeUnit.MILLISECONDS))
      
      Behaviors.receiveMessage { _ =>
        Behaviors.same
      }
    }

    Behaviors.receiveMessage {
      case UpdateAck(stepId, boidState) =>

        Behaviors.same
    }
  }

end SimulationController

