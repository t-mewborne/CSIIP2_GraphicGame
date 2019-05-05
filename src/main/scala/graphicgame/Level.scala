package graphicgame

import collection.mutable
import scalafx.scene.input.KeyCode

class Level(
  val maze: Maze,
  private var _entities: Seq[Entity]) {

  private var i = 0
  //private var currLength = _entities.length

  def entities(): Seq[Entity] = _entities

  def +=(e: Entity): Unit = _entities +:= e

  def updateAll(delay: Double): Unit = {
    /*
     * A for loop was causing an error with the following idea.
     * I believe _entities.length was only read once, and when an entity was removed from the level the list was no longer as long.
     * Therefore, the for loop was throwing an index out of bounds
     */
    _entities.foreach(_.update(delay))
    _entities = _entities.filter(_.stillHere())
  }

  def players = entities.collect { case p: Player => p }
  def enemies = entities.collect { case e: Enemy => e }
  def projectiles = entities.collect { case pr: Projectile => pr }
  def makePassable(x:Double,y:Double,score:Int) = new PassableLevel(entities.map(_.makePassable), maze, x, y, score)
}