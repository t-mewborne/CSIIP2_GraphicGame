package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import java.net.Socket
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import scalafx.scene.control.TextInputDialog
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.application.Platform
import scalafx.scene.input.MouseEvent

object Client extends JFXApp {
  val boardWidth = 100 * 10
  val boardHeight = 80 * 10
  
  val textDialog = new TextInputDialog("localhost")
  val machine = textDialog.showAndWait().getOrElse("localhost")
  
  val sock = new Socket(machine, 8080)

  val out = new ObjectOutputStream(sock.getOutputStream)
  val in = new ObjectInputStream(sock.getInputStream)

  val canvas = new Canvas(boardWidth, boardHeight)
  val gc = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, 14)

  stage = new JFXApp.PrimaryStage {
    title = "Pacman?"
    scene = new Scene(boardWidth, boardHeight) {
      content = canvas

      onMouseClicked = (e: MouseEvent) => {
        out.writeObject(MouseClicked(renderer.pixelsToBlocksX(e.getX).toInt.toDouble, renderer.pixelsToBlocksY(e.getY).toInt.toDouble))
      }

      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.W => out.writeObject(UpPressed)
          case KeyCode.S => out.writeObject(DownPressed)
          case KeyCode.A => out.writeObject(LeftPressed)
          case KeyCode.D => out.writeObject(RightPressed)
          case _ =>
        }
      }
      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.W => out.writeObject(UpReleased)
          case KeyCode.S => out.writeObject(DownReleased)
          case KeyCode.A => out.writeObject(LeftReleased)
          case KeyCode.D => out.writeObject(RightReleased)
          case _ =>
        }
      }

      Future {
        while (true) {
          in.readObject() match {
            case pl: PassableLevel =>
              Platform.runLater(renderer.render(pl, pl.playerX, pl.playerY))
          }
        }
      }
    }
  }
}