package integrations.notifications

import integrations.dealer.CarInfoResponse
import integrations.pay.PayResponse
import sttp.model.StatusCode
import zio.{IO, ZIO}

trait NotificationIntegration {
  def sendNotification(payResponse: PayResponse, carInfoResponse: CarInfoResponse): IO[Serializable, StatusCode]
}

object NotificationIntegration {
  def sendNotification(payResponse: PayResponse, carInfoResponse: CarInfoResponse): ZIO[NotificationIntegration, Serializable, StatusCode] =
    ZIO.serviceWithZIO[NotificationIntegration](_.sendNotification(payResponse, carInfoResponse))
}
