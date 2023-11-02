import buycar.BuyCarMain
import dealer.DealerMain
import documents.DocumentsMain
import notifications.NotificationsMain
import pay.PayMain
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object StartApp extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("Start all services")
      _ <- BuyCarMain.run.fork
      _ <- DealerMain.run.fork
      _ <- DocumentsMain.run.fork
      //_ <- NotificationsMain.run.fork
      _ <- PayMain.run
    } yield ()
  }
}