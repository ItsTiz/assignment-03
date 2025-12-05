package actor_solution.utils

object SimulationParameters:

  /**
   * General
   * */
  val boidNumber: Int = 1000
  val workerActors: Int  = 16
  val framerate: Int = 144
  val updateInterval: Long = 1000 / framerate
  val environmentWidth: Int = 1920
  val environmentHeight: Int = 1080

  /**
   * UI
   * */
  val windowWidth: Int = 1920
  val windowHeight: Int = 1080
  val boidWidth: Int = 5
  val boidHeight: Int = 5

  /**
   * Boids
   * */
  val boidMaxSpeed: Double = 4.0
  val boidPerceptionRadius: Double = 30.0
  val boidAvoidRadius: Double = 20.0
  val separationWeight = 1.0
  val alignmentWeight = 1.0
  val cohesionWeight = 1.0