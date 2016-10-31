name := """java_play_architecture"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.0",
  "com.datastax.cassandra" % "cassandra-driver-mapping" % "3.1.0",
  "com.datastax.cassandra" % "cassandra-driver-extras" % "3.1.0",
  "com.impetus.kundera.client" % "kundera-cassandra-ds-driver" % "3.5",
  filters
)
