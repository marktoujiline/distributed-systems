package dfs

// Simple client
import java.net._
import java.io._
import scala.io._
import scala.annotation.tailrec

class Client(port: Int) {
    def run(): Unit = {
        val s = new Socket(InetAddress.getByName("localhost"), port)
        val remoteIn = new BufferedSource(s.getInputStream())
        val file = new BufferedInputStream(new FileInputStream("in.txt"))
        val out = new BufferedOutputStream(s.getOutputStream())

        @tailrec
        def upload(): Unit = {
            /**
              * Since underlying InputStream is FileInputStream,
              * a -1 is written when EOF has been reached.
              */
            val byte = file.read()

            if (byte != -1) {
                out.write(byte)
                upload()
            }
        }

        upload()

        /**
         * Flush is necessary because OutputStream is buffered.
         * If buffer hasn't been filled up, then data isn't sent.
         * Flush sends whatever is in the buffer, even if it isn't full.
         */
        
        out.flush()

        /**
         * Closes output side of the socket. Input is still open
         * so messages from server can be receives. No more data
         * can be sent to server via this connection.
         */
        s.shutdownOutput()

        println("Read response from server, if any:")
        for (line <- remoteIn.getLines()) {
            println(s"::: $line")
        }
        file.close()
        s.close()
    }
}
