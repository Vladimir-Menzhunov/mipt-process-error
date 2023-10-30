package buycar

import buycar.api.HttpRoutes
import buycar.config.ServiceConfig
import zio._
import zio.http.Server

object BuyCarMain extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("Start buy-car service")
      server <- zio.http.Server.serve(HttpRoutes.app)
        .provide(
          Server.live,
          ServiceConfig.live,
        )
    } yield server
  }
}
