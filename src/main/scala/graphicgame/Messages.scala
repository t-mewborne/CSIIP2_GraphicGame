package graphicgame

case object UpPressed
case object DownPressed 
case object LeftPressed 
case object RightPressed 

case object UpReleased
case object DownReleased 
case object LeftReleased 
case object RightReleased 

case class MouseClick(x:Double,y:Double)