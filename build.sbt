import scala.sys.process._
import play.sbt.PlayImport.PlayKeys
import ReleaseTransformations._
import sbtrelease.{Version, versionFormatError}
import com.typesafe.sbt.packager.docker.DockerChmodType
import Dependencies._

ThisBuild / organization := "touj"

ThisBuild / scalaVersion := "2.13.3"

git.useGitDescribe := true

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  setNextVersion,
  runClean,
  runTest,
  tagRelease,
  releaseStepCommand("inventory/docker:publish"),
  releaseStepCommand("transaction/docker:publish"),
  releaseStepCommand("user/docker:publish"),
  pushChanges
)

// bump the version, eg. 1.2.1 -> 1.3.0
releaseNextVersion := {
  ver => Version(ver).map(_.bump(releaseVersionBump.value).withoutQualifier.string).getOrElse(versionFormatError(ver))
}

lazy val root = (project in file("."))
  .aggregate(inventory, transaction, user, dfs)
  .enablePlugins(GitVersioning)

lazy val inventory = (project in file("inventory")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9001)
lazy val transaction = (project in file("transaction")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9002)
lazy val user = (project in file("user")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9003)
lazy val dfs = (project in file("dfs"))
  .settings(
    libraryDependencies += Lihaoyi.osLib
  )
