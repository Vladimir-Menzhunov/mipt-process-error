package integrations.notifications

import io.circe.generic.JsonCodec

@JsonCodec
case class Notification(userName: String, email: String, actionInfo: String)