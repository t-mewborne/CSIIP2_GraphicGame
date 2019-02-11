package graphicgame

class Player(
  private var _x: Double,
  private var _y: Double,
  val level: Level,
  private var movingUp: Boolean,
  private var movingDown: Boolean,
  private var movingLeft: Boolean,
  private var movingRight: Boolean) extends Entity {

  private val moveInterval = 0.1
  private var moveDelay = 0.0

  private var _width = 5
  private var _height = 7

  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (movingUp) _y-=1
      if (movingDown) _y+=1
      if (movingLeft) _x-=1
      if (movingRight) _x+=1
      moveDelay = 0.0
    }
  }

  def postCheck(): Unit = ??? //What is this for

  def stillHere(): Boolean = ???

  def moveUpPressed(): Unit = movingUp = true
  def moveDownPressed(): Unit = movingDown = true
  def moveLeftPressed(): Unit = movingLeft = true
  def moveRightPressed(): Unit = movingRight = true

  def moveUpReleased(): Unit = movingUp = false
  def moveDownReleased(): Unit = movingDown = false
  def moveLeftReleased(): Unit = movingLeft = false
  def moveRightReleased(): Unit = movingRight = false
  
  def move(direction:Int):Unit={
    if (direction == 1) _y-=Renderer2D.cellSize //Up
    if (direction == 2) _y+=Renderer2D.cellSize //Down
    if (direction == 3) _x-=Renderer2D.cellSize //Left
    if (direction == 4) _x+=Renderer2D.cellSize //Right
  }

}

object Player {
    
}