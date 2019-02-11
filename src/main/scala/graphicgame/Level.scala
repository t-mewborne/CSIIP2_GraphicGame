package graphicgame

class Level (
    val maze:Maze,
    private var _entities: Seq[Entity]){
  
  def entities():Seq[Entity] = _entities
  
  def +=(e:Entity):Unit = ???
  
  def updateAll(delay:Double):Unit= {
    _entities(0).update(delay)
    _entities(1).update(delay)
  }
  
}