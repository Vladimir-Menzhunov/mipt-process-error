import Dependencies._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

lazy val root = (project in file("."))
  .settings(
    name := "process-errors"
  )
  .aggregate(
    buycar,
    dealer,
    documents,
    notification,
    pay
  )
  .dependsOn(
    buycar,
    dealer,
    documents,
    notification,
    pay
  )

lazy val buycar = (project in file("buycar"))
  .settings(
    name := "buy-car",
    libraryDependencies ++= BuyCar.dependencies,
    assembly / assemblyJarName := "buycar.jar"
  )

lazy val dealer = (project in file("dealer"))
  .settings(
    name := "dealer",
    libraryDependencies ++= Dealer.dependencies,
    assembly / assemblyJarName := "dealer.jar"
  )

lazy val documents = (project in file("documents"))
  .settings(
    name := "documents",
    libraryDependencies ++= Documents.dependencies,
    assembly / assemblyJarName := "documents.jar"
  )

lazy val notification = (project in file("notifications"))
  .settings(
    name := "notifications",
    libraryDependencies ++= Notifications.dependencies,
    assembly / assemblyJarName := "notifications.jar"
  )

lazy val pay = (project in file("pay"))
  .settings(
    name := "pay",
    libraryDependencies ++= Pay.dependencies,
    assembly / assemblyJarName := "pay.jar"
  )
