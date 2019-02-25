package graphicgame

class Level (
    val maze:Maze,
    private var _entities: Seq[Entity]){
  
  def entities():Seq[Entity] = _entities
  
  def +=(e:Entity):Unit = _entities +:= e
  
  def updateAll(delay:Double):Unit= {
   _entities.foreach(_.update(delay))
  }
  
}