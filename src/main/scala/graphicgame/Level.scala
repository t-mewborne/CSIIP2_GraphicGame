package graphicgame

class Level (
    val maze:Maze,
    private var _entities: Seq[Entity]){
    
  private var i = 0
  //private var currLength = _entities.length
  
  def entities():Seq[Entity] = _entities
  
  def +=(e:Entity):Unit = _entities +:= e
  
  def updateAll(delay:Double):Unit= {
    /*
     * A for loop was causing an error with the following idea. 
     * I believe _entities.length was only read once, and when an entity was removed from the level the list was no longer as long.
     * Therefore, the for loop was throwing an index out of bounds
     */
    i = 0
    while (i < _entities.length){
      if (!_entities(i).stillHere()) _entities = _entities.filter(_ != _entities(i)) 
      i+=1 
    }
   _entities.foreach(_.update(delay))
  } 
}