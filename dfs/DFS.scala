package dfs

object DFS {
  def main(args: Array[String]): Unit = {
    val PORT  = 9999;
    if (args.length == 1) {
      args(0) match {
        case "-s" => 
          println("server")
          (new Server(PORT)).run()
        case "-c" =>
          println("client")
          (new Client(PORT)).run()
        case _ => println("neither")
      }
    } else {
      println("Wrong number of arguments")
    }
  }
}