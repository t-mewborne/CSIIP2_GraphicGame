//ENTITY STYLE (2,x(0-3))

package graphicgame

import collection.mutable

class Enemy(
  private var _x: Double,
  private var _y: Double,
  private var _width: Int,
  private var _height: Int,
  val level: Level) extends Entity {

  val boss = util.Random.nextInt(10) > 8
  val style = if (boss)(2, 4) else (2, util.Random.nextInt(4)) //0, 1, 2, 3, 4 Red, Orange, Blue, Pink, Boss TODO Change enemy type if player captured/enemy killed
  private var dead = false
  private val speed = 10
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = true
  private var _stillHere = true
  private var health = 10

  //TODO change x,y coordinates if player/another enemy is already in the given spawning location

  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height
  def stillHere(): Boolean = _stillHere
  def makePassable(): PassableEntity = new PassableEntity(x, y, width, height, style)
  def score():Int = 0

  def update(delay: Double): Unit = {
    if (level.players.length > 0) {
      var distances = level.players.map(player => math.sqrt(((_x - player.x) * (_x - player.x)) + ((_y - player.y) * (_y - player.y))))
      var distance = distances.min
      var playerTrack = level.players(distances.indexOf(distance))
      if (distance <= 40) findPlayer(speed * delay, playerTrack)
      else randomMove(delay)
      if (Entity.intersect(this, playerTrack)) playerTrack.kill()
    }
  }

  //def postCheck(): Unit = ??? //TODO What is this for?

  def findPlayer(moveInterval: Double, player: Player): Unit = {
    val up = shortestPath(_x, _y - 1, player.x, player.y)
    val down = shortestPath(_x, _y + 1, player.x, player.y)
    val left = shortestPath(_x - 1, _y, player.x, player.y)
    val right = shortestPath(_x + 1, _y, player.x, player.y)
    if (up <= down && up <= left && up <= right) move(0, -moveInterval)
    else if (down <= up && down <= left && down <= right) move(0, moveInterval)
    else if (left <= up && left <= down && left <= right) move(-moveInterval, 0)
    else if (right <= up && right <= left && right <= down) move(moveInterval, 0)
  }

  def move(dx: Double, dy: Double): Unit = { //1, 2, 3, 4: Up, Down, Left, Right
    if (boss && level.maze.isClear(x + dx*2, y + dy*2, width, height, this)) {
      _x += dx*2
      _y += dy*2
    }
    
    if (!boss && level.maze.isClear(x + dx, y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }

  def kill() {
    if (boss) health -= 1 else health = 0
    _stillHere = !(health<=0)
  }

  def randomMove(delay: Double) {
    //TODO
    var dx = util.Random.nextInt(3) - 1
    var dy = util.Random.nextInt(3) - 1
    if (dx != 0) dy = 0
    for (i <- 0 to util.Random.nextInt(5) + 5) move(dx * delay, dy * delay)
  }

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