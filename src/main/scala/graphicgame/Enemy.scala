package graphicgame

import collection.mutable

class Enemy(
  private var _x:      Double,
  private var _y:      Double,
  private var _width:  Int,
  private var _height: Int,
  maze:                Maze,
  player: Player) extends Entity {

  private var dead = false
  private val moveInterval = 0.03
  private var moveDelay = 0.0
  private var movingUp = false
  private var movingDown = false
  private var movingLeft = false
  private var movingRight = true
  private var _stillHere = true
  private val _enemyType = util.Random.nextInt(4) + 1 //1, 2, 3, 4 Red, Orange, Blue, Pink TODO Change enemy image if player captured/enemy killed, takes 2 shots to kill enemy?
  private var wallTest = Array.ofDim[Int](maze.height,maze.width)

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
  
  for (r <- 0 until maze.height){ //Create a map of the maze for the enemy to find the shortest path
    for (c <- 0 until maze.width){
      wallTest(r)(c) = if (maze.apply(r,c) == Floor) 0 else -1
    }
  }
  //println("Is there a wall here: " + (if (maze.apply(_x.toInt,_y.toInt) == Floor) "no" else "yes"))
  
  //println(shortestPath(_x.toInt, _y.toInt, player.x.toInt, player.y.toInt, wallTest))
  val offsets = List((0, -1), (0, 1), (-1, 0), (1, 0))
  def shortestPath(sx: Int, sy: Int, ex: Int, ey: Int, maze: Array[Array[Int]]): Int = {
    val q = new ArrayQueue[(Int, Int, Int)]()
    q.enqueue(sx, sy, 0)
    val visited = mutable.Set[(Int, Int)](sx -> sy)
    while (!q.isEmpty) {
      val (x, y, steps) = q.dequeue()
      for ((dx, dy) <- offsets) { //TODO this line is throwing an error when shortestPath is called
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