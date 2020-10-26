import play.sbt.PlayImport.PlayKeys
import ReleaseTransformations._

ThisBuild / organization := "touj"

ThisBuild / version := "0.0.0"

ThisBuild / scalaVersion := "2.13.3"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runClean,                               // : ReleaseStep
  runTest,                                // : ReleaseStep
  // setReleaseVersion,                      // : ReleaseStep
  // commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  pushChanges,                            // : ReleaseStep, also checks that an upstream branch is properly configured
  releaseStepCommand("inventory/docker:publish"),
  releaseStepCommand("transaction/docker:publish"),
  releaseStepCommand("user/docker:publish")
)

lazy val root = (project in file(".")).aggregate(inventory, transaction, user)

lazy val inventory = (project in file("inventory")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9001)
lazy val transaction = (project in file("transaction")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9002)
lazy val user = (project in file("user")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9003)
