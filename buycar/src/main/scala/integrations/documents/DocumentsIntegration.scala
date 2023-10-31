package integrations.documents

import zio.{IO, ZIO}

trait DocumentsIntegration {
  def getDocuments(id: String): IO[Serializable, DocumentsUser]
}

object DocumentsIntegration {
  def getDocuments(id: String): ZIO[DocumentsIntegration, Serializable, DocumentsUser] =
    ZIO.serviceWithZIO[DocumentsIntegration](_.getDocuments(id))
}
