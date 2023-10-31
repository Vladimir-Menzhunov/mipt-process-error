package integrations.pay

import io.circe.generic.JsonCodec
import io.circe.{Decoder, HCursor}

case class PayResponse(
    name: String,
    email: String,
    balance: Int,
    purchasedCar: Option[String]
)

object PayResponse {
  implicit val decoder: Decoder[PayResponse] = (c: HCursor) =>
    for {
      name <- c.downField("name").as[String]
      email <- c.downField("email").as[String]
      price <- c.downField("balance").as[Int]
      purchasedCar <- c.downField("purchasedCar").as[Option[String]]
    } yield PayResponse(name, email, price, purchasedCar)
}

@JsonCodec
case class PayRequest(
    userName: String,
    email: String,
    balance: Int,
    carName: String,
    carPrice: Int
)
