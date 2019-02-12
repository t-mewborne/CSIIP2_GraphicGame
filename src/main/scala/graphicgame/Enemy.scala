package graphicgame

class Enemy(
  private var _x: Double,
  private var _y: Double,
  private var _width: Int,
  private var _height: Int,
  maze: Maze) extends Entity {

  private var dead = false
  private val moveInterval = 0.03
  private var moveDelay = 0.0
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = true

  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (movingUp) move(0, -1)
      if (movingDown) move(0, 1)
      if (movingLeft) move(-1, 0)
      if (movingRight) move(1, 0)
      moveDelay = 0.0
    }
  }

  def postCheck(): Unit = ??? //What is this for

  def stillHere(): Boolean = ???

  def move(dx: Double, dy: Double): Unit = {
    if (maze.isClear(x + dx, y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }
}

object Enemy {

}