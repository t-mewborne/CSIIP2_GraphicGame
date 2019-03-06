package graphicgame

import collection.mutable

class Enemy(
  private var _x:      Double,
  private var _y:      Double,
  private var _width:  Int,
  private var _height: Int,
  val level:           Level,
  player:              Player) extends Entity {

  private var dead = false
  private val speed = 18
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = true
  private var _stillHere = true
  //  private var _shortestUp = 0
  //  private var _shortestDown = 0
  //  private var _shortestLeft = 0
  //  private var _shortestRight = 0
  private val _enemyType = util.Random.nextInt(4) + 1 //1, 2, 3, 4 Red, Orange, Blue, Pink TODO Change enemy image if player captured/enemy killed, takes 2 shots to kill enemy?

  //TODO change x,y coordinates if player/another enemy is already in the given spawning location

  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height
  def stillHere(): Boolean = _stillHere
  def enemyType(): Int = _enemyType

  def update(delay: Double): Unit = {
    var distance = math.sqrt(((_x - player.x) * (_x - player.x)) + ((_y - player.y) * (_y - player.y)))
    if (distance <= 40) findPlayer(speed*delay)
    else randomMove()
    if (Entity.intersect(this, player)) player.kill()
  }

  //def postCheck(): Unit = ??? //TODO What is this for?

  def findPlayer(moveInterval : Double): Unit = {
      val up = shortestPath(_x, _y - 1, player.x, player.y)
      val down = shortestPath(_x, _y + 1, player.x, player.y)
      val left = shortestPath(_x - 1, _y, player.x, player.y)
      val right = shortestPath(_x + 1, _y, player.x, player.y)
      if (up <= down && up <= left && up <= right) move(0, -1)
      else if (down <= up && down <= left && down <= right) move(0, moveInterval)
      else if (left <= up && left <= down && left <= right) move(-moveInterval, 0)
      else if (right <= up && right <= left && right <= down) move(moveInterval, 0)
  }

  def move(dx: Double, dy: Double): Unit = { //1, 2, 3, 4: Up, Down, Left, Right
    if (level.maze.isClear(x + dx, y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }
  
  def kill(){
    _stillHere = false
  }

  def randomMove() {
    //TODO
    var dx = util.Random.nextInt(3) - 1
    var dy = util.Random.nextInt(3) - 1
    if (dx != 0) dy = 0
    for (i <- 0 to util.Random.nextInt(5) + 5) move(dx, dy)
  }

  //println("Is there a wall here: " + (if (maze.apply(_x.toInt,_y.toInt) == Floor) "no" else "yes"))

  //println(shortestPath(_x.toInt, _y.toInt, player.x.toInt, player.y.toInt))
  def shortestPath(sx: Double, sy: Double, ex: Double, ey: Double): Int = {
    if (level.maze.isClear(sx, sy, width, height, this)) {
      val offsets = List((0, -1), (0, 1), (-1, 0), (1, 0))
      val q = new ArrayQueue[(Double, Double, Int)]()
      q.enqueue(sx, sy, 0)
      var solution: Int = 1000000000
      val visited = mutable.Set[(Double, Double)](sx -> sy)
      while (!q.isEmpty && solution == 1000000000) {
        val (x, y, steps) = q.dequeue()
        for ((dx, dy) <- offsets) {
          val nx = x + dx
          val ny = y + dy
          if (level.maze.isClear(nx, ny, width, height, this) && !visited(nx -> ny) && steps < 50) {
            if (math.abs(nx - ex) < 3 && (ny - ey).abs < 3) solution = steps
            q.enqueue(nx, ny, steps + 1)
            visited += nx -> ny
          }
        }
      }
      solution
    } else 1000000000
  }
}

object Enemy {

}