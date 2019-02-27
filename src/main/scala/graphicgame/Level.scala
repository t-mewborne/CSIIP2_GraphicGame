package graphicgame

class Level (
    val maze:Maze,
    private var _entities: Seq[Entity]){
  
  def entities():Seq[Entity] = _entities
  
  def +=(e:Entity):Unit = _entities +:= e
  
  def updateAll(delay:Double):Unit= {
    for (i <- 0 until _entities.length){
      if (!_entities(i).stillHere()) _entities.filter(_ != _entities(i)) //TODO This is not working properly
    }
   //_entities.filter(_.update(delay)) //remove dead entities
   _entities.foreach(_.update(delay))
  } 
}