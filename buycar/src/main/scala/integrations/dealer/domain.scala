package integrations.dealer

import io.circe.{Decoder, HCursor}

case class CarInfoResponse(
    name: String,
    isAvailable: Boolean,
    price: Option[Int]
)

object CarInfoResponse {
  implicit val decoder: Decoder[CarInfoResponse] = (c: HCursor) =>
    for {
      name <- c.downField("name").as[String]
      isAvailable <- c.downField("isAvailable").as[Boolean]
      price <- c.downField("price").as[Option[Int]]
    } yield CarInfoResponse(name, isAvailable, price)
}
