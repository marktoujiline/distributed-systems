import play.sbt.PlayImport.PlayKeys
import ReleaseTransformations._

ThisBuild / organization := "touj"

ThisBuild / scalaVersion := "2.13.3"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("inventory/docker:publish"),
  releaseStepCommand("transaction/docker:publish"),
  releaseStepCommand("user/docker:publish"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

releaseTagComment        := s"Releasing ${(version in ThisBuild).value} [ci skip]"
releaseCommitMessage     := s"Setting version to ${(version in ThisBuild).value} [ci skip]"
releaseNextCommitMessage := s"Setting version to ${(version in ThisBuild).value} [ci skip]"

lazy val root = (project in file(".")).aggregate(inventory, transaction, user)

lazy val inventory = (project in file("inventory")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9001)
lazy val transaction = (project in file("transaction")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9002)
lazy val user = (project in file("user")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9003)
