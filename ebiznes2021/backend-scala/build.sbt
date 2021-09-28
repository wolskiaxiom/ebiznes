name := "backendscala"

version := "latest"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
resolvers += Resolver.jcenterRepo
resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

val playSilhouetteVersion = "6.1.1"
val slickVersion = "3.3.3"
val playSlickVersion = "5.0.0"

scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-password-bcrypt" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-persistence" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-crypto-jca" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-totp" % playSilhouetteVersion,
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "com.iheart" %% "ficus" % "1.4.7",
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.3-akka-2.6.x",
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.play" %% "play-slick" % playSlickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
  "org.xerial"        %  "sqlite-jdbc" % "3.30.1",
  ws,
  ehcache,
  guice,
  filters
)

lazy val root = (project in file(".")).enablePlugins(PlayScala).disablePlugins(PlayFilters)
enablePlugins(DockerPlugin)
unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

//PlayKeys.devSettings := Seq("play.server.http.port" -> "8080")
routesImport += "utils.route.Binders._"

import com.typesafe.sbt.packager.docker.DockerChmodType
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
dockerExposedPorts := Seq(9000)
packageName in Docker := "wolskiaxiom/" +  packageName.value