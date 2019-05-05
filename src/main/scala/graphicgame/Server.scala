package graphicgame

import java.net.ServerSocket
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue

object Server extends App {
  private var players = List[Player]()
  private val playerQueue = new LinkedBlockingQueue[Player]
  val mazeWidth = 30
  val mazeHeight = 30

  val portNumber = 8080
  private val ss = new ServerSocket(portNumber)
  println("Server hosted on port " + portNumber)
  val maze = RandomMaze(10, false, mazeHeight, mazeWidth, 0.7)
  val level = new Level(maze, Seq())
  
  for (i <- 0 to 20) level += new Enemy(5+util.Random.nextInt(mazeWidth)*10,5+util.Random.nextInt(mazeHeight)*10,5,5,level)
  
  Future {
    while (true) {
      val sock = ss.accept()
      println("A player joined the game. ")
      val in = new ObjectInputStream(sock.getInputStream)
      val out = new ObjectOutputStream(sock.getOutputStream)
      val player = new Player(5 + util.Random.nextInt(mazeWidth) * 10, 5 + util.Random.nextInt(mazeHeight) * 10, 2.5, 2.5, 0, level, sock, in, out)
      playerQueue.put(player)
      level += player
    }
  }

  var lastTime = -1L
  val frameInterval = 0.03
  var frameDelay = 0.0
  while (true) {
    while (playerQueue.size() > 0) {
      val player = playerQueue.take()
      Future {
        while (true) {
          player.handleKey(player.in.readObject())
        }
      }
      players ::= player
    }
    val time = System.nanoTime()
    if (lastTime != -1) {
      val delay = (time - lastTime) / 1e9
    	frameDelay += delay
    	level.updateAll(delay)
    	if (frameDelay >= frameInterval) {
        for (player <- players) {
          val pb = player.level.makePassable(player.x,player.y,player.score)
          try {
            player.out.writeObject(pb)
          } catch {
            case e: Throwable=>
            println("A player left the game.")
            players = players.filterNot(p => p.equals(player))
          }
        }
        frameDelay=0
      }
    }
    lastTime = time
  }
}