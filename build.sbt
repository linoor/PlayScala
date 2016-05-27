name := """scala-project"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers := ("Atlassian Releases" at "https://maven.atlassian.com/public/") +: resolvers.value

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.10.1"
libraryDependencies ++= Seq("com.typesafe.play" %% "anorm" % "2.5.0")
libraryDependencies ++= Seq("com.typesafe.play" %% "play-slick" % "2.0.0")
libraryDependencies ++= Seq("com.typesafe.play" %% "play-slick-evolutions" % "2.0.0")

libraryDependencies += evolutions

routesGenerator := StaticRoutesGenerator

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

fork in run := false

offline:=true
