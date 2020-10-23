import play.sbt.PlayImport.PlayKeys

organization := "touj"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).aggregate(inventory, transaction, user)

lazy val inventory = (project in file("inventory")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9001)
lazy val transaction = (project in file("transaction")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9002)
lazy val user = (project in file("user")).enablePlugins(PlayScala).settings(PlayKeys.playDefaultPort := 9003)

scalaVersion := "2.13.3"
