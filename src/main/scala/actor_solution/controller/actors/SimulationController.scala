package actor_solution.controller.actors

import actor_solution.controller.actors.WorkerPoolActor.Update
import actor_solution.model.Boid.Boid
import actor_solution.model.BoidsModel
import actor_solution.utils.SimulationParameters.{alignmentWeight, cohesionWeight, separationWeight, updateInterval}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.duration.DurationLong

object SimulationController:
  sealed trait SimulationSyncCommand
  private case object Tick extends SimulationSyncCommand
  final case class UpdateAck(updatedBoids: List[Boid]) extends SimulationSyncCommand

  private case object TimerKey

  def apply(): Behavior[SimulationSyncCommand] =
    Behaviors.setup { context =>
      val boids = BoidsModel.randomBoids()
      val boidsModel = BoidsModel(separationWeight, alignmentWeight, cohesionWeight)
      val gridActor = context.spawn(GridActor(boids), "GridActor")
      val workerPoolActor = context.spawn(WorkerPoolActor(boids, boidsModel, gridActor), "WorkerPoolActor")

      Behaviors.withTimers { timers =>

        timers.startTimerWithFixedDelay(
          key = TimerKey,
          msg = Tick,
          delay = updateInterval.millis
        )

        Behaviors.receiveMessage[SimulationSyncCommand] {
          case Tick =>
            workerPoolActor ! Update(context.self)
            Behaviors.same
          case UpdateAck(updated) =>
            // TODO: forward to renderer or update state as needed
            Behaviors.same
        }
      }
    }

end SimulationController
