import play.sbt.PlayImport.PlayKeys
import ReleaseTransformations._
import sbtrelease.{Version, versionFormatError}

ThisBuild / organization := "touj"

ThisBuild / scalaVersion := "2.13.3"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setNextVersion,
  commitNextVersion,
  tagRelease,
  releaseStepCommand("inventory/docker:publish"),
  releaseStepCommand("transaction/docker:publish"),
  releaseStepCommand("user/docker:publish"),
  pushChanges
)

// // strip the qualifier off the input version, eg. 1.2.1-SNAPSHOT -> 1.2.1
// releaseVersion     := { ver => Version(ver).map(_.withoutQualifier.string).getOrElse(versionFormatError(ver)) }

// // bump the version and append '-SNAPSHOT', eg. 1.2.1 -> 1.3.0-SNAPSHOT
// releaseNextVersion := {
//   ver => Version(ver).map(_.bump(releaseVersionBump.value).asSnapshot.string).getOrElse(versionFormatError(ver))
// },

// bump the version, eg. 1.2.1 -> 1.3.0
releaseNextVersion := {
  ver => Version(ver).map(_.bump(releaseVersionBump.value).withoutQualifier.string).getOrElse(versionFormatError(ver))
}

releaseTagComment        := s"Releasing ${(version in ThisBuild).value} [ci skip]"
releaseNextCommitMessage := s"Setting version to ${(version in ThisBuild).value} [ci skip]"

lazy val root = (project in file(".")).aggregate(inventory, transaction, user)

lazy val inventory = (project in file("inventory")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9001)
lazy val transaction = (project in file("transaction")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9002)
lazy val user = (project in file("user")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9003)
