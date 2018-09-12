import com.typesafe.sbt.packager.MappingsHelper.{contentOf, directory}


name := "myhadoop"

version := "1.0"

scalaVersion := "2.12.1"

logBuffered in Test := false

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "com.typesafe.akka" %% "akka-actor" % "2.5.16",
  "com.typesafe.akka" %% "akka-remote" % "2.5.16",
"com.typesafe.akka" %% "akka-testkit" % "2.5.16" % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)


enablePlugins(JavaServerAppPackaging)

mainClass in Compile := Some("yinma.mapreduce.myremotedemo.RemoteActor01")
//mainClass in Compile := Some("cn.epoque.shoes.aip.ml.platform.dimensional.Root")
//mainClass in Compile := Some("epoque.bigdata.footinfo.loader.WebServerHttps")

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
    // copy configuration files to config directory
    contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

// add 'config' directory first in the classpath of the start script,

// an alternative is to set the config file locations via CLI parameters
// when starting the application
scriptClasspath := Seq("../config/") ++ scriptClasspath.value

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))