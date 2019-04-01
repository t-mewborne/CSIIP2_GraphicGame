package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.input.MouseEvent

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {
  val boardWidth = 100 * 10
  val boardHeight = 80 * 10
  val mazeWidth  = 30
  val mazeHeight = 30
  stage = new JFXApp.PrimaryStage {
    title = "Pacman?" // TODO Change this to match the theme of your game.
    scene = new Scene(boardWidth, boardHeight) {
      // Put your code here.
      val canvas = new Canvas(boardWidth, boardHeight)
      val gc = canvas.graphicsContext2D
      val renderer = new Renderer2D(gc,14)
      
      val maze = RandomMaze(10,false,mazeHeight,mazeWidth,0.7)
      val level = new Level(maze, Seq())
      val player = new Player(5+util.Random.nextInt(mazeWidth)*10,5+util.Random.nextInt(mazeHeight)*10,2.5,2.5,level)
      level += player
      for (i <- 0 to 20) level += new Enemy(5+util.Random.nextInt(mazeWidth)*10,5+util.Random.nextInt(mazeHeight)*10,5,5,level,player)
      content = canvas
      renderer.render(level, player.x,player.y)

      onMouseClicked = (e: MouseEvent) => {
        player.mouseClick(renderer.pixelsToBlocksX(e.getX).toInt.toDouble,renderer.pixelsToBlocksY(e.getY).toInt.toDouble)
      }
      
      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.W => player.upPressed()
          case KeyCode.S => player.downPressed()
          case KeyCode.A => player.leftPressed()
          case KeyCode.D => player.rightPressed()
          case _ =>
        }
      }
      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.W => player.upReleased()
          case KeyCode.S => player.downReleased()
          case KeyCode.A => player.leftReleased()
          case KeyCode.D => player.rightReleased()
          case _ =>
        }
      }
      var lastTime = -1L
      val timer = AnimationTimer(time => {
        if (lastTime != -1) {
          val delay = (time - lastTime) / 1e9
          level.updateAll(delay)
          renderer.render(level, player.x,player.y)
        }
        lastTime = time
      })
      timer.start()
    }
  }
}


//key pressed events make sure to import scalafx.something._