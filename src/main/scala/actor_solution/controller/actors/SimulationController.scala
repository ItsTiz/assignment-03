package actor_solution.controller.actors

import actor_solution.controller.actors.GridActor.UpdateGrid
import actor_solution.controller.actors.WorkerPoolActor.Update
import actor_solution.model.Boid.Boid
import actor_solution.model.BoidsModel
import actor_solution.utils.SimulationParameters.{
  alignmentWeight,
  boidPerceptionRadius,
  cohesionWeight,
  separationWeight,
  updateInterval
}
import actor_solution.view.RendererActor.{RenderCommand, UpdateGUI}
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.duration.DurationLong
import scala.swing.ListView.Renderer

object SimulationController:
  sealed trait SimulationSyncCommand
  private case object Tick extends SimulationSyncCommand
  final case class UpdateAck(updatedBoids: List[Boid]) extends SimulationSyncCommand

  private case object TimerKey

  def apply(renderer: ActorRef[RenderCommand]): Behavior[SimulationSyncCommand] =
    Behaviors.setup { context =>
      val boids = BoidsModel.randomBoids()
      val model = BoidsModel(separationWeight, alignmentWeight, cohesionWeight)
      val gridActor = context.spawn(GridActor(boidPerceptionRadius), "GridActor")
      val workerPoolActor = context.spawn(WorkerPoolActor(boids, model, gridActor), "WorkerPoolActor")

      Behaviors.withTimers { timers =>
        timers.startTimerAtFixedRate(TimerKey, Tick, updateInterval.millis)

        def waitingForFrameTick(
                                 renderer: ActorRef[RenderCommand],
                                 pool: ActorRef[WorkerPoolActor.Update],
                                 grid: ActorRef[GridActor.UpdateGrid],
                                 boids: List[Boid]
                               ): Behavior[SimulationSyncCommand] =
          Behaviors.receiveMessage {
            case Tick =>
              grid ! UpdateGrid(boids)
              pool ! Update(context.self)
              waitingForAck
            case _ =>
              Behaviors.same
          }

        def waitingForAck: Behavior[SimulationSyncCommand] =
          Behaviors.receiveMessage {
            case UpdateAck(updated) =>
              renderer ! UpdateGUI(updated)
              waitingForFrameTick(renderer, workerPoolActor, gridActor, updated)
            case _ =>
              Behaviors.same
          }

        // ---- the behavior returned by withTimers ----
        waitingForFrameTick(renderer, workerPoolActor, gridActor, boids)
      }
    }


end SimulationController
