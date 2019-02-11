package graphicgame

class Level (
    val maze:Maze,
    private var _entities: Seq[Entity]){
  
  def entities():Seq[Entity]= ???
  def +=(e:Entity):Unit= ???
  def updateAll(delay:Double):Unit= ???
}