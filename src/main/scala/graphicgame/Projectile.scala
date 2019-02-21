package graphicgame

class Projectile(  
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
  private var movingRight = false
  
  
  
  def x: Double = _x
  def y: Double = _y
  def width: Double = _width
  def height: Double = _height
  
  def update(delay: Double): Unit = ??? 
}