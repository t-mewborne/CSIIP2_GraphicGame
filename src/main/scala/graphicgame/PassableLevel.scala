package graphicgame

case class PassableLevel(entities: Seq[PassableEntity], maze:Maze, playerX:Double, playerY:Double) extends Serializable