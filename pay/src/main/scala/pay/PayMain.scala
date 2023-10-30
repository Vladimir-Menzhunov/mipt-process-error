package pay

import pay.api.HttpRoutes
import pay.config.ServiceConfig
import zio._
import zio.http.Server

object PayMain extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("Start pay service")
      server <- zio.http.Server.serve(HttpRoutes.app)
        .provide(
          Server.live,
          ServiceConfig.live,
        )
    } yield server
  }
}
