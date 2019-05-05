//ENTITY STYLE (1,1-8)

package graphicgame

import java.net.Socket
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Player(
  private var _x: Double,
  private var _y: Double,
  private var _width: Double,
  private var _height: Double,
  private var _score: Int,
  val level: Level,
  val sock: Socket,
  val in: ObjectInputStream,
  val out: ObjectOutputStream) extends Entity {

  
  private var _stillHere = true
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = false
  private var mouseX = 0.0
  private var mouseY = 0.0
  private val speed = 20
  
  private var _facing = 0 //0-7 = Right, Right/Down, Down, Down/Left, Left, Left/Up, Up, Up/Right
  var style = (1, facing)
 

  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height
  def stillHere(): Boolean = _stillHere
  def makePassable(): PassableEntity = new PassableEntity(x, y, width, height, style)
  def score():Int = _score
  
  def kill(): Unit = {
    //println("Dead :(")
    //_stillHere = false
    _x = 5+util.Random.nextInt(Server.mazeWidth) * 10
    _y = 5+util.Random.nextInt(Server.mazeHeight) * 10
  }
  
  def facing(): Int = {
    if (movingUp && movingRight) _facing = 7
    else if (movingUp && movingLeft) _facing = 5
    else if (movingDown && movingRight) _facing = 1
    else if (movingDown && movingLeft) _facing = 3
    else if (movingUp) _facing = 6
    else if (movingDown) _facing = 2
    else if (movingLeft) _facing = 4
    else if (movingRight) _facing = 0
    _facing
  }

  def update(delay: Double): Unit = {
      if (movingUp) move(0,-speed*delay)
      if (movingDown) move(0,speed*delay)
      if (movingLeft) move(-speed*delay,0)
      if (movingRight) move(speed*delay,0)
  }

 // def postCheck(): Unit = ???
  
  def mouseClick(mouseXPos:Double,mouseYPos:Double): Unit = {
    mouseX = mouseXPos
    mouseY = mouseYPos
    level += new Projectile(_x,_y,1,1,level,mouseXPos,mouseYPos,this)
  }
  
  def increaseScore: Unit = _score += 1

  def upPressed(): Unit = movingUp = true
  def downPressed(): Unit = movingDown = true
  def leftPressed(): Unit = movingLeft = true
  def rightPressed(): Unit = movingRight = true
  
  def upReleased(): Unit = movingUp = false
  def downReleased(): Unit = movingDown = false
  def leftReleased(): Unit = movingLeft = false
  def rightReleased(): Unit = movingRight = false
  
  def handleKey(obj: AnyRef): Unit = {

    obj match {
      case UpPressed => upPressed()
      case DownPressed => downPressed()
      case LeftPressed => leftPressed()
      case RightPressed => rightPressed()
      case UpReleased => upReleased()
      case DownReleased => downReleased()
      case LeftReleased => leftReleased()
      case RightReleased => rightReleased()
      case MouseClicked(x,y) => mouseClick(x,y)
    }
  }
  
  def move(dx:Double,dy:Double):Unit={
    if (level.maze.isClear(x+dx,y+dy,width,height,this)){
      _x+=dx
      _y+=dy
    }
  }
}

object Player {
    
}