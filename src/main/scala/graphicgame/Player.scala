package graphicgame

class Player(
  private var _x: Double,
  private var _y: Double,
  private var _width: Int,
  private var _height: Int,
  maze: Maze) extends Entity {

  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = false
  private var mouseX = 0.0
  private var mouseY = 0.0
  private var shooting = false
  
  private var _facing = 4 //1, 2, 3, 4 = Up, Down, Left, Right
  
  private val moveInterval = 0.006
  private var moveDelay = 0.003



  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height
  def facing(): Int = _facing

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (movingUp) move(0,-1)
      if (movingDown) move(0,1)
      if (movingLeft) move(-1,0)
      if (movingRight) move(1,0)
      if (shooting) fire()
      moveDelay = 0.0
    }
  }

 // def postCheck(): Unit = ???
  //def stillHere(): Boolean = ???
  
  def mouseClick(mouseXPos:Double,mouseYPos:Double): Unit = {
    shooting = true
    mouseX = mouseXPos
    mouseY = mouseYPos
  }

  def upPressed(): Unit = {
    movingUp = true
    _facing = 1
  }
  def downPressed(): Unit = {
    movingDown = true
    _facing = 2
  }
  def leftPressed(): Unit = {
    movingLeft = true
    _facing = 3
  }
  def rightPressed(): Unit = {
    movingRight = true
    _facing = 4
  }
  
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
  
  def fire():Unit={
    println("Mouse event at "+ mouseX+","+mouseY)
    shooting = false
  }

}

object Player {
    
}