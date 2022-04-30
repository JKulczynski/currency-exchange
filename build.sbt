ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "currency-exchange"
  )
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "3.1.0"