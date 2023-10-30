import zio._
//package buycar
//
//object BuyCarMain extends ZIOAppDefault {
//  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
//    for {
//      _ <- ZIO.logInfo("Start app")
//      server <- zio.http.Server.serve(HttpRoutes.app)
//        .provide(
//          Server.live,
//          ServiceConfig.live,
//        )
//    } yield server
//  }
//}
