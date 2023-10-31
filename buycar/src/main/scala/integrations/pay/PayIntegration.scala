package integrations.pay

import integrations.dealer.CarInfoResponse
import integrations.documents.DocumentsUser
import zio.{IO, ZIO}

trait PayIntegration {
  def pay(cardInfoResponse: CarInfoResponse, documentsUser: DocumentsUser): IO[Serializable, PayResponse]
}

object PayIntegration {
  def pay(cardInfoResponse: CarInfoResponse, documentsUser: DocumentsUser): ZIO[PayIntegration, Serializable, PayResponse] =
    ZIO.serviceWithZIO[PayIntegration](_.pay(cardInfoResponse, documentsUser))
}
