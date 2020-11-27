package dfs
// Simple server
import java.net._
import java.io._
import scala.io._

class Server(port: Int) {

    def run(): Unit = {
        val serverThread = new Thread {
            val server = new ServerSocket(port);

            /**
              * Because getLines blocks, there is no way for the interrupt to terminate the
              * thread without some data being sent. The only way to stop the thread is to 
              * close the socket. This will cause a SocketException to be thrown at the getLines
              * call site, which is why that exception has to be handled.
              */
            override def interrupt(): Unit = {
                server.close()
                super.interrupt()
            }

            override def run(): Unit = {
                try {
                    while (!Thread.interrupted()) {
                        val s = server.accept()
                        val in = new BufferedSource(s.getInputStream()).getLines()
                        val out = new PrintStream(s.getOutputStream())
                        // Send message to socket recipient
                        out.println(s"server-${in.next()}")
                    }
                } catch {
                    case ex: SocketException =>
                        println("Gracefully terminating ...")
                }
            }
        }
        serverThread.start()
        println("Press enter to stop server ")
        StdIn.readLine()
        serverThread.interrupt()
    }
}
