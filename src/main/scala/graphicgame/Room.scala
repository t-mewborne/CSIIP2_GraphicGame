package graphicgame

class Room {
  
  private var upHeld = false
  private var downHeld = false
  private var leftHeld = false
  private var rightHeld = false

  
  
  
  private var _currentEnemy = new Enemy(util.Random.nextInt(8).toDouble, util.Random.nextInt(16).toDouble, (maze, entity), false, 1)
  private var _nextEnemy = new Enemy(util.Random.nextInt(8).toDouble, util.Random.nextInt(16).toDouble, (maze, entity), false, 1)
  private var _player = new Player(0,0,(maze, entity),upHeld,downHeld,leftHeld,rightHeld)


  private val moveInterval = 0.1
  private var moveDelay = 0.0
  

  def currentEnemy = _currentEnemy
  def nextEnemy = _nextEnemy
  def player = _player

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (upHeld) player.move(1)
      if (downHeld) player.move(2)
      if (leftHeld) player.move(3)
      if (rightHeld) player.move(4)
      moveDelay = 0.0
    }
    if (currentEnemy.stillHere) {
        //Make the enemy move?
      } else {
        _currentEnemy = nextEnemy
        _nextEnemy = new Enemy(util.Random.nextInt(8).toDouble, util.Random.nextInt(16).toDouble, (maze, entity), false, 1)
      }
  }

  def upPressed() = upHeld = true
  def leftPressed() = leftHeld = true
  def rightPressed() = rightHeld = true
  def downPressed() = downHeld = true
  def upReleased() = upHeld = false
  def leftReleased() = leftHeld = false
  def rightReleased() = rightHeld = false
  def downReleased() = downHeld = false
}

object Board {
  val width = 80
  val height = 100
}