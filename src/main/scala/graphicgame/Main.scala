package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {
  val boardWidth = 100 * 10 //TODO 10 to a universal number (see git>dr lewis in class code>drmario (RandomMaze.width?)
  val boardHeight = 80 * 10 //TODO ^^
  stage = new JFXApp.PrimaryStage {
    title = "My Game" // TODO Change this to match the theme of your game.
    scene = new Scene(boardWidth, boardHeight) {
      // Put your code here.
      val canvas = new Canvas(boardWidth, boardHeight)
      val gc = canvas.graphicsContext2D
      val renderer = new Renderer2D(gc, 10.0) //TODO may need to change value for block size
      content = canvas
    }
  }
}


//key pressed events make sure to import scalafx.something._