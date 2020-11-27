/*
  By default, sbt runs the sbt task within the same JVM.
  If something happens that would shut down the JVM, then the sbt
  server would shut down as well. Setting this to true forks the JVM
  so that failures in the code execution doesn't crash sbt.
  See https://www.scala-sbt.org/1.x/docs/Forking.html
  See https://stackoverflow.com/questions/44298847/why-do-we-need-to-add-fork-in-run-true-when-running-spark-sbt-application
*/
fork in run := true

/*
  By default, sbt does not forward STDIN to the forked process.
  If you fork and use STDIN, use this setting.
  See https://www.scala-sbt.org/1.x/docs/Forking.html#Configuring+Input 
*/
connectInput in run := true
