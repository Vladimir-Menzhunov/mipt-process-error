package integrations.dealer

import sttp.client3.circe.asJson
import sttp.client3.{UriContext, basicRequest}
import sttp.client3.httpclient.zio.SttpClient
import sttp.client3.httpclient.zio.SttpClient.Service
import sttp.model.Uri
import zio.{Task, ZIO, ZLayer}
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec

final class DealerIntegrationImpl(
    httpClient: SttpClient.Service
) extends DealerIntegration {

  val baseUri: Uri = uri"http://localhost:9091"

  override def checkCar(name: String): Task[CarInfoResponse] = {
    val url = baseUri
      .addPath(Seq("check", "car"))
      .withParam("name", name)

    val request =
      basicRequest
        .get(url)
        .response(asJson[CarInfoResponse])

    httpClient.send(request).flatMap(response => ZIO.fromEither(response.body))
  }
}

object DealerIntegrationImpl {
  val live: ZLayer[Service, Nothing, DealerIntegrationImpl] =
    ZLayer.fromFunction(new DealerIntegrationImpl(_))
}
