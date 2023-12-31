import Libs._
import sbt._

trait Dependencies {
  def dependencies: Seq[ModuleID]
}

object Dependencies {
  object BuyCar extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(zio, pureconfig, sttp, circe, rezilience).flatten
  }

  object Dealer extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(zio, pureconfig, circe).flatten
  }

  object Documents extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(zio, pureconfig, circe).flatten
  }

  object Pay extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(zio, pureconfig, circe).flatten
  }

  object Notifications extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(zio, pureconfig, circe).flatten
  }
}
