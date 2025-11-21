package actor_solution.utils

object SimulationParameters:

  /**
   * General
   * */
  val boidNumber: Int = 1000
  val workerActors: Int  = 16
  val framerate: Int = 60
  val updateInterval: Long = 1000 / framerate
  val environmentWidth: Int = 1000
  val environmentHeight: Int = 1000

  /**
   * UI
   * */
  val windowWidth: Int = 800
  val windowHeight: Int = 800

  /**
   * Boids
   * */
  val boidMaxSpeed: Double = 4.0
  val boidPerceptionRadius: Double = 30.0
  val boidAvoidRadius: Double = 20.0
  val separationWeight = 1.0
  val alignmentWeight = 1.0
  val cohesionWeight = 1.0