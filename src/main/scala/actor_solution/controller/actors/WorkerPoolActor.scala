package actor_solution.controller.actors

import actor_solution.model.Boid.Boid
import actor_solution.model.BoidsModel
import actor_solution.utils.SimulationParameters
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import SimulationParameters.workerActors
import actor_solution.controller.actors.BoidWorker.UpdateState
import actor_solution.controller.actors.GridActor.{GridCommand, NeighboursRequest, UpdateGrid}
import actor_solution.controller.actors.SimulationController.UpdateAck

import scala.concurrent.duration.DurationInt

object WorkerPoolActor:

  sealed trait WorkerPoolCommand
  final case class Update(replyTo: ActorRef[UpdateAck]) extends WorkerPoolCommand
  final private case class AggregatedBoids(updatedBoids: List[Boid]) extends WorkerPoolCommand

  def apply(
      boids: List[Boid],
      boidsModel: BoidsModel,
      gridActor: ActorRef[GridCommand]
  ): Behavior[WorkerPoolCommand] =
    Behaviors.setup { context =>
      val boidPartitions = BoidsModel.partition(boids, workerActors)
      val workers = boidPartitions.zipWithIndex.map { case (partition, idx) =>
        context.spawn(BoidWorker(partition), s"BoidWorker-${idx + 1}")
      }

      def idle(): Behavior[WorkerPoolCommand] =
        Behaviors.receiveMessage {
          case Update(replyTo) =>
            context.spawnAnonymous(
              Aggregator[Reply, AggregatedBoids](
                sendRequests =
                  adapter => workers.foreach(worker => worker ! UpdateState(boidsModel, adapter, gridActor)),
                expectedReplies = workers.size,
                replyTo = context.self,
                aggregateReplies = replies =>
                  AggregatedBoids(replies.collect { case BoidWorker.UpdatedBoids(bs) => bs}.flatten.toList),
                timeout = 5.seconds
              )
            )
            waitingWorkers(replyTo)
          case AggregatedBoids(_) => Behaviors.ignore
        }

      def waitingWorkers(replyTo: ActorRef[UpdateAck]): Behavior[WorkerPoolCommand] =
        Behaviors.receiveMessage {
          case AggregatedBoids(boids) =>
            gridActor ! UpdateGrid(boids)
            replyTo ! UpdateAck(boids)
            idle()
          case Update(_) => Behaviors.ignore
        }
      idle()
    }

end WorkerPoolActor
