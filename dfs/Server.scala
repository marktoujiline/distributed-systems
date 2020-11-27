package dfs
// Simple server
import java.net._
import java.io._
import scala.io._

class Server(port: Int) {

    def run(): Unit = {
        val serverThread = new Thread {
            /**
              * Creates a socket bound to the port. At this point, other
              * apps can no longer create sockets at the same port. Also,
              * connections are not allowed until `accept` is called.
              */
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
                        /**
                          * Block until connection is made.
                          */
                        val socket = server.accept()

                        /**
                          * In/out streams represent a stream of bytes.
                          * Implemented read/write operations read/write a single
                          * byte at a time. Blocks until data is available
                          * or end of stream is detected.
                          */
                        val inputStream = socket.getInputStream()
                        val outputStream = socket.getOutputStream()

                        /**
                          * BufferedSource and PrintStream are wrappers around lower level Stream objects.
                          * Provide convenience methods.
                          */
                        val in = new BufferedSource(inputStream)
                        val out = new PrintStream(outputStream)

                        val it: Iterator[String] = in.getLines()
                        
                        // Send next line to socket recipient
                        out.println(s"server-${it.next()}")
                    }
                } catch {
                    case ex: SocketException =>
                        println("Socket was closed, terminating gracefully.")
                    case ex: IOException => {
                        println("Socket#accept failed")
                        ex.printStackTrace()
                    }
                    case ex: SecurityException =>
                        println("Security manager did not allow operation")
                        ex.printStackTrace()
                }
            }
        }
        serverThread.start()
        println("Press enter to stop server ")
        StdIn.readLine()
        serverThread.interrupt()
    }
}
