package documents

import documents.api.HttpRoutes
import documents.config.ServiceConfig
import zio._
import zio.http.Server

object DocumentsMain extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("Start documents service")
      server <- zio.http.Server.serve(HttpRoutes.app)
        .provide(
          Server.live,
          ServiceConfig.live,
        )
    } yield server
  }
}
