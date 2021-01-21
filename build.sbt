name := "iterator-error"

version := "0.1"

//scalaVersion := "2.11.12"
scalaVersion := "2.12.13"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.3" % Test

lazy val flinkV = "1.12.1"
libraryDependencies += "org.apache.flink" %% "flink-streaming-scala" % flinkV
libraryDependencies += "org.apache.flink" %% "flink-clients" % flinkV

lazy val jacksonV = "2.12.1"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % jacksonV
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonV
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonV

fork in Test := true