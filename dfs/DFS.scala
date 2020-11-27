package dfs

object DFS {
  def main(args: Array[String]): Unit = {
    val PORT  = 9999;
    if (args.length == 1) {
      args(0) match {
        case "-s" => (new Server(PORT)).run()
        case "-c" => (new Client(PORT)).run()
        case _ => println("Invalid argument, must be either -c or -s")
      }
    } else {
      println("Wrong number of arguments")
    }
  }
}