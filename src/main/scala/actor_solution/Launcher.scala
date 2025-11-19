package actor_solution

import akka.actor.typed.ActorSystem

object Launcher:
  def main(args: Array[String]): Unit =
    ActorSystem(SimulationApp(), "BoidsSimulation")