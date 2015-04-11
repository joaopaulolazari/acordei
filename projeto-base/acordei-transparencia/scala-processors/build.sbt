name := "scala-processors"

version := "1.0"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.10" % "2.2-M1",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "com.github.scopt" %% "scopt" % "3.2.0",
  "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",
  "org.slf4j" % "slf4j-api" % "1.7.1",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.1",  // for any java classes looking for this
  "ch.qos.logback" % "logback-classic" % "1.0.3"
)