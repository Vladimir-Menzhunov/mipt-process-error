package integrations.documents

import io.circe.generic.JsonCodec

@JsonCodec
case class DocumentsUser(name: String, email: String, balance: Int)