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
    title = "Pacman Survival" // TODO Change this to match the theme of your game.
    scene = new Scene(boardWidth, boardHeight) {
      // Put your code here.
      val canvas = new Canvas(boardWidth, boardHeight)
      val gc = canvas.graphicsContext2D
      val renderer = new Renderer2D(gc,7)
      
      val maze = RandomMaze(20,false,mazeHeight,mazeWidth,0.7)
      val player = new Player(10+util.Random.nextInt(mazeWidth)*20,10+util.Random.nextInt(mazeHeight)*20,5,5,maze)
      val level = new Level(maze, Seq(player))
      for (i <- 0 to 30) level += new Enemy(10+util.Random.nextInt(mazeWidth)*20,10+util.Random.nextInt(mazeHeight)*20,10,10,maze)
      content = canvas
      renderer.render(level, player.x,player.y)

      onMouseClicked = (e: MouseEvent) => {
        player.mouseClick(e.getX,e.getY)
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
          //if (Entity.intersect(player, enemy)) println("Player/Enemy Intersection-- you died") //TODO Delete this later
        }
        lastTime = time
      })
      timer.start()
    }
  }
}


//key pressed events make sure to import scalafx.something._