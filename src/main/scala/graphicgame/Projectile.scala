package graphicgame

class Projectile(
  private var _x: Double,
  private var _y: Double,
  private var _width: Int,
  private var _height: Int,
  maze: Maze,
  private var newX: Double,
  private var newY: Double) extends Entity {

  private var _stillHere = true
  
  private var timeAlive = 0.0
  private val moveInterval = 0.003
  private var moveDelay = 0.0

  private var m = (_y - newY) / (_x - newX)
  private var b = newY - m * newX

  private var dx = 1.0
  if (math.abs(newX - _x) < 10) dx = dx / 2
  if (math.abs(newX - _x) < 5) dx = dx / 2
  if (math.abs(newX - _x) < 2.5) dx = dx / 2
  if (math.abs(newX - _x) < 1.25) dx = 0

  def x: Double = _x
  def y: Double = _y
  def width: Double = _width
  def height: Double = _height
  def stillHere = _stillHere

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (_y != newY) move()
      else _stillHere = false
      moveDelay = 0.0
      timeAlive += 0.1
      if (timeAlive > 10.0) _stillHere = false
      println(_stillHere)
    }
  }

  def move() {
    if (_x == newX && _y == newY) _stillHere = false 
    else {
      val xTest = if (newX < _x) _x - 1 else _x + 1
      val yTest = (m * xTest) + b
      if (maze.isClear(xTest, yTest, width, height, this)) {
        if (newX < _x) _x -= dx
        else _x += dx
        _y = (m * _x) + b
      } 
      else _stillHere = false
    }
  }
}