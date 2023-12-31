import sbt._

object V {
  val zio = "2.0.13"
  val zioHttp = "0.0.5"
  val pureconfig = "0.17.4"
  val sttp = "3.9.0"
  val circe = "0.14.5"
  val rezilience = "0.9.3"
}

object Libs {

  val zio: List[ModuleID] = List(
    "dev.zio" %% "zio" % V.zio,
    "dev.zio" %% "zio-http" % V.zioHttp
  )

  val pureconfig: List[ModuleID] = List(
    "com.github.pureconfig" %% "pureconfig" % V.pureconfig
  )

  val sttp: List[ModuleID] = List(
    "com.softwaremill.sttp.client3" %% "zio" % V.sttp,
    "com.softwaremill.sttp.client3" %% "circe" % V.sttp
  )

  val circe: List[ModuleID] = List(
    "io.circe" %% "circe-parser" % V.circe,
    "io.circe" %% "circe-core" % V.circe,
    "io.circe" %% "circe-generic" % V.circe
  )
  // https://www.vroste.nl/rezilience/docs/
  val rezilience = List(
    "nl.vroste" %% "rezilience" % V.rezilience
  )
}