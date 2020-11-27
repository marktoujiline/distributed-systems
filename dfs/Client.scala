package dfs

// Simple client
import java.net._
import java.io._
import scala.io._



class Client(port: Int) {
    def run(): Unit = {
        val s = new Socket(InetAddress.getByName("localhost"), port)
        lazy val in = new BufferedSource(s.getInputStream()).getLines()
        val out = new PrintStream(s.getOutputStream())

        out.println("Hello, world")
        out.flush()
        println("Received: " + in.next())

        s.close()
    }
}
