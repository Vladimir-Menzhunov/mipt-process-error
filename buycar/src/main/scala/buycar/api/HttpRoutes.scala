package buycar.api

import buycar.api.domain.getRequestInfo
import circuitbreaker.MyCircuitBreaker
import integrations.dealer.DealerIntegration
import integrations.documents.{DocumentsIntegration, DocumentsUser}
import integrations.notifications.NotificationIntegration
import integrations.pay.PayIntegration
import nl.vroste.rezilience.CircuitBreaker.{CircuitBreakerOpen, WrappedError}
import zio.ZIO
import zio.http._
import zio.http.model.Method

import scala.collection.concurrent.TrieMap

object HttpRoutes {
  val fallbackDocuments: TrieMap[String, DocumentsUser] = TrieMap.empty

  val app: HttpApp[
    MyCircuitBreaker with NotificationIntegration with PayIntegration with DocumentsIntegration with DealerIntegration,
    Response
  ] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" =>
        ZIO.succeed(Response.text("Hello buycar service"))

      case req @ Method.GET -> !! / "buy" / "car" =>
        val response = for {
          requestInfo <- getRequestInfo(req)
          carInfo <- DealerIntegration.checkCar(requestInfo.carName)
          documentsUser <-
            MyCircuitBreaker
              .run(DocumentsIntegration.getDocuments(requestInfo.userId))
              .tap(doc => ZIO.succeed(fallbackDocuments.put(requestInfo.userId, doc)))
              .catchAll {
                case CircuitBreakerOpen =>
                  val data = fallbackDocuments.get(requestInfo.userId)
                  ZIO.logInfo(s"Get data from fallback $data") *> ZIO.fromOption(data)
                case WrappedError(error) =>
                  ZIO.logError(s"Get error from documents ${error.toString}") *>
                    ZIO.fail(error)
              }
          payResult <- PayIntegration.pay(carInfo, documentsUser)
          _ <- NotificationIntegration
            .sendNotification(payResult, carInfo)
            .forkDaemon
        } yield Response.text(s"Answer: ${payResult.toString}")

        response.orElseFail(Response.text("Сервис недоступен!"))
    }
}
