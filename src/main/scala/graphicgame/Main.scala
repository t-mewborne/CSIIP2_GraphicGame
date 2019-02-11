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
  val boardWidth = 100 * Renderer2D.cellSize
  val boardHeight = 80 * Renderer2D.cellSize
  stage = new JFXApp.PrimaryStage {
    title = "My Game" // TODO Change this to match the theme of your game.
    scene = new Scene(boardWidth, boardHeight) {
      // Put your code here.
      val canvas = new Canvas(boardWidth, boardHeight)
      val gc = canvas.graphicsContext2D
      val renderer = new Renderer2D(gc, Renderer2D.cellSize)
      val room = new Room
      content = canvas

            onKeyPressed = (ke: KeyEvent) => {
              ke.code match {
                case KeyCode.Up => room.upPressed()
                case KeyCode.Down => room.downPressed()
                case KeyCode.Left => room.leftPressed()
                case KeyCode.Right => room.rightPressed()
                case _ =>
              }
            }
            onKeyReleased = (ke: KeyEvent) => {
              ke.code match {
                case KeyCode.Up => room.upReleased()
                case KeyCode.Down => room.downReleased()
                case KeyCode.Left => room.leftReleased()
                case KeyCode.Right => room.rightReleased()
                case _ =>
              }
            }

      var lastTime = -1L
      val timer = AnimationTimer(time => {
        if (lastTime != -1) {
          val delay = (time - lastTime) / 1e9
          room.update(delay)
          renderer.render((maze, entity), 0.0,0.0)
        }
        lastTime = time
      })
      timer.start()
    }
  }
}


//key pressed events make sure to import scalafx.something._