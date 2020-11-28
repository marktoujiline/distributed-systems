package dfs
// Simple server
import java.net._
import java.io._
import scala.io._
import scala.annotation.tailrec

class ServerThread(serverSocket: ServerSocket) extends Thread {

    /**
     * Because getLines blocks, there is no way for the interrupt to terminate the
     * thread without some data being sent. The only way to stop the thread is to 
     * close the socket. This will cause a SocketException to be thrown at the getLines
     * call site, which is why that exception has to be handled.
     */
    override def interrupt(): Unit = {
        serverSocket.close()
        super.interrupt()
    }

    override def run(): Unit = {
        try {
            while (!Thread.interrupted()) {
                /**
                 * Block until connection is made.
                 */
                val socket = serverSocket.accept()
                println(s"Server port created for client = ${socket.getPort()} by ${Thread.currentThread().getName()}")
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
                val out = new PrintStream(outputStream)

                @tailrec
                def readData(): Unit = {
                    val byte = inputStream.read()
                    if (byte != -1) {
                        os.write.append(os.pwd/"out.txt", Array(byte.byteValue()))
                        readData()
                    }
                }
                readData()
                out.println("Finished uploading")
                socket.close()
                println("Closed socket")
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

class Server(port: Int) {

    def run(): Unit = {
        /* Creates a socket bound to the port. At this point, other
         * apps can no longer create sockets at the same port. Also,
         * connections are not allowed until `accept` is called.
        */
        val socket = new ServerSocket(port)
        val threadPool = for(i <- 1 to 3) yield {
            val thread = new ServerThread(socket)
            thread.setName(s"Thread-${i}")
            thread
        }

        threadPool.foreach(_.start())
        println("Press enter to stop server ")
        StdIn.readLine()
        threadPool.foreach(_.interrupt())
    }
}
