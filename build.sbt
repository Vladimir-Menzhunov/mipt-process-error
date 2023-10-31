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
    notifications,
    pay
  )
  .dependsOn(
    buycar,
    dealer,
    documents,
    notifications,
    pay
  )

lazy val buycar = (project in file("buycar"))
  .settings(
    name := "buycar",
    libraryDependencies ++= BuyCar.dependencies,
    scalacOptions ++= Seq("-Ymacro-annotations"),
    assembly / assemblyJarName := "buycar.jar"
  )

lazy val dealer = (project in file("dealer"))
  .settings(
    name := "dealer",
    libraryDependencies ++= Dealer.dependencies,
    scalacOptions ++= Seq("-Ymacro-annotations"),
    assembly / assemblyJarName := "dealer.jar"
  )

lazy val documents = (project in file("documents"))
  .settings(
    name := "documents",
    libraryDependencies ++= Documents.dependencies,
    scalacOptions ++= Seq("-Ymacro-annotations"),
    assembly / assemblyJarName := "documents.jar"
  )

lazy val notifications = (project in file("notifications"))
  .settings(
    name := "notifications",
    libraryDependencies ++= Notifications.dependencies,
    scalacOptions ++= Seq("-Ymacro-annotations"),
    assembly / assemblyJarName := "notifications.jar"
  )

lazy val pay = (project in file("pay"))
  .settings(
    name := "pay",
    libraryDependencies ++= Pay.dependencies,
    scalacOptions ++= Seq("-Ymacro-annotations"),
    assembly / assemblyJarName := "pay.jar"
  )
