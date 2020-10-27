# Distributed Systems

## SBT
This is a monorepo, so multiple services + libraries live here.

To add a service:
- Run: `sbt new playframework/play-scala-seed.g8`
- Delete `lazy val root = (project in file(".")).enablePlugins(PlayScala)` from the module's build.sbt
- Add the module to the root build.sbt

To build image locally:
- `<project>/docker:publishLocal`

