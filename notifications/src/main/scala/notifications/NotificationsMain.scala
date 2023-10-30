package notifications

import notifications.api.HttpRoutes
import notifications.config.ServiceConfig
import zio._
import zio.http.Server

object NotificationsMain extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("Start notifications service")
      server <- zio.http.Server.serve(HttpRoutes.app)
        .provide(
          Server.live,
          ServiceConfig.live,
        )
    } yield server
  }
}
