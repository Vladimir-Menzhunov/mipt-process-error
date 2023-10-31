package pay.api

import io.circe.generic.JsonCodec

@JsonCodec
case class PayRequest(
    userName: String,
    email: String,
    balance: Int,
    carName: String,
    carPrice: Int
)

@JsonCodec
case class PayResponse(
    name: String,
    email: String,
    balance: Int,
    purchasedCar: Option[String]
)
