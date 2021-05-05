package dfs
// Simple server
import java.net._
import java.io._
import scala.io._

class LargeObject {
    private var _data = Array.ofDim[Byte](52428788)

}

class MemoryAnalysis(port: Int) {

    def generateManyThreadsWithHighCPUUsage(): Unit = {
        val threads = for (i <- 0 to 10) yield (new Thread {
            override def run(): Unit = {
                while(!Thread.interrupted()) {}
            }
        })
        threads.foreach(t => t.start())
        Thread.sleep(5000)
        threads.foreach(_.interrupt())
    }

    def generateLargeHeapObjects(): Unit = {
        val largeObjects = for (i <- 0 to 100) yield {
            Thread.sleep(500)
            new LargeObject()
        }
    }
}
