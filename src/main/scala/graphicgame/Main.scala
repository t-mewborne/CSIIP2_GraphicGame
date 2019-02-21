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
  val boardWidth = 100 * 10
  val boardHeight = 80 * 10
  stage = new JFXApp.PrimaryStage {
    title = "Pacman Survival" // TODO Change this to match the theme of your game.
    scene = new Scene(boardWidth, boardHeight) {
      // Put your code here.
      val canvas = new Canvas(boardWidth, boardHeight)
      val gc = canvas.graphicsContext2D
      val renderer = new Renderer2D(gc,7)
      
      val maze = RandomMaze(20,false,130,130,0.7)
      val player = new Player(103,95,5,5,maze)
      val enemy = new Enemy(87,70,12,12,maze)
      val level = new Level(maze, Seq(player,enemy))
      content = canvas
      renderer.render(level, player.x,player.y)

            onKeyPressed = (ke: KeyEvent) => {
              ke.code match {
                case KeyCode.Up => player.upPressed()
                case KeyCode.Down => player.downPressed()
                case KeyCode.Left => player.leftPressed()
                case KeyCode.Right => player.rightPressed()
                case _ =>
              }
            }
            onKeyReleased = (ke: KeyEvent) => {
              ke.code match {
                case KeyCode.Up => player.upReleased()
                case KeyCode.Down => player.downReleased()
                case KeyCode.Left => player.leftReleased()
                case KeyCode.Right => player.rightReleased()
                case _ =>
              }
            }
      var lastTime = -1L
      val timer = AnimationTimer(time => {
        if (lastTime != -1) {
          val delay = (time - lastTime) / 1e9
          //gc.clearRect(0,0,boardWidth,boardHeight) //TODO I don't think I need this
          level.updateAll(delay)
          renderer.render(level, player.x,player.y)
          if (Entity.intersect(player, enemy)) println("Player/Enemy Intersection-- you died") //TODO Delete this later
        }
        lastTime = time
      })
      timer.start()
    }
  }
}


//key pressed events make sure to import scalafx.something._