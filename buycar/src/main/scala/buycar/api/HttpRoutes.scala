package buycar.api

import buycar.api.domain.getRequestInfo
import integrations.dealer.DealerIntegration
import integrations.documents.DocumentsIntegration
import integrations.notifications.NotificationIntegration
import integrations.pay.PayIntegration
import zio.ZIO
import zio.http._
import zio.http.model.{Method, Status}

object HttpRoutes {
  val app: HttpApp[NotificationIntegration with PayIntegration with DocumentsIntegration with DealerIntegration, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" =>
        ZIO.succeed(Response.text("Hello buycar service"))

      case req @ Method.GET -> !! / "buy" / "car" =>
        val response = for {
          requestInfo <- getRequestInfo(req)
          carInfo <- DealerIntegration.checkCar(requestInfo.carName)
          documentsUser <- DocumentsIntegration.getDocuments(requestInfo.userId)
          payResult <- PayIntegration.pay(carInfo, documentsUser)
          _ <- NotificationIntegration.sendNotification(payResult, carInfo).forkDaemon
        } yield Response.text(s"Answer: ${payResult.toString}")

        response.orElseFail(Response.status(Status.BadRequest))
    }
}
