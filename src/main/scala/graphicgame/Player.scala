package graphicgame

class Player(
  private var _x: Double,
  private var _y: Double,
  private var _width: Int,
  private var _height: Int,
  maze: Maze) extends Entity {

  private var _stillHere = true
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = false
  private var mouseX = 0.0
  private var mouseY = 0.0
  
  private var _facing = 0 //0-7 = Right, Right/Down, Down, Down/Left, Left, Left/Up, Up, Up/Right
  
  private val moveInterval = 0.006
  private var moveDelay = 0.003



  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height
  def stillHere(): Boolean = _stillHere
  
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
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (movingUp) move(0,-1)
      if (movingDown) move(0,1)
      if (movingLeft) move(-1,0)
      if (movingRight) move(1,0)
      moveDelay = 0.0
    }
  }

 // def postCheck(): Unit = ???
  
  def mouseClick(mouseXPos:Double,mouseYPos:Double,level:Level): Unit = {
    mouseX = mouseXPos
    mouseY = mouseYPos
    val currentLevel=level
    currentLevel += new Projectile(_x,_y,2,2,maze,mouseXPos,mouseYPos)
  }

  def upPressed(): Unit = movingUp = true
  def downPressed(): Unit = movingDown = true
  def leftPressed(): Unit = movingLeft = true
  def rightPressed(): Unit = movingRight = true
  
  def upReleased(): Unit = movingUp = false
  def downReleased(): Unit = movingDown = false
  def leftReleased(): Unit = movingLeft = false
  def rightReleased(): Unit = movingRight = false
  
  def move(dx:Double,dy:Double):Unit={
    if (maze.isClear(x+dx,y+dy,width,height,this)){
      _x+=dx
      _y+=dy
    }
  }
}

object Player {
    
}