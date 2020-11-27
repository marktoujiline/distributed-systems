package dfs
// Simple server
import java.net._
import java.io._
import scala.io._

class Server(port: Int) {

    def run(): Unit = {
        val serverThread = new Thread {
            val server = new ServerSocket(port);

            override def interrupt(): Unit = {
                server.close()
                super.interrupt()
            }

            override def run(): Unit = {
                println(s"Starting server on port $port")
                try {
                    while (!Thread.interrupted()) {
                        val s = server.accept()
                        val in = new BufferedSource(s.getInputStream()).getLines()
                        val out = new PrintStream(s.getOutputStream())
                        out.println(s"server-${in.next()}")
                        out.flush()
                    }
                } catch {
                    case ex: SocketException =>
                        println("Socket closed, terminating thread")
                }
            }
        }
        serverThread.start()
        println("Press enter to kill the server ")
        StdIn.readLine()
        serverThread.interrupt()
    }
}
