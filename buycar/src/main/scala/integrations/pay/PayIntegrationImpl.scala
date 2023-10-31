package integrations.pay

import integrations.dealer.CarInfoResponse
import integrations.documents.DocumentsUser
import io.circe.syntax.EncoderOps
import sttp.client3.circe.asJson
import sttp.client3.{SttpBackend, UriContext, basicRequest}
import sttp.model.Uri
import zio.{IO, Task, ZIO, ZLayer}

class PayIntegrationImpl(
    httpClient: SttpBackend[Task, Any]
) extends PayIntegration {

  val baseUri: Uri = uri"http://localhost:9094"

  def pay(
      carInfoResponse: CarInfoResponse,
      documentsUser: DocumentsUser
  ): IO[Serializable, PayResponse] = {
    val url = baseUri
      .addPath(Seq("pay"))

    val request =
      basicRequest
        .post(url)
        .body(
          PayRequest(
            userName = documentsUser.name,
            email = documentsUser.email,
            balance = documentsUser.balance,
            carName = carInfoResponse.name,
            carPrice = carInfoResponse.price.getOrElse(0)
          ).asJson.toString()
        )
        .response(asJson[PayResponse])

    httpClient.send(request).flatMap(response => ZIO.fromEither(response.body))
  }
}

object PayIntegrationImpl {
  val live: ZLayer[SttpBackend[Task, Any], Nothing, PayIntegration] =
    ZLayer.fromFunction(new PayIntegrationImpl(_))
}
