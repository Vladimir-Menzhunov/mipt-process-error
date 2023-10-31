package integrations.documents

import sttp.client3.circe.asJson
import sttp.client3.{SttpBackend, UriContext, basicRequest}
import sttp.model.Uri
import zio.{Task, ZIO, ZLayer}

class DocumentsIntegrationImpl(
    httpClient: SttpBackend[Task, Any]
) extends DocumentsIntegration {

  val baseUri: Uri = uri"http://localhost:9092"

  def getDocuments(id: String): Task[DocumentsUser] = {
    val url = baseUri
      .addPath(Seq("get", "documents", "by", "user"))
      .withParam("id", id)

    val request =
      basicRequest
        .get(url)
        .response(asJson[DocumentsUser])

    httpClient.send(request).flatMap(response => ZIO.fromEither(response.body))
  }
}

object DocumentsIntegrationImpl {
  val live: ZLayer[SttpBackend[Task, Any], Nothing, DocumentsIntegration] =
    ZLayer.fromFunction(new DocumentsIntegrationImpl(_))
}
