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
  
  private val moveInterval = 0.006
  private var moveDelay = 0.003



  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height

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
  //def stillHere(): Boolean = ???

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