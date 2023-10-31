package integrations.dealer

import io.circe.parser._
import sttp.client3.{SttpBackend, UriContext, basicRequest}
import sttp.model.Uri
import zio.{IO, Task, ZIO, ZLayer}

class DealerIntegrationImpl(
    httpClient: SttpBackend[Task, Any]
) extends DealerIntegration {

  val baseUri: Uri = uri"http://localhost:9091"

  override def checkCar(name: String): IO[Serializable, CarInfoResponse] = {
    val url = baseUri
      .addPath(Seq("check", "car"))
      .withParam("name", name)

    val request =
      basicRequest
        .get(url)

    httpClient.send(request).flatMap(response =>
      ZIO.fromEither(response.body.flatMap(parse(_).flatMap(_.as[CarInfoResponse])))
    )
  }
}

object DealerIntegrationImpl {
  val live: ZLayer[SttpBackend[Task, Any], Nothing, DealerIntegrationImpl] =
    ZLayer.fromFunction(new DealerIntegrationImpl(_))
}
