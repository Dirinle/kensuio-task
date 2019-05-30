name := "kensuio-task"

version := "0.1"

scalaVersion := "2.12.8"
resolvers += Resolver.bintrayRepo("fcomb", "maven")
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-stream" % "2.6.0-M2",
  "com.typesafe" % "config" % "1.3.4",
  "de.heikoseeberger" %% "akka-http-circe" % "1.25.2",
  "io.circe" %% "circe-generic" % "0.12.0-M1",
  "io.fcomb" %% "akka-http-circe" % "10.0.7_0.8.0"
)