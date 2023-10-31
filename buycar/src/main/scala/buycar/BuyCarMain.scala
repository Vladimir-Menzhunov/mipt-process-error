package buycar

import buycar.api.HttpRoutes
import buycar.config.ServiceConfig
import integrations.dealer.DealerIntegrationImpl
import integrations.documents.DocumentsIntegrationImpl
import integrations.notifications.NotificationIntegrationImpl
import integrations.pay.{PayIntegration, PayIntegrationImpl}
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio._
import zio.http.Server

object BuyCarMain extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("Start buy-car service")
      server <- zio.http.Server
        .serve(HttpRoutes.app)
        .provide(
          Server.live,
          ServiceConfig.live,
          HttpClientZioBackend.layer(),
          DocumentsIntegrationImpl.live,
          DealerIntegrationImpl.live,
          PayIntegrationImpl.live,
          NotificationIntegrationImpl.live,
        )

    } yield server
  }
}
