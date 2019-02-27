package graphicgame

import collection.mutable

class Enemy(
  private var _x:      Double,
  private var _y:      Double,
  private var _width:  Int,
  private var _height: Int,
  maze:                Maze) extends Entity {

  private var dead = false
  private val moveInterval = 0.03
  private var moveDelay = 0.0
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = true
  private var _stillHere = true
  private val _enemyType = util.Random.nextInt(4) + 1 //1, 2, 3, 4 Red, Orange, Blue, Pink
  private var wallTest = new Array[Array[Int]](maze.height)

  def x(): Double = _x
  def y(): Double = _y
  def width(): Double = _width
  def height(): Double = _height
  def stillHere(): Boolean = _stillHere
  def enemyType(): Int = _enemyType

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

  //def postCheck(): Unit = ??? //What is this for?

  def move(dx: Double, dy: Double): Unit = {
    if (maze.isClear(x + dx, y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }

//  for (r <- -5 until maze.height + 5) {
//      for (c <- -5 until maze.width + 5) {
//        if (maze(r, c) == Wall) print('#') else print(' ')
//      }
//      println()
//    }
  
  //println(shortestPath(0, 0, 9, 9, maze.getWallArray))
  val offsets = List((0, -1), (0, 1), (-1, 0), (1, 0))
  def shortestPath(sx: Int, sy: Int, ex: Int, ey: Int, maze: Array[Array[Int]]): Int = {
    val q = new ArrayQueue[(Int, Int, Int)]()
    q.enqueue(sx, sy, 0)
    val visited = mutable.Set[(Int, Int)](sx -> sy)
    while (!q.isEmpty) {
      val (x, y, steps) = q.dequeue()
      for ((dx, dy) <- offsets) {
        val nx = x + dx
        val ny = y + dy
        if (nx == ex && ny == ey) return steps + 1
        if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze(nx).length && maze(nx)(ny) == 0 && !visited(nx -> ny)) {
          q.enqueue(nx, ny, steps + 1)
          visited += nx -> ny
        }
      }
    }
    100000000
  }
}

object Enemy {

}