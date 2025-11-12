
name := "assignment-03"
version := "1.0.0"

scalaVersion := "3.3.3"

javacOptions ++= Seq("--release", "17")

resolvers += "Akka library repository".at("https://repo.akka.io/LD7QHqF1JbCaQdsp9YdgYfP-Cx7_-mQw6VEVARsnUAdBX0h7/secure")

enablePlugins(ScalafmtPlugin, ScoverageSbtPlugin, AssemblyPlugin)

lazy val akkaVersion = "2.10.5"
lazy val akkaGroup = "com.typesafe.akka"
libraryDependencies ++= Seq(
  akkaGroup %% "akka-actor-typed" % "2.10.5",
  akkaGroup %% "akka-actor" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  akkaGroup %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
)

// JUnit 5 can be added later if tests are introduced
// libraryDependencies += "org.junit.jupiter" % "junit-jupiter" % "5.10.2" % Test

// If you ever add a runnable entry point, you can set it like this:
// Compile / run / mainClass := Some("sequential.Main")
