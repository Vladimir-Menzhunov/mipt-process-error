package integrations.notifications

import integrations.dealer.CarInfoResponse
import integrations.pay.PayResponse
import io.circe.syntax.EncoderOps
import sttp.client3.{SttpBackend, UriContext, basicRequest}
import sttp.model.{StatusCode, Uri}
import zio.{Task, ZIO, ZLayer}

class NotificationIntegrationImpl(
    httpClient: SttpBackend[Task, Any]
) extends NotificationIntegration {

  val baseUri: Uri = uri"http://localhost:9093"

  def sendNotification(payResponse: PayResponse, carInfoResponse: CarInfoResponse): Task[StatusCode] = {
    val url = baseUri
      .addPath(Seq("send", "notification"))

    val request =
      basicRequest
        .post(url)
        .body(
          Notification(
            payResponse.name,
            payResponse.email,
            s"You bought a ${carInfoResponse.name} car for ${carInfoResponse.price}"
          ).asJson.toString()
        )

    httpClient.send(request).flatMap(response => ZIO.succeed(response.code))
  }
}

object NotificationIntegrationImpl {
  val live: ZLayer[SttpBackend[Task, Any], Nothing, NotificationIntegration] =
    ZLayer.fromFunction(new NotificationIntegrationImpl(_))
}
