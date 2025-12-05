package actor_solution

import actor_solution.model.SimulationApp
import actor_solution.utils.SimulationParameters.{environmentWidth, environmentHeight, windowWidth, windowHeight}
import actor_solution.view.BoidsPanel
import akka.actor.typed.ActorSystem

import javax.swing.WindowConstants
import scala.concurrent.ExecutionContext.Implicits.global
import scala.swing.{Dimension, MainFrame, SimpleSwingApplication}

object BoidsSimulator extends SimpleSwingApplication:
  private val panel = new BoidsPanel(environmentWidth, environmentHeight)
  private val system = ActorSystem(SimulationApp(panel, top), "BoidsSimulation")

  def start(): Unit =
    top.visible = true

  def top: MainFrame = new MainFrame:
    title = "Boids Simulation"
    preferredSize = new Dimension(windowWidth, windowHeight)
    resizable = true
    peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)
    contents = panel

    listenTo(this)
    reactions += { case scala.swing.event.WindowClosing(_) =>
      system.terminate()
      system.whenTerminated.onComplete(_ => scala.swing.Swing.onEDT(quit()))
    }
