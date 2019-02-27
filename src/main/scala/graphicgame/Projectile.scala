package graphicgame

class Projectile(
  private var _x:      Double,
  private var _y:      Double,
  private var _width:  Int,
  private var _height: Int,
  maze:                Maze,
  private var newX:    Double,
  private var newY:    Double) extends Entity {

  private var _stillHere = true

  private var timeAlive = 0.0
  private val moveInterval = 0.003 //TODO Needs to be slightly faster then the player
  private var moveDelay = 0.0

  if (newY == _y) newY+=1 //Prevents projectiles from creating undefined lines (causes projectile to show up for a short time then disappear)
  
  private var m = (_y - newY) / (_x - newX) //Slope of the projectile
  private var b = newY - m * newX //Y intercept of the projectile

  private var dx = 1.0
  private var dy = 1.0
  if (math.abs(newX - _x) < 10) dx = dx / 2
  if (math.abs(newX - _x) < 5) dx = dx / 3
  if (math.abs(newX - _x) < 2.5) dx = dx / 4
  if (math.abs(newX - _x) < 1.25) dx = 0
  if (newX < _x) dx *= -1 //Make dx negative if the player clicked to the left of the center of the screen
  if (newY < _y) dy *= -1
  
  def x: Double = _x
  def y: Double = _y
  def width: Double = _width
  def height: Double = _height
  def stillHere: Boolean = _stillHere

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      move()
      moveDelay = 0.0
      timeAlive += 0.1
      if (timeAlive > 10.0) _stillHere = false //Kill the projectile after it has been alive for too long
    }
  }

  def move() {
    val xTest = if (newX < _x) _x - 1 else _x + 1
    val yTest = if (dx != 0) (m * xTest) + b else _y+dy
    //if (!maze.isClear(xTest, yTest, width, height, this)) m *= -1 //This is supposed to invert the slope but projectiles just go through walls
    if (maze.isClear(xTest, yTest, width, height, this)) {
      if (dx == 0) _y += dy
      else{
        _x += dx
        _y = (m * _x) + b
      }
    } 
    else _stillHere = false //Kill the projectile if it hits a wall. Maybe change this to bounce (invert slope?)
  }
}