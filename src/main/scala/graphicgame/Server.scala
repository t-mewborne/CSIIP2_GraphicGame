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

  private val ss = new ServerSocket(4040)
  val maze = RandomMaze(10, false, mazeHeight, mazeWidth, 0.7)
  val level = new Level(maze, Seq())
  
  Future {
    while (true) {
      val sock = ss.accept()
      val in = new ObjectInputStream(sock.getInputStream)
      val out = new ObjectOutputStream(sock.getOutputStream)
      val player = new Player(5 + util.Random.nextInt(mazeWidth) * 10, 5 + util.Random.nextInt(mazeHeight) * 10, 2.5, 2.5, level, sock, in, out)
      playerQueue.put(player)
      level += player
    }
  }

  var lastTime = -1L
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
      for (player <- players) {
        if (player.board.update(delay)) {
          val pb = player.board.makePassable()
          player.out.writeObject(pb)
        }
      }
    }
    lastTime = time
  }
}